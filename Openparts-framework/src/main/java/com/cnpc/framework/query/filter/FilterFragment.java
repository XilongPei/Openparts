package com.cnpc.framework.query.filter;

public abstract class FilterFragment implements IFilter {

	public FilterFragment() {
	}

	public int getFilterType() {
		return 1;
	}

	public IFilter appendAnd(IFilter filter) throws RuntimeException {
		throw new RuntimeException();
	}

	public IFilter appendOr(IFilter filter) throws RuntimeException {
		throw new RuntimeException();
	}

	public IFilter append(IFilter filter, ConditionOperator conjunction)
			throws RuntimeException {
		throw new RuntimeException();
	}
}
