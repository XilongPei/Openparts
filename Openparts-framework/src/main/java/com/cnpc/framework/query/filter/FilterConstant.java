package com.cnpc.framework.query.filter;

import java.util.Arrays;
import java.util.List;

public class FilterConstant extends FilterFragment {

	public FilterConstant(Object value) {
		this.value = value;
	}

	public String getString() {
		return "?";
	}

	public List getValues() {
		List ret = Arrays.asList(new Object[] { value });
		return ret;
	}

	public void setTableAlias(String s) {
	}

	private Object value;
}