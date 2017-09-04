package com.cnpc.framework.query.filter;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BetweenFilter extends BaseFilter
{

 public BetweenFilter(FilterVariable left, FilterConstant right1, FilterConstant right2)
 {
     filterFragments.add(left);
     filterFragments.add(right1);
     filterFragments.add(right2);
     operator = ConditionOperator.BETWEEN;
 }

 public List getValues() throws RuntimeException
 {
     List ret = new ArrayList();
     IFilter filter;
     for(Iterator i$ = filterFragments.iterator(); i$.hasNext(); ret.addAll(filter.getValues()))
         filter = (IFilter)i$.next();
     return ret;
 }
}