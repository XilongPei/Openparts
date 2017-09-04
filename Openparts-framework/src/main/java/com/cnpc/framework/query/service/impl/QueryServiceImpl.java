package com.cnpc.framework.query.service.impl;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.base.pojo.PageInfo;
import com.cnpc.framework.base.service.impl.BaseServiceImpl;
import com.cnpc.framework.exception.QueryException;
import com.cnpc.framework.query.entity.Query;
import com.cnpc.framework.query.entity.QueryCondition;
import com.cnpc.framework.query.entity.QueryConfig;
import com.cnpc.framework.query.service.QueryService;
import com.cnpc.framework.query.util.ExportUtil;
import com.cnpc.framework.query.util.QueryUtil;
import com.cnpc.framework.utils.StrUtil;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("queryService")
public class QueryServiceImpl extends BaseServiceImpl implements QueryService {

    private static final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);


    @Override
    public Map<String, Object> loadData(String reqObj) throws Exception {
        //用于接收返回数据(配置、分页、数据)
        Map<String, Object> map = new HashMap<>();
        QueryCondition queryCondition = JSON.parseObject(reqObj, QueryCondition.class);
        //获取Query配置
        Query query = QueryUtil.getQuery(queryCondition);
        //获取所属的类
        Class<?> objClass = QueryUtil.getClassName(query.getClassName());
        // 分页信息
        PageInfo pageInfo = QueryUtil.getPageInfo(queryCondition, query);
        //返回数据
        List objList = getDataList(queryCondition, query, pageInfo, objClass, true);

        //table自定义方法，以后有需要的话可放开
        //List<Call> callList = getCallList(query);
        //query.setCallList(callList);
        map.put("query", query);
        map.put("pageInfo", pageInfo);
        map.put("rows", objList);
        return map;
    }

    @Override
    public String exportData(String reqObj, String tableName) throws Exception {
        List<QueryCondition> queryConditions = JSON.parseArray(reqObj, QueryCondition.class);
        if (queryConditions.isEmpty()) {
            throw new QueryException("导出时发生异常：queryConditions为空");
        }

        String fileName=System.currentTimeMillis()+"_"+tableName;
        //临时表名
        OutputStream fout = new FileOutputStream(ExportUtil.getFilePath(fileName));
        // 产生工作簿对象
        WritableWorkbook workbook = Workbook.createWorkbook(fout);
        for (int qindex = 0; qindex < queryConditions.size(); qindex++) {
            QueryCondition queryCondition = queryConditions.get(qindex);
            Query query = QueryUtil.getQuery(queryCondition);
            Class<?> objClass = QueryUtil.getClassName(query.getClassName());
            //此处pageInfo没有什么用处
            PageInfo pageInfo = QueryUtil.getPageInfo(queryCondition, query);
            List objList = getDataList(queryCondition, query, pageInfo, objClass, false);
            //构建工作表
            WritableSheet sheet = workbook.createSheet(StrUtil.isEmpty(queryCondition.getSheetName()) ?
                    tableName : queryCondition.getSheetName(), qindex);

            //写入标题
            ExportUtil.writeTitle(queryCondition,query,sheet);
            //写入表头
            ExportUtil.writeHeader(queryCondition,query,sheet);
            //写入数据
            ExportUtil.writeData(queryCondition,query,sheet,objList,objClass);
            //执行其他特殊处理
            ExportUtil.executeSheetMethod(queryCondition,sheet);

        }
        workbook.write();
        workbook.close();
        fout.flush();
        fout.close();
        return fileName;

    }


    /**
     * 获取查询结果数据
     *
     * @param queryCondition 查询条件
     * @param query          查询配置
     * @param pageInfo       分页信息
     * @param objClass       映射类
     * @param isQuery        是否是查询 true=查询 false=导出（其他）
     * @return 返回的数据列表
     * @throws QueryException 获取过程中的异常
     */
    public List getDataList(QueryCondition queryCondition, Query query, PageInfo pageInfo, Class objClass, boolean isQuery) throws QueryException {
        List objList = null;
        //使用反射的接口查询，一般用于非常复杂的查询
        if (!query.getSimpleSearch()) {
            objList = QueryUtil.queryByService(queryCondition, query, pageInfo);
        }
        // sql 查询方式 (1=1 方式传值,同时支持变量传值@#),可映射到类
        else if (StrUtil.isNotBlank(query.getSql())) {
            Map<String, Object> params = QueryUtil.getSqlParams(queryCondition, query);
            String sql = params.get("sql").toString();
            Object[] objArrs = (Object[]) params.get("objArr");
            Type[] typeArrs = (Type[]) params.get("typeArr");
            if (query.getAllowPaging() && isQuery)
                objList = this.findMapBySql(sql, query.getCountStr(), pageInfo, objArrs, typeArrs, objClass);
            else
                objList = this.findMapBySql(sql, objArrs, typeArrs, objClass);
        }
        //var sql 查询方式 (非1=1方式传值 变量替换@#方式)，可映射到类
        else if (StrUtil.isNotBlank(query.getVarSql())) {
            StringBuilder sqlBuilder = new StringBuilder(query.getVarSql());
            QueryUtil.generateFilter(queryCondition, query, sqlBuilder);
            String sql = sqlBuilder.toString();
            if (query.getAllowPaging() && isQuery)
                objList = this.findMapBySql(sql, query.getCountStr(), pageInfo, new Object[]{}, new Type[]{}, objClass);
            else
                objList = this.findMapBySql(sql, new Object[]{}, new Type[]{}, objClass);
        }
        // criteria 离线查询方式
        else if (objClass != null) {
            DetachedCriteria criteria = QueryUtil.generateCriteria(queryCondition, query, objClass);
            pageInfo.setCount(this.getCountByCriteria(criteria));
            if (query.getAllowPaging() && isQuery)
                objList = this.getListByCriteria(criteria, pageInfo);
            else
                objList = this.findByCriteria(criteria);
        }
        return objList;
    }



    public void deleteAndSave(QueryConfig queryConfig) {

        // 获取原有列表并删除
        delete(queryConfig);
        // 再保存最新的列表
        this.save(queryConfig);
    }

    public void delete(QueryConfig queryConfig) {

        // 获取原有列表并删除
        DetachedCriteria criteria = DetachedCriteria.forClass(QueryConfig.class);
        criteria.add(Restrictions.eq("userid", queryConfig.getUserid()));
        criteria.add(Restrictions.eq("pageName", queryConfig.getPageName()));
        criteria.add(Restrictions.eq("queryId", queryConfig.getQueryId()));
        List<QueryConfig> list = this.findByCriteria(criteria);
        this.batchDelete(list);
    }

}
