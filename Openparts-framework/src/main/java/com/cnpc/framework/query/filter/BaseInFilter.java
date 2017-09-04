package com.cnpc.framework.query.filter;

public abstract class BaseInFilter extends BaseFilter {

    public BaseInFilter(FilterVariable left, boolean notFlag) {

        filterFragments.add(left);
        setOperator(notFlag);
    }

    protected void setOperator(boolean notFlag) {

        operator = notFlag ? ConditionOperator.NOT_IN : ConditionOperator.IN;
    }
}
