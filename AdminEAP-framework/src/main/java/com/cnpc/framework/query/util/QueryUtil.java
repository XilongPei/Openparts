package com.cnpc.framework.query.util;

import com.cnpc.framework.base.pojo.PageInfo;
import com.cnpc.framework.exception.QueryException;
import com.cnpc.framework.query.entity.Column;
import com.cnpc.framework.query.entity.Query;
import com.cnpc.framework.query.entity.QueryCondition;
import com.cnpc.framework.query.filter.ConditionOperator;
import com.cnpc.framework.query.filter.IFilter;
import com.cnpc.framework.query.filter.StringFilter;
import com.cnpc.framework.query.pojo.QueryDefinition;
import com.cnpc.framework.utils.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.type.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by billJiang on 2017/1/18.
 * e-mail:jrn1012@petrochina.com.cn qq:475572229
 * query查询的工具类
 */
public class QueryUtil {

    private static final Logger logger= LoggerFactory.getLogger(QueryUtil.class);

    /**
     * 根据类名获取类
     *
     * @param className 类名
     * @return 类
     * @throws ClassNotFoundException 找不到类异常
     */
    public static Class<?> getClassName(String className) throws ClassNotFoundException {
        Class<?> objClass = null;
        if (!StrUtil.isEmpty(className)) {
            objClass = Class.forName(className);
        }
        return objClass;

    }

    /**
     * 根据queryCondition获取query
     *
     * @param queryCondition 查询条件
     * @return 查询参数
     * @throws QueryException xml配置不存在 queryId为空异常
     */
    public static Query getQuery(QueryCondition queryCondition) throws QueryException {
        if (queryCondition.getQuery() != null)
            return queryCondition.getQuery();
        String queryId = queryCondition.getQueryId();
        if (!StrUtil.isEmpty(queryId)) {
            Query query = QueryDefinition.getQueryById(queryId);
            if (query == null) {
                throw new QueryException("queryId为【" + queryId + "】的xml配置不存在");
            } else {
                return query;
            }
        } else {
            throw new QueryException("queryId为空，请指定queryId!");
        }
    }

    /**
     * 获取分页信息
     *
     * @param queryCondition 查询条件
     * @param query          查询配置
     * @return 分页信息
     */
    public static PageInfo getPageInfo(QueryCondition queryCondition, Query query) {
        PageInfo pageInfo = new PageInfo();
        if (queryCondition.getPageInfo() == null) {
            pageInfo.setPageSize(query.getPagesize());
        } else {
            pageInfo = queryCondition.getPageInfo();
        }
        return pageInfo;
    }

    /**
     * 通过接口方式查询，获取数据列表
     *
     * @param queryCondition 查询参数
     * @param query          查询配置
     * @param pageInfo       分页信息
     * @return 数据列表
     * @throws QueryException service和method不存在异常，以及method内部异常
     */
    public static List queryByService(QueryCondition queryCondition, Query query, PageInfo pageInfo) throws QueryException {
        Object service = SpringContextUtil.getBean(query.getService());
        if (service == null) {
            throw new QueryException("service为【" + query.getService() + "】的接口不存在");
        }
        Class clazz = service.getClass();
        if (StrUtil.isEmpty(query.getMethod())) {
            throw new QueryException("method为【" + query.getMethod() + "】不存在");
        }
        try {
            Method method = clazz.getDeclaredMethod(query.getMethod(), QueryCondition.class, PageInfo.class);
            return (List) method.invoke(service, queryCondition, pageInfo);
        } catch (Exception ex) {
            throw new QueryException("【" + query.getService() + "】接口的【" + query.getMethod() + "】方法调用异常");
        }
    }

    /**
     * 获取注入值类型
     *
     * @param objArr 值
     * @return 值类型
     */
    public static Type[] getTypeArr(Object[] objArr) {
        Type[] typeArr = new Type[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj instanceof java.lang.String) {
                typeArr[i] = StringType.INSTANCE;
            } else if (obj instanceof java.lang.Integer) {
                typeArr[i] = IntegerType.INSTANCE;
            } else if (obj instanceof java.lang.Boolean) {
                typeArr[i] = BooleanType.INSTANCE;
            } else if (obj instanceof java.util.Date) {
                typeArr[i] = DateType.INSTANCE;
            } else {
                typeArr[i] = StringType.INSTANCE;
            }
        }
        return typeArr;
    }

    /**
     * 获取sql的查询参数 sql：查询sql  objArr:值参数数组  typeArr：类型数组
     *
     * @param query 查询配置
     * @return 注入条件后的查询sql及参数
     */
    public static Map getSqlParams(QueryCondition queryCondition, Query query) throws QueryException{
        Map<String, Object> map = new HashMap<>();
        StringBuilder sqlBuilder = new StringBuilder(query.getSql());
        Object[] objArr = new Object[]{};
        Type[] typeArr = new Type[]{};
        String replaced = "1=1";
        String sql = sqlBuilder.toString();
        int conPos = sql.indexOf(replaced);
        int countMatch = 0;
        if (conPos > -1) {
            IFilter filter = generateFilter(queryCondition, query, sqlBuilder);
            sql = sqlBuilder.toString();
            if (filter != null) {
                objArr = filter.getValues().toArray();
                typeArr = QueryUtil.getTypeArr(objArr);
                countMatch = StrUtil.countMatches(sql, replaced);
                sql = sql.replace(replaced, filter.getString());
            }
        }
        Object[] objArrs = new Object[objArr.length * countMatch];
        Type[] typeArrs = new Type[typeArr.length * countMatch];
        for (int i = 0; i < countMatch; i++) {
            System.arraycopy(objArr, 0, objArrs, i * objArr.length, objArr.length);
            System.arraycopy(typeArr, 0, typeArrs, i * typeArr.length, typeArr.length);
        }
        map.put("sql", sql);
        map.put("objArr", objArrs);
        map.put("typeArr", typeArrs);
        return map;
    }


    /**
     * 获取排序配置,使用ID替换key 解决排序问题
     *
     * @param condition 查询条件（界面）
     * @param query     查询配置
     * @return 排序
     */
    private static String getSortInfo(QueryCondition condition, Query query) {
        String sortInfo=!StrUtil.isEmpty(condition.getSortInfo()) ? condition.getSortInfo() : query.getOrder();
        if(StrUtil.isEmpty(sortInfo))
            return sortInfo;
        String[] arr=sortInfo.split(",");
        for (String str : arr) {
            String[] keyArr=str.split(" ");
            String key=keyArr[0].trim();
            String id=getColumnIdByKey(query,key);
            if(!StrUtil.isEmpty(id))
            sortInfo=sortInfo.replace(key,id);
        }
        return sortInfo;
    }

    private static String getColumnIdByKey(Query query,String key){
        for (Column column : query.getColumnList()) {
            if(column.getKey().equals(key)){
                return column.getId();
            }
        }
        return key;
    }

    /**
     * 获取是否需要当前1=1的条件注入private
     *
     * @param map 前端值
     * @return 是否需要作为条件注入1=1
     */
    private static boolean getIsCondition(Map map) {
        boolean isCondition = true;
        if (map.get("isCondition") != null)
            isCondition = Boolean.valueOf(map.get("isCondition").toString());
        return isCondition;
    }

    /**
     * 获取查询操作符
     *
     * @param map 前台参数
     * @param column 列配置
     * @return 操作符
     */
    private static String getOperatorStr(Map map, Column column) {
        if (map.get("operator") != null && !StrUtil.isEmpty(map.get("operator").toString())) {
            return map.get("operator").toString();
        }
        return column.getOperator();
    }

    /**
     * 获取操作符
     *
     * @param operator 操作符字符串
     * @return 操作符
     */
    private static ConditionOperator getOperator(String operator) {
        return ConditionOperator.getOperator(operator);
    }

    /**
     * 查询值是否为空
     *
     * @param value 值
     * @return 是否为空
     */
    private static boolean isNotEmptyValue(Object value) {
        if (value == null)
            return false;
        if (StrUtil.isEmpty(value.toString())) {
            return false;
        }
        if (value.toString().equals("%%") || value.toString().equals("%") || value.toString().equals(","))
            return false;
        return true;
    }


    /**
     * 不需要注入值的情况
     *
     * @param operator 操作符
     * @return 是否需要值
     */
    private static boolean isNeedValue(ConditionOperator operator) {
        if (operator.equals(ConditionOperator.NOT_NULL) || operator.equals(ConditionOperator.NULL))
            return false;
        if (operator.equals(ConditionOperator.NOT_EXISTS) || operator.equals(ConditionOperator.EXISTS))
            return false;
        return true;
    }

    /**
     * 根据操作符来处理值，并返回结果
     *
     * @param operator 操作符
     * @param value    值
     * @return 处理后的值
     * @throws Exception JSonHelper.getValue异常
     */
    public static Object getValueByOperator(ConditionOperator operator, Object value, Class clazz) throws Exception {
        if (operator.equals(ConditionOperator.BETWEEN)) {
            return getValueForBetween(value, clazz);
        } else if (operator.equals(ConditionOperator.IN) || operator.equals(ConditionOperator.NOT_IN)) {
            return getValueForInOrNotIn(value, clazz);
        }
        return JSonHelper.getValue(clazz, value);
    }


    /**
     * 处理between的注入值
     *
     * @param value 原来值
     * @param clazz 类型
     * @return 处理后的值
     * @throws Exception JSonHelper.getValue异常
     */
    private static Object getValueForBetween(Object value, Class clazz) throws Exception {
        String[] values = value.toString().split(",");
        List list = new ArrayList();
        if (StrUtil.isEmpty(values[0]) && StrUtil.isEmpty(values[1])) {
            return null;
        }
        for (String s : values) {
            if (StrUtil.isEmpty(s)) {
                list.add(null);
            } else {
                list.add(JSonHelper.getValue(clazz, s));
            }
        }
        //只选取了开始时间
        if(values.length==1){
            list.add(null);
        }
        return list;
    }


    /**
     * 处理in not_in的注入值
     *
     * @param value 原来值
     * @param clazz 类型
     * @return 处理后的值
     * @throws Exception JSonHelper.getValue异常
     */
    private static Object getValueForInOrNotIn(Object value, Class clazz) throws Exception {
        String[] values = value.toString().split(",");
        List list = new ArrayList();
        for (String s : values) {
            if (!StrUtil.isEmpty(s)) {
                list.add(JSonHelper.getValue(clazz, s));
            }
        }
        return list.isEmpty() ? null : list;
    }


    /**
     * 日期查询的处理
     *
     * @param operator 操作符
     * @param value    值
     * @return map 处理后的值 operator返回操作符（日期操作 EQ 改为BETWEEN） value
     */
    private static Map<String, Object> getValueForDate(ConditionOperator operator, Object value) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (!value.toString().contains(":")||value.toString().contains("00:00:00")) {
            if (operator.equals(ConditionOperator.BETWEEN)) {
                List list = (List) value;
                if (list.get(1) != null) {
                    Object nextValue = DateUtil.getLastMilliSecond(list.get(1));
                    list.set(1, nextValue);
                }
                value = list;
            } else if ((operator.equals(ConditionOperator.EQ)) || (operator.equals(ConditionOperator.GT))
                    || (operator.equals(ConditionOperator.GE))) {
                Object nextValue = DateUtil.getLastMilliSecond(value);
                if (operator.equals(ConditionOperator.EQ)) {
                    operator = ConditionOperator.BETWEEN;
                    value = Arrays.asList(value, nextValue);
                } else {
                    value = nextValue;
                }
            }
        }
        map.put("operator", operator);
        map.put("value", value);
        return map;
    }


    /**
     * 查询条件值处理结果（值和操作符）
     *
     * @param operator 操作符
     * @param value    值
     * @return map 处理后的值 operator返回操作符（日期操作 EQ 改为BETWEEN） value
     */
    private static Map<String, Object> getValueForOperator(ConditionOperator operator, Object value, Class clazz) throws QueryException {
        Object newValue;
        try {
            newValue = getValueByOperator(operator, value, clazz);
        } catch (Exception ex) {
            throw new QueryException("JSonHelper.getValue发生异常,转化的参数为：" + value.toString());
        }
        if (newValue == null)
            return null;
        //对日期格式的处理
        if (Date.class.equals(clazz) || Date.class.equals(clazz.getSuperclass())) {
            try {
                //时间处理可能改变操作符 比如EQ 变成 BETWEEN
                Map<String, Object> date_map = getValueForDate(operator, newValue);
                newValue = date_map.get("value");
                operator = (ConditionOperator) date_map.get("operator");
            } catch (Exception ex) {
                throw new QueryException("DateUtil.getLastMilliSecond日期转化发生异常");
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("operator", operator);
        map.put("value", newValue);
        return map;
    }

    /**
     * 获取服务器端注入的查询条件
     *
     * @param query 查询配置
     * @return 服务器端拼接的查询条件
     */
    private static List<Map<String, Object>> getServerConditions(Query query) {
        List<Column> collist = query.getColumnList();
        List<Map<String, Object>> serverConditions = new ArrayList<>();
        for (Column column : collist) {
            //控制是否是服务器端查询条件的开关
            if (!column.getIsServerCondition()) {
                continue;
            }
            Map<String, Object> conditionMap = new HashMap<>();
            conditionMap.put("value", column.getValue());
            conditionMap.put("operator", column.getOperator());
            conditionMap.put("key", column.getKey());
            serverConditions.add(conditionMap);
        }
        return serverConditions;
    }


    /**
     * 拼接sql查询条件
     *
     * @param condition  查询条件
     * @param query      查询配置
     * @param sqlBuilder sql
     * @return 查询条件拼接对象
     */
    public static IFilter generateFilter(QueryCondition condition, Query query, StringBuilder sqlBuilder) throws QueryException {

        //sql追加排序
        String sortInfo = getSortInfo(condition, query);
        String sql_order = sqlBuilder.toString();
        if (!StrUtil.isEmpty(sortInfo)) {
            //存在则替换
            if (sql_order.toLowerCase().lastIndexOf("order ") > -1)
                sql_order = sql_order.substring(0, sql_order.toLowerCase().lastIndexOf("order ")) + " order by " + sortInfo;
            else //不存在order，末端追加
                sql_order = sql_order + " order by " + sortInfo;
            if (query.getAllowPaging() && StrUtil.isNotBlank(query.getSortKey())) {
                //sortKey主要用于排序后依然重复的情况，在换页后第一页跑到第二页，一般sortkey为id,或者时间
                sql_order = sql_order + "," + query.getSortKey();
            }
            sqlBuilder.delete(0, sqlBuilder.toString().length()).append(sql_order);
        }

        IFilter filter = null;
        if (!StrUtil.isEmpty(query.getFilter())) {
            filter = new StringFilter(query.getFilter());
        }
        List<Map<String, Object>> conditions = condition.getConditions();
        //注入服务器查询条件 通过column的 isServerCondition operator value注入
        conditions.addAll(getServerConditions(query));
        //TODO 数据权限注入
        if (conditions.isEmpty()) {
            return filter;
        }

        logger.debug("-------"+query.getId()+"的filter查询条件-----------");
        for (Map<String, Object> map : conditions) {
            String key = map.get("key").toString();
            Object value = map.get("value");
            boolean isCondition = getIsCondition(map);
            Column column = query.getColumn(key);
            if (column == null) {
                column = new Column();
                column.setKey(key);
            }
            String operatorStr = getOperatorStr(map, column);
            logger.debug("condition:"+key+" "+operatorStr+" "+ value);

            ConditionOperator operator = getOperator(operatorStr);

            Class clazz;
            try {
                clazz = JSonHelper.getClass(column.getClassType());
            } catch (Exception ex) {
                throw new QueryException("转化类【" + column.getClassType() + "】出现异常！");
            }
            //操
            // 作需要值，并且有值
            Object newValue = value;
            if (isNeedValue(operator) && isNotEmptyValue(value)) {
                //对值进行处理
                Map<String, Object> value_map = getValueForOperator( operator, value, clazz);
                if (value_map == null)
                    continue;
                newValue = value_map.get("value");
                operator = (ConditionOperator) value_map.get("operator");

                IFilter simpleFilter = operator.getFilter(key, newValue);
                if (simpleFilter != null && isCondition) {
                    filter = simpleFilter.appendAnd(filter);
                }
            } else if (!isNeedValue(operator)) {
                //操作不需要值
                filter = operator.getFilter(key, newValue).appendAnd(filter);
            }
            //替换变量
            sqlBuilder = processVariable(sqlBuilder, key, newValue, column);
        }

        return filter;
    }


    /**
     * 处理变量条件@#
     *
     * @param sqlBuilder sql
     * @param key        键
     * @param value      值
     * @param column     列配置
     * @return 替换为值后的sql
     */
    private static StringBuilder processVariable(StringBuilder sqlBuilder, String key, Object value, Column column) {
        String sql = sqlBuilder.toString();
        if (sql.contains("@" + key + "#")) {
            if (value instanceof java.lang.String) {
                if (column.getIsQuote()) {
                    sql = sql.replaceAll("@" + key + "#", "'" + value + "'");
                } else {
                    sql = sql.replaceAll("@" + key + "#", value.toString());
                }
            } else if (value instanceof java.lang.Integer) {
                sql = sql.replaceAll("@" + key + "#", value.toString());
            } else if (value instanceof ArrayList) {
                String str = getJoinList((List) value, column);
                sql = sql.replaceAll("@" + key + "#", str);
            }
        } else {
            return sqlBuilder;
        }
        sqlBuilder.delete(0, sqlBuilder.toString().length()).append(sql);
        return sqlBuilder;
    }

    /**
     * 处理集合类型数据 处理成类似’abc','ba'的数据
     *
     * @param list   数据集合
     * @param column 列配置
     * @return 处理后的数据
     */
    private static String getJoinList(List list, Column column) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : list) {
            if (obj == null) {
                continue;
            }
            if (obj instanceof String) {
                if (column.getIsQuote())
                    sb.append("'").append(obj.toString()).append("'").append(",");
                else
                    sb.append(obj.toString()).append(",");
            }
            if (obj instanceof Integer)
                sb.append(obj.toString()).append(",");
        }
        String str = sb.toString();
        if (sb.length() > 0)
            str = sb.substring(0, sb.length() - 1);
        return str;
    }


    /**
     * 拼接criteria离线查询条件
     *
     * @param condition   查询条件
     * @param query       查询配置
     * @param entityClazz 映射类
     * @return 离线查询criteria
     */
    public static DetachedCriteria generateCriteria(QueryCondition condition, Query query, Class entityClazz) throws QueryException {
        DetachedCriteria criteria = DetachedCriteria.forClass(entityClazz);

        //排序处理
        String sortInfo = getSortInfo(condition, query);
        if (!StrUtil.isEmpty(sortInfo)) {
            if (query.getAllowPaging() && !StrUtil.isEmpty(query.getSortKey())) {
                sortInfo = sortInfo + "," + query.getSortKey();
            }
            String[] strArr = sortInfo.split(",");
            for (String str : strArr) {
                String[] order_str = str.split(" ");
                if (order_str.length != 2) {
                    throw new QueryException("排序" + str + "的配置有问题,可能未配置asc或desc，请检查");
                }
                criteria = ObjectUtil.getCriteriaWithAlias(criteria, order_str[0]);
                if ("DESC".equals(order_str[1].toUpperCase())) {
                    criteria.addOrder(Order.desc(order_str[0]));
                } else {
                    //默认为asc排序
                    criteria.addOrder(Order.asc(order_str[0]));
                }
            }
        }

        //查询条件注入
        List<Map<String, Object>> conditions = condition.getConditions();
        conditions.addAll(getServerConditions(query));
        //TODO 数据权限注入
        if (conditions.isEmpty()) {
            return criteria;
        }
        logger.debug("-------"+query.getId()+"的filter查询条件-----------");
        for (Map<String, Object> map : conditions) {
            String key = map.get("key").toString();
            Object value = map.get("value");
            Column column = query.getColumn(key);
            if (column == null) {
                column = new Column();
                column.setKey(key);
            }
            String operatorStr = getOperatorStr(map, column);
            logger.debug("condition:"+key+" "+operatorStr+" "+ value);
            ConditionOperator operator = getOperator(operatorStr);
            //类型处理
            Class clazz;
            try {
                clazz = JSonHelper.getClass(column.getClassType());
            } catch (Exception ex) {
                throw new QueryException("转化类【" + column.getClassType() + "】出现异常！");
            }

            Object newValue = value;
            if (isNeedValue(operator) && isNotEmptyValue(value)) {
               //值处理
                Map<String, Object> value_map = getValueForOperator(operator, value, clazz);
                if (value_map == null)
                    continue;
                newValue = value_map.get("value");
                operator = (ConditionOperator) value_map.get("operator");
                criteria=ObjectUtil.getCriteriaWithAlias(criteria,key);
                criteria=operator.getCriteria(criteria,key,newValue);
            } else if (!isNeedValue(operator)) {
                criteria=ObjectUtil.getCriteriaWithAlias(criteria,key);
                criteria=operator.getCriteria(criteria,key,newValue);
            }
        }

        return criteria;
    }


}
