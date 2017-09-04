package com.cnpc.framework.query.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleFilter extends BaseFilter {

	public SimpleFilter(ConditionOperator operator, IFilter leftHand,
			IFilter rightHand) {
		this.operator = operator;
		filterFragments.add(leftHand);
		filterFragments.add(rightHand);
	}

	public List getValues() throws RuntimeException {
		List ret = new ArrayList();
		IFilter filter;
		for (Iterator i$ = filterFragments.iterator(); i$.hasNext(); ret
				.addAll(filter.getValues()))
			filter = (IFilter) i$.next();
		return ret;
	}
}