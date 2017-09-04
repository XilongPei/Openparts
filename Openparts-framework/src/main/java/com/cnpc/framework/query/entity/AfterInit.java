package com.cnpc.framework.query.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester3.annotations.rules.ObjectCreate;
import org.apache.commons.digester3.annotations.rules.SetNext;

@ObjectCreate(pattern="queryContext/query/afterInit")
public class AfterInit {	
	List<AfterCall> afterCallList;
	public AfterInit(){
		afterCallList=new ArrayList<AfterCall>();
	}
	@SetNext
	public void addAfterCall(AfterCall call){
		afterCallList.add(call);
	}
	public List<AfterCall> getAfterCallList() {
		return afterCallList;
	}
	
	
}
