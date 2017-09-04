package com.cnpc.framework.query.filter;

import java.util.List;

public class FilterFactory extends FilterFactoryNext {

	public FilterFactory() {
	}

	public static IFilter getBetweenFilter(String name, Object val1, Object val2) {
		return getBetween(name, val1, val2);
	}

	public static IFilter getBetweenFilter(String alias, String name,
			Object val1, Object val2) {
		return getBetween(alias, name, val1, val2);
	}

	public static IFilter getExistFilter(String selectQuery) {
		return getExist(selectQuery);
	}

	public static IFilter getNotExistFilter(String selectQuery) {
		return getNotExist(selectQuery);
	}

	public static IFilter getInFilter(String name, List values) {
		return getInList(name, values);
	}

	public static IFilter getInFilter(String alias, String name, List values) {
		return getInList(alias, name, values);
	}

	public static IFilter getNotInFilter(String name, List values) {
		return getNotInList(name, values);
	}

	public static IFilter getNotInFilter(String alias, String name, List values) {
		return getNotInList(alias, name, values);
	}

	public static IFilter getInFilter(String name, String selectQuery) {
		return getInQuery(name, selectQuery);
	}

	public static IFilter getInFilter(String alias, String name,
			String selectQuery) {
		return getInQuery(alias, name, selectQuery);
	}

	public static IFilter getNotInFilter(String name, String selectQuery) {
		return getNotInQuery(name, selectQuery);
	}

	public static IFilter getNotInFilter(String alias, String name,
			String selectQuery) {
		return getNotInQuery(alias, name, selectQuery);
	}

	public static IFilter getIsNotNullFilter(String name) {
		return getIsNotNull(name);
	}

	public static IFilter getIsNotNullFilter(String alias, String name) {
		return getIsNotNull(alias, name);
	}

	public static IFilter getIsNullFilter(String name) {
		return getIsNull(name);
	}

	public static IFilter getIsNullFilter(String alias, String name) {
		return getIsNull(alias, name);
	}

	public static IFilter getSimpleFilter(String strCondition) {
		return getStringFilter(strCondition);
	}

	public static IFilter getSimpleFilter(String name, Object val) {
		return getEq(name, val);
	}

	/**
	 * @deprecated Method getSimpleFilter is deprecated
	 */
	public static IFilter getSimpleFilter(String logicName, Object name,
			Object val) {
		if (val instanceof ConditionOperator)
			return getFilter(logicName, name, (ConditionOperator) val);
		if (name instanceof String)
			return getEq(logicName, (String) name, val);
		else
			return getEq(logicName, name);
	}

	public static IFilter getSimpleFilter(String alias, String name,
			Object val, ConditionOperator op) {
		return getFilter(alias, name, val, op);
	}
}