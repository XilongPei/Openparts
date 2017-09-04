package com.cnpc.framework.query.filter;

public class NullFilter extends BaseFilter {

	public NullFilter(String name, boolean notFlag) {
		this(null, name, notFlag);
	}

	public NullFilter(String alias, String name, boolean notFlag) {
		FilterVariable property = new FilterVariable(alias, name);
		filterFragments.add(property);
		setOperator(notFlag);
	}

	private void setOperator(boolean notFlag) {
		operator = notFlag ? ConditionOperator.NOT_NULL
				: ConditionOperator.NULL;
	}
}