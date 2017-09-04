package com.cnpc.framework.query.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class BaseFilter implements IFilter {

    protected ConditionOperator operator;

    protected List filterFragments;

    public BaseFilter() {

        filterFragments = new ArrayList(3);
    }

    public String getString() throws RuntimeException {

        String result = operator.getString();
        for (int i = filterFragments.size() - 1; i >= 0; i--) {
            IFilter filter = (IFilter) filterFragments.get(i);
            result = result.replace((new StringBuilder()).append("%").append(i).toString(), filter.getString());
        }

        return result;
    }

    public List getValues() throws RuntimeException {

        return Collections.EMPTY_LIST;
    }

    public int getFilterType() {

        return 2;
    }

    public IFilter appendAnd(IFilter filter) throws RuntimeException {

        return append(filter, ConditionOperator.AND);
    }

    public IFilter appendOr(IFilter filter) throws RuntimeException {

        return append(filter, ConditionOperator.OR);
    }

    public IFilter append(IFilter filter, ConditionOperator operator) throws RuntimeException {

        IFilter ret = this;
        if (filter != null)
            ret = new SimpleFilter(operator, this, filter);
        return ret;
    }

    public void setTableAlias(String alias) {

        IFilter filterFragment;
        for (Iterator i$ = filterFragments.iterator(); i$.hasNext(); filterFragment.setTableAlias(alias))
            filterFragment = (IFilter) i$.next();

    }

}
