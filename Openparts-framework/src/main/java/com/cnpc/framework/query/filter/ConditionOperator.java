package com.cnpc.framework.query.filter;

import com.cnpc.framework.exception.QueryException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionOperator {
    private final String operator;
    private static final Map cachedOperators = new HashMap();

    private ConditionOperator(String operator) {
        this.operator = operator;
    }

    public String getString() {
        return operator;
    }

    public IFilter getFilter(String key, Object value) throws RuntimeException {
        return FilterFactory.getFilter(key, value, this);
    }


    public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value) throws QueryException {
        throw new QueryException(this.operator + "的getCriteria方法未实现");
    }

    public IFilter getFilter(String alias, String key, Object value) throws RuntimeException {
        IFilter filter = getFilter(key, value);
        filter.setTableAlias(alias);
        return filter;
    }

    public static ConditionOperator getOperator(String operator) throws RuntimeException {
        operator = operator.toUpperCase();
        ConditionOperator op = (ConditionOperator) cachedOperators
                .get(operator);
        if (op == null)
            try {
                Field field = ConditionOperator.class.getField(operator);
                op = (ConditionOperator) field.get(null);
                cachedOperators.put(operator, op);
            } catch (Exception e) {
                throw new RuntimeException((new StringBuilder()).append("Unknown operator: ").append(operator).toString(), e);
            }
        return op;
    }

    public static final ConditionOperator LIKE = new ConditionOperator(
            "%0 LIKE %1") {

        @Override
        public IFilter getFilter(String key, Object value)
                throws RuntimeException {
            String string = String.valueOf(value);
            StringBuffer tmpSb = new StringBuffer(string);
            if (!string.startsWith("%") && !string.endsWith("%")) {
                if (string.startsWith("*"))
                    tmpSb.replace(0, 1, "%");
                if (string.endsWith("*"))
                    tmpSb.replace(tmpSb.length() - 1, tmpSb.length(), "%");
            }
            string = tmpSb.toString();
            return super.getFilter(key, string);
        }

        final String DEFAULT_LIKE_SYMBOL = "%";
        final String CUSTOM_LIKE_SYMBOL = "*";


        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value)
                throws QueryException {
            criteria.add(Restrictions.like(key, value));
            return criteria;
        }

    };
    public static final ConditionOperator EQ = new ConditionOperator("%0 = %1") {
        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value)
                throws QueryException {
            criteria.add(Restrictions.eq(key, value));
            return criteria;
        }
    };
    public static final ConditionOperator NOT_EQ = new ConditionOperator(
            "%0 <> %1") {
        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value)
                throws QueryException {
            criteria.add(Restrictions.not(Restrictions.eq(key, value)));
            return criteria;
        }
    };
    public static final ConditionOperator GT = new ConditionOperator("%0 > %1");
    public static final ConditionOperator LT = new ConditionOperator("%0 < %1");
    public static final ConditionOperator GE = new ConditionOperator("%0 >= %1") {
        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value)
                throws QueryException {
            criteria.add(Restrictions.ge(key, value));
            return criteria;
        }
    };
    public static final ConditionOperator LE = new ConditionOperator("%0 <= %1");
    public static final ConditionOperator IN = new ConditionOperator(
            "%0 IN (%1)") {

        @Override
        public IFilter getFilter(String key, Object value)
                throws RuntimeException {
            List values = (List) value;
            return FilterFactory.getInFilter(key, values);
        }

        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value)
                throws QueryException {
            criteria.add(Restrictions.in(key, (List) value));
            return criteria;
        }

    };
    public static final ConditionOperator NOT_IN = new ConditionOperator(
            "%0 NOT IN (%1)") {

        @Override
        public IFilter getFilter(String key, Object value)
                throws RuntimeException {
            List values = (List) value;
            return FilterFactory.getNotInFilter(key, values);
        }

        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value)
                throws QueryException {
            criteria.add(Restrictions.not(Restrictions.in(key, (List) value)));
            return criteria;
        }

    };
    public static final ConditionOperator BETWEEN = new ConditionOperator(
            "%0 BETWEEN %1 AND %2") {

        @Override
        public IFilter getFilter(String key, Object value)
                throws RuntimeException {
            IFilter filter = null;
            List values = (List) value;
            if (values.size() == 2) {
                if (values.get(0) == null) {
                    if (values.get(1) != null)
                        filter = FilterFactory
                                .getFilter(key, values.get(1), LE);
                } else if (values.get(1) == null)
                    filter = FilterFactory.getFilter(key, values.get(0), GE);
                else
                    filter = FilterFactory.getBetween(key, values.get(0),
                            values.get(1));
            }
            if (filter != null)
                return filter;
            else
                throw new RuntimeException((new StringBuilder()).append(
                        "Unimplemented create filter Between: key,value: ")
                        .append(key).append(",").append(value).toString());
        }


        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value) throws QueryException {
            List values=(List) value;
            if(values.size()==2){
                if(values.get(0)==null){
                    if(values.get(1)!=null)
                    criteria.add(Restrictions.le(key,values.get(1)));
                }else if(values.get(1)==null){
                    criteria.add(Restrictions.ge(key,values.get(0)));
                }else{
                    criteria.add(Restrictions.between(key,values.get(0),values.get(1)));
                }
            }
            return criteria;
        }
    };
    public static final ConditionOperator NULL = new ConditionOperator(
            "%0 IS NULL") {
        @Override
        public IFilter getFilter(String key, Object value) throws RuntimeException {
            return FilterFactory.getIsNullFilter(key);
        }

        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value)
                throws QueryException {
            criteria.add(Restrictions.isNull(key));
            return criteria;
        }
    };
    public static final ConditionOperator NOT_NULL = new ConditionOperator(
            "%0 IS NOT NULL") {
        @Override
        public IFilter getFilter(String key, Object value) throws RuntimeException {
            return FilterFactory.getIsNotNullFilter(key);
        }

        @Override
        public DetachedCriteria getCriteria(DetachedCriteria criteria, String key, Object value)
                throws QueryException {
            criteria.add(Restrictions.isNotNull(key));
            return criteria;
        }
    };
    public static final ConditionOperator EXISTS = new ConditionOperator(
            "EXISTS (%0)");
    public static final ConditionOperator NOT_EXISTS = new ConditionOperator(
            "NOT EXISTS (%0)");
    public static final ConditionOperator AND = new ConditionOperator(
            "(%0) AND (%1)") {

        @Override
        public IFilter getFilter(String key, Object value)
                throws RuntimeException {
            throw new RuntimeException((new StringBuilder()).append(
                    "Unimplemented get filter for this Operator: ")
                    .append(this).toString());
        }

    };
    public static final ConditionOperator OR = new ConditionOperator(
            "(%0) OR (%1)") {

        @Override
        public IFilter getFilter(String key, Object value)
                throws RuntimeException {
            throw new RuntimeException((new StringBuilder()).append(
                    "Unimplemented get filter for this Operator: ")
                    .append(this).toString());
        }

    };


}