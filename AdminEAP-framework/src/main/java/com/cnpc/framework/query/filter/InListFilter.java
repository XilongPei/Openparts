package com.cnpc.framework.query.filter;

import java.util.List;
public class InListFilter extends BaseInFilter {

	public InListFilter(FilterVariable left, List values, boolean notFlag) {
		super(left, notFlag);
		this.values = values;
	}

	public String getString() throws RuntimeException {
		String result = super.getString();
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (int i = values.size() - 1; i >= 0; i--)
			if (first) {
				sb.append("?");
				first = false;
			} else {
				sb.append(", ?");
			}

		return result.replace("%1", sb.toString());
	}

	public List getValues() {
		return values;
	}

	private final List values;
}