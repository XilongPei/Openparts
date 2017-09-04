package com.cnpc.framework.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cnpc.framework.base.dao.RedisDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.springframework.stereotype.Service;

import com.cnpc.framework.base.dao.BaseDao;
import com.cnpc.framework.base.pojo.PageInfo;
import com.cnpc.framework.base.service.BaseService;
import com.cnpc.framework.utils.StrUtil;

/**
 *
 *
 */
@Service("baseService")
public class BaseServiceImpl implements BaseService {

    @Resource
    public BaseDao baseDao;

    @Resource
    public RedisDao redisDao;

    public <T> Serializable save(T obj) {

        return baseDao.save(obj);
    }

    public <T> void delete(T obj) {

        baseDao.delete(obj);
    }

    public <T> void update(T obj) {

        baseDao.update(obj);
    }

    public Object saveOrUpdate(Object obj) {

        return baseDao.saveOrUpdate(obj);
    }

    public <T> void batchSave(List<T> entityList) {

        baseDao.batchSave(entityList);
    }

    public <T> void batchUpdate(List<T> entityList) {

        baseDao.batchUpdate(entityList);
    }

    public <T> void batchDelete(List<T> entityList) {

        baseDao.batchDelete(entityList);
    }

    public <T> void batchSaveOrUpdate(List<T> entityList) {

        baseDao.batchSaveOrUpdate(entityList);
    }

    public <T> List<T> list(Class<T> clazz) {

        return baseDao.list(clazz);
    }

    public <T> T get(Class<T> clazz, Serializable id) {

        return baseDao.get(clazz, id);
    }

    public <T> T get(String hql) {

        return baseDao.get(hql);
    }

    public <T> T get(String hql, Map<String, Object> params) {

        return baseDao.get(hql, params);
    }

    public <T> List<T> find(String hql) {

        return baseDao.find(hql);
    }

    public <T> List<T> find(String hql, Map<String, Object> params) {

        return baseDao.find(hql, params);
    }

    public <T> List<T> find(String hql, int page, int rows) {

        return baseDao.find(hql, page, rows);
    }

    public <T> List<T> find(String hql, Map<String, Object> params, int page, int rows) {

        return baseDao.find(hql, params, page, rows);
    }

    public long count(String hql) {

        // return baseDao.count(hql);

        Long count = baseDao.count(hql);
        ;
        if (count == null) {
            return 0;
        }
        return count;

    }

    public Long count(String hql, Map<String, Object> params) {

        return baseDao.count(hql, params);
    }

    public int executeHql(String hql) {

        return baseDao.executeHql(hql);
    }

    public int executeHql(String hql, Map<String, Object> params) {
        return baseDao.executeHql(hql, params);
    }

    public <T> T getBySql(String sql) {

        return baseDao.getBySql(sql);
    }

    public <T> T getBySql(String sql, Map<String, Object> params) {

        return baseDao.getBySql(sql, params);
    }

    public <T> List<T> findBySql(String sql, Class<T> clazz) {

        return baseDao.findBySql(sql, clazz);
    }

    public <T> List<T> findBySql(String sql, Map<String, Object> params, Class<T> clazz) {

        return baseDao.findBySql(sql, params, clazz);
    }

    public <T> List<T> findBySql(String sql, int page, int rows, Class<T> clazz) {

        return baseDao.findBySql(sql, page, rows, clazz);
    }

    public <T> List<T> findBySql(String sql, Map<String, Object> params, int page, int rows, Class<T> clazz) {

        return baseDao.findBySql(sql, params, page, rows, clazz);
    }

    public List<Map<String, Object>> findMapBySql(String sql) {

        return baseDao.findMapBySql(sql);
    }

    public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params) {

        return baseDao.findMapBySql(sql, params);
    }

    public List<Map<String, Object>> findMapBySql(String sql, int page, int rows) {

        return baseDao.findMapBySql(sql, page, rows);
    }

    public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params, int page, int rows) {

        return baseDao.findMapBySql(sql, params, page, rows);
    }

    public List findMapBySql(String sql, Object[] params, Type[] types, Class clazz) {

        return baseDao.findMapBySql(sql, params, types, clazz);
    }

    public <T> List<T> find(String sql, Map<String, Object> params, Class<T> clazz) {
        return baseDao.find(sql, params, clazz);
    }

    public List findMapBySql(String sql, String countStr, PageInfo pageInfo, Object[] params, Type[] types, Class clazz) {

        if (StrUtil.isEmpty(countStr))
            countStr = "count(*)";
        String countSql = "select " + countStr + " from (" + sql + ") as table_alias";// .substring(sql.toLowerCase().indexOf("from"));
        int count = this.countBySql(countSql, params, types).intValue();
        pageInfo.setCount(count);
        return baseDao
                .findMapBySql(sql, pageInfo.getPageSize() * (pageInfo.getPageNum() - 1), pageInfo.getPageSize(), params, types, clazz);
    }

    public Long countBySql(String sql, Object[] params, Type[] types) {

        return baseDao.countBySql(sql, params, types);
    }

    public Long countBySql(String sql) {

        return baseDao.countBySql(sql);
    }

    public Long countBySql(String sql, Map<String, Object> params) {

        return baseDao.countBySql(sql, params);
    }

    public int executeSql(String sql) {

        return baseDao.executeSql(sql);
    }

    public int executeSql(String sql,Map<String,Object> params) {

        return baseDao.executeSql(sql,params);
    }

    public <T> List<T> getListByCriteria(DetachedCriteria criteria, PageInfo page) {

        return baseDao.getListByCriteria(criteria, page);
    }

    public List<?> getListByCriteria(DetachedCriteria criteria, Integer startPage, Integer pageSize) {

        return baseDao.getListByCriteria(criteria, startPage, pageSize);
    }

    public int getCountByCriteria(DetachedCriteria criteria) {

        return baseDao.getCountByCriteria(criteria);
    }

    public List findByExample(Object example) {

        return baseDao.findByExample(example);
    }

    public List findByExample(Object example, String condition, boolean enableLike) {

        return baseDao.findByExample(example, condition, enableLike);
    }

    public boolean isExist(String hql, Map<String, Object> param) {

        return count(hql, param) > 0;
    }

    public <T> List<T> findByCriteria(DetachedCriteria criteria) {

        return baseDao.findByCriteria(criteria);

    }

    public Object getMaxByExample(Object exampleEntity, String maxProperty, String condition, boolean enableLike) {
        return baseDao.getMaxByExample(exampleEntity, maxProperty, condition, enableLike);
    }


    //redis接口通用方法
    public void deleteCacheByKey(String key) {
        redisDao.delete(key);
    }

    public <T> boolean addCacheByKey(String key, T object) {
        return redisDao.add(key, object);
    }

    public <T> boolean saveCacheByKey(String key, T object) {
        return redisDao.save(key, object);
    }

    public String getCacheByKey(String key) {
        return redisDao.get(key);
    }

    public <T> T getCacheByKey(String key, Class clazz) {
        return redisDao.get(key, clazz);
    }

    public List findMapBySql(String sql, Map<String, Object> params, int page, int rows, Class clazz) {
        return baseDao.findMapBySql(sql, params, page, rows, clazz);
    }
}
