package com.cnpc.framework.base.dao.impl;

import com.cnpc.framework.base.dao.BaseDao;
import com.cnpc.framework.base.pojo.PageInfo;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author bin
 */
@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {

    @Resource
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {

        return this.sessionFactory.getCurrentSession();
    }

    public <T> Serializable save(T obj) {

        return this.getCurrentSession().save(obj);
    }

    public <T> void delete(T obj) {

        this.getCurrentSession().delete(obj);
    }

    public <T> void update(T obj) {

        this.getCurrentSession().update(obj);
    }

    public Object saveOrUpdate(Object obj) {

        this.getCurrentSession().saveOrUpdate(obj);
        return obj;
    }

    public <T> void batchSave(List<T> entityList) {

        Session session = getCurrentSession();
        for (int i = 0; i < entityList.size(); i++) {
            T entity = entityList.get(i);
            session.save(entity);
            if (i % 20 == 0) {
                session.flush();
                session.clear();
            }
        }
    }

    public <T> void batchUpdate(List<T> entityList) {

        Session session = getCurrentSession();
        for (int i = 0; i < entityList.size(); i++) {
            T entity = entityList.get(i);
            session.update(entity);
            if (i % 20 == 0) {
                session.flush();
                session.clear();
            }
        }
    }

    public <T> void batchDelete(List<T> entityList) {

        Session session = getCurrentSession();
        for (int i = 0; i < entityList.size(); i++) {
            T entity = entityList.get(i);
            session.delete(entity);
            if (i % 20 == 0) {
                session.flush();
                session.clear();
            }
        }
    }

    public <T> void batchSaveOrUpdate(List<T> entityList) {

        Session session = getCurrentSession();
        for (int i = 0; i < entityList.size(); i++) {
            T entity = entityList.get(i);
            session.saveOrUpdate(entity);
            if (i % 20 == 0) {
                session.flush();
                session.clear();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> list(Class<T> clazz) {

        Criteria ct = this.getCurrentSession().createCriteria(clazz);
        return ct.list();
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz, Serializable id) {

        return (T) this.getCurrentSession().get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String hql) {

        Query query = this.getCurrentSession().createQuery(hql);
        List<T> ls = query.list();
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String hql, Map<String, Object> params) {

        Query query = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        List<T> ls = query.list();
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(String hql) {

        Query query = this.getCurrentSession().createQuery(hql);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(String hql, Map<String, Object> params) {

        Query query = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object obj = params.get(key);
                if (obj instanceof Collection<?>) {
                    query.setParameterList(key, (Collection<?>) obj);
                } else if (obj instanceof Object[]) {
                    query.setParameterList(key, (Object[]) obj);
                } else {
                    query.setParameter(key, obj);
                }
            }
        }
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(String hql, int page, int rows) {

        Query query = this.getCurrentSession().createQuery(hql);
        return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(String hql, Map<String, Object> params, int page, int rows) {

        Query query = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    public Long count(String hql) {

        Query query = this.getCurrentSession().createQuery(hql);
        return (Long) query.uniqueResult();
    }

    public Long count(String hql, Map<String, Object> params) {

        Query query = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return (Long) query.uniqueResult();
    }

    public int executeHql(String hql) {

        Query query = this.getCurrentSession().createQuery(hql);
        return query.executeUpdate();
    }



    public int executeHql(String hql,Map<String,Object> params){
        Query query = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object obj = params.get(key);
                if (obj instanceof Collection<?>) {
                    query.setParameterList(key, (Collection<?>) obj);
                } else if (obj instanceof Object[]) {
                    query.setParameterList(key, (Object[]) obj);
                } else {
                    query.setParameter(key, obj);
                }
            }
        }
        return query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBySql(String sql) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        List<T> ls = sqlQuery.list();
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBySql(String sql, Map<String, Object> params) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sqlQuery.setParameter(key, params.get(key));
            }
        }
        List<T> ls = sqlQuery.list();
        if (ls != null && ls.size() > 0) {
            return ls.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findBySql(String sql, Class<T> clazz) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addEntity(clazz);
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findBySql(String sql, Map<String, Object> params, Class<T> clazz) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery=getSqlQueryByMap(sqlQuery,params);
        sqlQuery.addEntity(clazz);
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findBySql(String sql, int page, int rows, Class<T> clazz) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery.addEntity(clazz);
        return sqlQuery.list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findBySql(String sql, Map<String, Object> params, int page, int rows, Class<T> clazz) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery=getSqlQueryByMap(sqlQuery,params);
        sqlQuery.addEntity(clazz);
        return sqlQuery.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    public Long countBySql(String sql) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        return ((BigInteger) sqlQuery.uniqueResult()).longValue();
    }

    public Long countBySql(String sql, Map<String, Object> params) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sqlQuery.setParameter(key, params.get(key));
            }
        }
        return (Long) sqlQuery.uniqueResult();
    }

    public int executeSql(String sql) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.executeUpdate();
    }

    public int executeSql(String sql,Map<String,Object> params){
        Query query = this.getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object obj = params.get(key);
                if (obj instanceof Collection<?>) {
                    query.setParameterList(key, (Collection<?>) obj);
                } else if (obj instanceof Object[]) {
                    query.setParameterList(key, (Object[]) obj);
                } else {
                    query.setParameter(key, obj);
                }
            }
        }
        return query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMapBySql(String sql) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery=getSqlQueryByMap(sqlQuery,params);
        return sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public SQLQuery getSqlQueryByMap(SQLQuery sqlQuery,Map<String,Object> params){
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object obj = params.get(key);
                if (obj instanceof Collection<?>)
                    sqlQuery.setParameterList(key, (Collection<?>) obj);
                else if (obj instanceof Object[])
                    sqlQuery.setParameterList(key, (Object[]) obj);
                else
                    sqlQuery.setParameter(key, obj);

            }
        }
        return sqlQuery;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMapBySql(String sql, int page, int rows) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        return sqlQuery.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findMapBySql(String sql, Map<String, Object> params, int page, int rows) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery=getSqlQueryByMap(sqlQuery,params);
        return sqlQuery.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public List findMapBySql(String sql, Map<String, Object> params, int page, int rows,Class clazz) {

        SQLQuery sqlQuery = this.getCurrentSession().createSQLQuery(sql);
        sqlQuery=getSqlQueryByMap(sqlQuery,params);
        if(clazz==null){
            sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }else{
            sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
        }
        return sqlQuery.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List findMapBySql(String sql, Object[] params, Type[] types, Class clazz) {

        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        if (clazz != null) {
            query.setResultTransformer(Transformers.aliasToBean(clazz));
        } else {
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        }
        query.setParameters(params, types);
        return query.list();
    }

    @Override
    public <T> List<T> find(String sql, Map<String,Object> params, Class<T> clazz) {

        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        query=getSqlQueryByMap(query,params);
        query.setResultTransformer(Transformers.aliasToBean(clazz));
        return query.list();
    }


    @Override
    public List findMapBySql(String sql, int firstResult, int maxResult, Object[] params, Type[] types, Class clazz) {

        if (clazz != null)
            return this.getCurrentSession().createSQLQuery(sql).setParameters(params, types).setFirstResult(firstResult)
                    .setMaxResults(maxResult).setResultTransformer(Transformers.aliasToBean(clazz)).list();
        else
            return this.getCurrentSession().createSQLQuery(sql).setParameters(params, types).setFirstResult(firstResult)
                    .setMaxResults(maxResult).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    public Long countBySql(String sql, Object[] params, Type[] types) {

        SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
        query.setParameters(params, types);
        return ((BigInteger) query.uniqueResult()).longValue();
    }

    public List<Map> findMap(String hql) {

        Query query = this.getCurrentSession().createQuery(hql);
        query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public <T> List<T> getListByCriteria(DetachedCriteria criteria, PageInfo page) {

        if (page == null) {
            return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
                    .setResultTransformer(CriteriaSpecification.ROOT_ENTITY).list();
        } else {
            return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
                    .setResultTransformer(CriteriaSpecification.ROOT_ENTITY).setFirstResult((page.getPageNum() - 1) * page.getPageSize())
                    .setMaxResults(page.getPageSize()).list();
        }
    }

    public List<?> getListByCriteria(DetachedCriteria criteria, Integer startPage, Integer pageSize) {

        if (startPage != null && pageSize != null) {
            return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
                    .setResultTransformer(CriteriaSpecification.ROOT_ENTITY).setFirstResult(startPage).setMaxResults(pageSize).list();
        } else {
            return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
                    .setResultTransformer(CriteriaSpecification.ROOT_ENTITY).list();
        }
    }

    public <T> List<T> findByCriteria(DetachedCriteria criteria) {

        return criteria.getExecutableCriteria(getCurrentSession()).setProjection(null)
                .setResultTransformer(CriteriaSpecification.ROOT_ENTITY).list();
    }

    public int getCountByCriteria(DetachedCriteria criteria) {

        return ((Long) criteria.getExecutableCriteria(getCurrentSession()).setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    public List findByExample(Object example) {

        return this.getCurrentSession().createCriteria(example.getClass()).add(Example.create(example)).list();
    }

    public List findByExample(Object example, String condition, boolean enableLike) {

        Criteria ec = this.getCurrentSession().createCriteria(example.getClass());
        if (enableLike)
            ec.add(Example.create(example).enableLike());
        else
            ec.add(Example.create(example));
        if (condition != null && !condition.equals("")) {
            String newCondition = condition.replaceAll("`", "'");
            ec.add(Restrictions.sqlRestriction(newCondition));
        }
        return ec.list();
    }

    public Object getMaxByExample(final Object exampleEntity, final String maxProperty, final String condition, final boolean enableLike) {

        Criteria executableCriteria = this.getCurrentSession().createCriteria(exampleEntity.getClass());
        executableCriteria.setProjection(Projections.max(maxProperty));
        if (enableLike) {
            executableCriteria.add(Example.create(exampleEntity).enableLike());
        } else {
            executableCriteria.add(Example.create(exampleEntity));
        }
        if (condition != null && !condition.equals("")) {
            String newCondition = condition.replaceAll("`", "'");
            executableCriteria.add(Restrictions.sqlRestriction(newCondition));
        }
        return executableCriteria.uniqueResult();
    }
}
