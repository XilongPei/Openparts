package com.cnpc.framework.query.filter;

import java.util.List;
 
public interface IFilter {
	public static final int TYPE_ELEMENT = 1;
	public static final int TYPE_FILTER = 2;
	public static final String DEFAULT_ALIAS = "{alias}";

	public abstract IFilter appendAnd(IFilter ifilter) throws RuntimeException;

	public abstract IFilter appendOr(IFilter ifilter) throws RuntimeException;

	public abstract IFilter append(IFilter ifilter,ConditionOperator conditionoperator) throws RuntimeException;

	public abstract int getFilterType();

	public abstract String getString() throws RuntimeException;

	public abstract List getValues() throws RuntimeException;

	public abstract void setTableAlias(String s);

}