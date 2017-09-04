package com.cnpc.framework.query.filter;

public class ExistFilter extends BaseFilter {

	public ExistFilter(StringFilter selectQuery, boolean notFlag) {
		filterFragments.add(selectQuery);
		setOperator(notFlag);
	}

	private void setOperator(boolean notFlag) {
		operator = notFlag ? ConditionOperator.NOT_EXISTS
				: ConditionOperator.EXISTS;
	}
}