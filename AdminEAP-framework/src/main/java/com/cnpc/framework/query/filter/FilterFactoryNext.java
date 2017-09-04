package com.cnpc.framework.query.filter;


import java.util.List;
public class FilterFactoryNext
{

 public FilterFactoryNext()
 {
 }

 public static IFilter getBetween(String name, Object val1, Object val2)
 {
     return getBetween(null, name, val1, val2);
 }

 public static IFilter getBetween(String alias, String name, Object v1, Object v2)
 {
     FilterVariable left = new FilterVariable(alias, name);
     FilterConstant right1 = new FilterConstant(v1);
     FilterConstant right2 = new FilterConstant(v2);
     BetweenFilter filter = new BetweenFilter(left, right1, right2);
     return filter;
 }

 public static IFilter getExist(String selectQuery)
 {
     return new ExistFilter(new StringFilter(selectQuery), false);
 }

 public static IFilter getNotExist(String selectQuery)
 {
     return new ExistFilter(new StringFilter(selectQuery), true);
 }

 public static IFilter getInList(String name, List values)
 {
     return getInList(null, name, values);
 }

 public static IFilter getInList(String alias, String name, List values)
 {
     return new InListFilter(new FilterVariable(alias, name), values, false);
 }

 public static IFilter getNotInList(String name, List values)
 {
     return getNotInList(null, name, values);
 }

 public static IFilter getNotInList(String alias, String name, List values)
 {
     return new InListFilter(new FilterVariable(alias, name), values, true);
 }

 public static IFilter getInQuery(String name, String selectQuery)
 {
     return getInQuery(null, name, selectQuery);
 }

 public static IFilter getInQuery(String alias, String name, String selectQuery)
 {
     return new InQueryFilter(new FilterVariable(alias, name), new StringFilter(selectQuery), false);
 }

 public static IFilter getNotInQuery(String name, String selectQuery)
 {
     return getNotInQuery(null, name, selectQuery);
 }

 public static IFilter getNotInQuery(String alias, String name, String selectQuery)
 {
     return new InQueryFilter(new FilterVariable(alias, name), new StringFilter(selectQuery), true);
 }

 public static IFilter getIsNull(String name)
 {
     return getIsNull(null, name);
 }

 public static IFilter getIsNull(String alias, String name)
 {
     return new NullFilter(alias, name, false);
 }

 public static IFilter getIsNotNull(String name)
 {
     return getIsNotNull(null, name);
 }

 public static IFilter getIsNotNull(String alias, String name)
 {
     return new NullFilter(alias, name, true);
 }

 public static IFilter getEq(String name, Object value)
 {
     return getEq(null, name, value);
 }

 public static IFilter getEq(String alias, String name, Object value)
 {
     return getFilter(alias, name, value, ConditionOperator.EQ);
 }

 public static IFilter getFilter(String name, Object val, ConditionOperator op)
 {
     return getFilter(null, name, val, op);
 }

 public static IFilter getFilter(String alias, String name, Object val, ConditionOperator op)
 {
     FilterVariable left = new FilterVariable(alias, name);
     FilterConstant right = new FilterConstant(val);
     IFilter filter = new SimpleFilter(op, left, right);
     return filter;
 }

 public static IFilter getStringFilter(String condition)
 {
     StringFilter ret = new StringFilter(condition);
     return ret;
 }

 public static  IFilter getAndConjunctFilters(IFilter filters[])
 {
     LogicConjunctFilter ret = new LogicConjunctFilter("and", filters);
     return ret;
 }

 public static  IFilter getOrConjunctFilters(IFilter filters[])
 {
     LogicConjunctFilter ret = new LogicConjunctFilter("or", filters);
     return ret;
 }
}