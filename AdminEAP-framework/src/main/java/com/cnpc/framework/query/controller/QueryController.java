package com.cnpc.framework.query.controller;

import com.alibaba.fastjson.JSON;
import com.cnpc.framework.query.entity.Column;
import com.cnpc.framework.query.entity.ColumnConfig;
import com.cnpc.framework.query.entity.Query;
import com.cnpc.framework.query.entity.QueryConfig;
import com.cnpc.framework.query.pojo.QueryDefinition;
import com.cnpc.framework.query.service.QueryService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于xml配置的query 需要优化
 *
 * @author billjiang
 */
@Controller
@RequestMapping("/query")
public class QueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);

    @Resource
    private QueryService queryService;

    /**
     * 第一次加载页面初始化
     *
     * @param reqObj 前台参数
     * @return
     */
    @RequestMapping("/loadData")
    @ResponseBody
    public Map<String, Object> loadData(String reqObj) throws Exception {

        return queryService.loadData(reqObj);
    }

    /**
     * 导出数据
     *
     * @param reqObjs   前台参数
     * @param tableName 表名
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/exportData")
    public void exportData(String reqObjs, String tableName,  HttpServletResponse response)
            throws Exception {
        String tempFile = queryService.exportData(reqObjs, tableName);
        response.getWriter().print(tempFile);
    }

    @RequestMapping("/downExport")
    public void downExport(HttpServletRequest request, HttpServletResponse response) {

        OutputStream out = null;
        try {
            String templateName = request.getParameter("tempfile");
            String fileName = request.getParameter("tableName");
            out = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1") + ".xls");
            File file = new File(request.getRealPath("/") + File.separator + "templates" + File.separator + "temp" + File.separator
                    + templateName + ".xls");
            FileInputStream inputStream = new FileInputStream(file);
            // 开始读取下载
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = inputStream.read(b)) > 0) {
                out.write(b, 0, i);
            }
            inputStream.close();
            file.delete();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=utf-8");
            try {
                out.write("数据表导出异常，请重试！".getBytes("utf-8"));
            } catch (IOException e1) {
            }
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 跳转到自定义表格配置界面
     */
    @RequestMapping(value = "tableConfig")
    public String tableConfig(String queryId, String pageName, Model model) {

        model.addAttribute("queryId", queryId);
        model.addAttribute("pageName", pageName);
        return "base/query/table_config";
    }

    /**
     * 跳转到自定义表格配置界面 配置其中一个query,与其绑定的query也随着改变 XXB
     *
     * @param queryId     主query
     * @param bindQueryId 次query
     * @param pageName
     * @param model
     * @return
     */
    @RequestMapping(value = "tableConfigForMultQuery")
    public String tableConfigForMultQuery(String queryId, String bindQueryId, String pageName, Model model) {

        model.addAttribute("queryId", queryId);
        model.addAttribute("bindQueryId", bindQueryId);
        model.addAttribute("pageName", pageName);
        return "base/query/table_config_multquery";
    }

    @RequestMapping(value = "getConfigData")
    @ResponseBody
    public ColumnConfig getConfigData(String queryId, String pageName, HttpSession session) {

        ColumnConfig config = new ColumnConfig();
        // 得到已选择的Column
        //TODO
        String userid = "todo";
        DetachedCriteria criteria = DetachedCriteria.forClass(QueryConfig.class);
        criteria.add(Restrictions.eq("queryId", queryId));
        criteria.add(Restrictions.eq("pageName", pageName));
        criteria.add(Restrictions.eq("userid", userid));
        List<QueryConfig> list = queryService.findByCriteria(criteria);
        // 如果数据库不为空，则取数据库，否则取xml配置文档
        Query query = QueryDefinition.getQueryById(queryId);
        Object[][] selected = new Object[][]{};
        Object[][] unSelected = new Object[][]{};
        List<Column> columnList = new ArrayList<Column>();
        for (Column column : query.getColumnList()) {
            columnList.add(column);
        }
        int left = 0;
        int right = 0;

        if (list.size() > 0) {
            QueryConfig configExist = list.get(0);
            List<String> columnNames = configExist.getColumns();
            // 选中的列
            int i = 0;
            for (String columnName : columnNames) {
                for (Column column : query.getColumnList()) {
                    if (columnName.equals(column.getId() != null ? column.getId() : column.getKey())) {
                        Object[] objs = new Object[2];
                        objs[0] = column.getHeader();
                        objs[1] = columnName;
                        selected[i++] = objs;
                        columnList.remove(column);
                    }
                }
            }
            config.setSelected(selected);
            // 未选中的列
            int j = 0;
            for (Column column : columnList) {
                if (!column.getHidden() && !column.getIsServerCondition()) {
                    Object[] objs = new Object[2];
                    objs[0] = column.getHeader();
                    objs[1] = column.getId() != null ? column.getId() : column.getKey();
                    unSelected[j++] = objs;
                }
            }
            config.setUnSelected(unSelected);
        } else {
            // 初始化选中列
            int i = 0;
            for (Column column : columnList) {
                if (!column.getHidden() && !column.getIsServerCondition()) {
                    Object[] objs = new Object[2];
                    objs[0] = column.getHeader();
                    objs[1] = column.getId() != null ? column.getId() : column.getKey();
                    selected[i++] = objs;
                }
            }
            config.setSelected(selected);
            config.setUnSelected(unSelected);
        }
        return config;
    }

    /**
     * @param queryId
     * @param pageName
     * @param session
     * @return
     */
    @RequestMapping(value = "getColumnConfig")
    @ResponseBody
    public Map<String, Object> getColumnConfig(String queryId, String pageName, HttpSession session) throws Exception {

        Query query = QueryDefinition.getQueryById(queryId);
        //TODO
        String userid = "todo";
        DetachedCriteria criteria = DetachedCriteria.forClass(QueryConfig.class);
        criteria.add(Restrictions.eq("queryId", queryId));
        criteria.add(Restrictions.eq("pageName", pageName));
        criteria.add(Restrictions.eq("userid", userid));
        List<QueryConfig> list = queryService.findByCriteria(criteria);
        List<String> colsName = new ArrayList<String>();
        if (list.size() > 0)
            colsName = list.get(0).getColumns();
        Map<String, Object> map = new HashMap<String, Object>();
        //query.setCallList(getCallList(query));
        map.put("query", query);
        map.put("columnName", colsName);
        return map;
    }

    /**
     * @param configObj
     * @param session
     * @return
     */
    @RequestMapping(value = "saveUserDefine")
    @ResponseBody
    public String saveUserDefine(String configObj, HttpSession session) {

        QueryConfig config = JSON.parseObject(configObj, QueryConfig.class);
        //TODO
        String userid = "todo";
        config.setUserid(userid);
        queryService.deleteAndSave(config);
        return "200";
    }

    /**
     * 恢复默认设置
     *
     * @param configObj
     * @param session
     * @return
     */
    @RequestMapping(value = "setDefault")
    @ResponseBody
    public String setDefault(String configObj, HttpSession session) {

        QueryConfig config = JSON.parseObject(configObj, QueryConfig.class);
        //TODO
        config.setUserid(null);
        queryService.delete(config);
        return "200";
    }

    @RequestMapping(value = "saveUserDefines")
    @ResponseBody
    public String saveUserDefines(String configList, HttpSession session) {

        List<QueryConfig> cfgList = JSON.parseArray(configList, QueryConfig.class);
        if (cfgList == null || cfgList.size() < 1)
            return "";
        //TODO
        for (QueryConfig cfg : cfgList) {
            cfg.setUserid(null);
            queryService.deleteAndSave(cfg);
        }
        return "200";
    }

    /**
     * 恢复默认设置
     *
     * @param configList
     * @param session
     * @return
     */
    @RequestMapping(value = "setDefaults")
    @ResponseBody
    public String setDefaults(String configList, HttpSession session) {

        List<QueryConfig> cfgList = JSON.parseArray(configList, QueryConfig.class);
        if (cfgList == null || cfgList.size() < 1)
            return "";
        //TODO
        for (QueryConfig cfg : cfgList) {
            cfg.setUserid(null);
            queryService.delete(cfg);
        }
        return "200";
    }

}
