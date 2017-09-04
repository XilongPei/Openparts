package com.cnpc.framework.query.filter;

import java.util.ArrayList;
import java.util.List;
public class LogicConjunctFilter extends BaseFilter {

	private static final String AND = " AND ";
	private static final String OR = " OR ";
	private IFilter filters[];
	private String logic;
	public LogicConjunctFilter(){
		
	}
	public  LogicConjunctFilter(String logic, IFilter filters[]) {
		this.logic = " AND ";
		if ("or".equalsIgnoreCase(logic))
			this.logic = " OR ";
		this.filters = filters;
	}

	public String getString() throws RuntimeException {
		StringBuffer result = new StringBuffer("( ");
		result.append(filters[0].getString());
		for (int i = 1; i < filters.length; i++)
			result.append(logic).append(filters[i].getString());

		result.append(" )");
		return result.toString();
	}

	public List getValues() throws RuntimeException {
		List ret = new ArrayList();
		IFilter arr$[] = filters;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			IFilter filter = arr$[i$];
			ret.addAll(filter.getValues());
		}

		return ret;
	}

	
}