package com.cnpc.framework.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;

import com.cnpc.framework.base.pojo.PageInfo;

/**
 * @author bin
 */
public interface BaseDao {

    /**
     * 保存对象
     *
     * @param obj 所要保存的对象
     * @return 唯一主键
     */
    <T> Serializable save(T obj);

    /**
     * 删除对象
     *
     * @param obj 所要删除的对象
     */
    <T> void delete(T obj);

    /**
     * 修改对象
     *
     * @param obj 所要修改的对象
     */
    <T> void update(T obj);

    /**
     * 保存或修改
     *
     * @param obj 所要修改的对象
     */
    Object saveOrUpdate(Object obj);

    /**
     * 批量保存
     */
    <T> void batchSave(List<T> entityList);

    /**
     * 批量更新
     */
    <T> void batchUpdate(List<T> entityList);

    /**
     * 批量删除
     */
    <T> void batchDelete(List<T> entityList);

    /**
     * 批量保存或修改
     */
    <T> void batchSaveOrUpdate(List<T> entityList);

    /**
     * 查询此对象所有数据
     *
     * @param clazz
     * @return
     */
    <T> List<T> list(Class<T> clazz);

    /**
     * 根据主键获取对象
     *
     * @param clazz 所要获取对象的类
     * @param id    主键
     * @return
     */
    <T> T get(Class<T> clazz, Serializable id);

    /**
     * hql语句查询单个实体对象
     *
     * @param hql 查询语句
     * @return 实体对象
     */
    <T> T get(String hql);

    /**
     * hql语句带条件查询单个实体对象
     *
     * @param hql    查询语句
     * @param params 条件参数
     * @return 实体对象
     */
    <T> T get(String hql, Map<String, Object> params);

    /**
     * hql语句查询实体集合
     *
     * @param hql 查询
     * @return
     */
    <T> List<T> find(String hql);

    List<Map> findMap(String hql);

    /**
     * hql语句带条件查询实体集合
     *
     * @param hql    查询语句
     * @param params 条件参数
     * @return
     */
    <T> List<T> find(String hql, Map<String, Object> params);

    /**
     * hql语句分页查询实体集合
     *
     * @param hql  查询语句
     * @param page 当前页号
     * @param rows 行数
     * @return
     */
    <T> List<T> find(String hql, int page, int rows);

    /**
     * hql语句带条件分页查询实体集合
     *
     * @param hql    查询语句
     * @param params 条件参数
     * @param page   当前页号
     * @param rows   行数
     * @return
     */
    <T> List<T> find(String hql, Map<String, Object> params, int page, int rows);

    /**
     * hql语句查询记录数
     *
     * @param hql 查询语句
     * @return
     */
    Long count(String hql);

    /**
     * hql语句带条件查询记录数
     *
     * @param hql    查询语句
     * @param params 条件参数
     * @return
     */
    Long count(String hql, Map<String, Object> params);

    /**
     * 执行hql语句（可带事务）
     *
     * @param hql 查询语句
     * @return
     */
    int executeHql(String hql);

    int executeHql(String hql, Map<String, Object> params);

    /**
     * sql查询获取实体对象
     *
     * @param sql 查询语句
     * @return
     */
    <T> T getBySql(String sql);

    /**
     * sql带条件查询获取实体对象
     *
     * @param sql    查询语句
     * @param params 条件参数
     * @return
     */
    <T> T getBySql(String sql, Map<String, Object> params);

    /**
     * sql查询实体结果集
     *
     * @param sql   查询语句
     * @param clazz 类对象
     * @return
     */
    <T> List<T> findBySql(String sql, Class<T> clazz);

    /**
     * sql带条件查询实体结果集
     *
     * @param sql    查询语句
     * @param params 条件参数
     * @param clazz  类对象
     * @return
     */
    <T> List<T> findBySql(String sql, Map<String, Object> params, Class<T> clazz);

    /**
     * sql分页查询结果集
     *
     * @param sql   查询语句
     * @param page  当前页
     * @param rows  行数
     * @param clazz 类对象
     * @return
     */
    <T> List<T> findBySql(String sql, int page, int rows, Class<T> clazz);

    /**
     * sql分页查询结果集
     *
     * @param sql    查询语句
     * @param params 条件参数
     * @param page   当前页
     * @param rows   行数
     * @param clazz  类对象
     * @return
     */
    <T> List<T> findBySql(String sql, Map<String, Object> params, int page, int rows, Class<T> clazz);

    /**
     * sql查询Map结果集
     *
     * @param sql 查询语句
     * @return
     */
    List<Map<String, Object>> findMapBySql(String sql);

    /**
     * sql带条件查询Map结果集
     *
     * @param sql    查询语句
     * @param params 条件参数
     * @return
     */
    List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params);

    <T> List<T> find(String sql, Map<String, Object> params, Class<T> clazz);

    /**
     * sql分页查询Map结果集
     *
     * @param sql  查询语句
     * @param page 当前页号
     * @param rows 行数
     * @return
     */
    List<Map<String, Object>> findMapBySql(String sql, int page, int rows);

    /**
     * sql带条件分页查询Map结果集
     *
     * @param sql    查询语句
     * @param params 条件参数
     * @param page   当前页
     * @param rows   行数
     * @return
     */
    List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params, int page, int rows);

    /**
     * sql查询记录数
     *
     * @param sql 查询语句
     * @return
     */
    Long countBySql(String sql);

    /**
     * sql带条件查询记录数
     *
     * @param sql    查询语句
     * @param params 条件参数
     * @return
     */
    Long countBySql(String sql, Map<String, Object> params);

    /**
     * 执行sql语句（带事务）
     *
     * @param sql 执行语句
     * @return
     */
    int executeSql(String sql);

    int executeSql(String sql,Map<String,Object> param);

    <T> List<T> getListByCriteria(DetachedCriteria criteria, PageInfo page);

    List<?> getListByCriteria(DetachedCriteria criteria, Integer startPage, Integer pageSize);

    int getCountByCriteria(DetachedCriteria criteria);

    List findByExample(Object example);

    <T> List<T> findByCriteria(DetachedCriteria criteria);

    List findMapBySql(String sql, int firstResult, int maxResult, Object[] params, Type[] types, Class clazz);

    List findMapBySql(String sql, Map<String, Object> params, int page, int rows, Class clazz);

    Long countBySql(String sql, Object[] params, Type[] types);

    List findMapBySql(String sql, Object[] params, Type[] types, Class clazz);

    List findByExample(Object example, String condition, boolean enableLike);

    Object getMaxByExample(Object exampleEntity, String maxProperty, String condition, boolean enableLike);

}
