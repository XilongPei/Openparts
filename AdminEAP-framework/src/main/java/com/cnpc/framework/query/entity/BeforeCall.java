package com.cnpc.framework.query.entity;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;
import org.apache.commons.digester3.annotations.rules.SetProperty;
/**
 * 执行方法
 * @author jrn
 *
 */
@ObjectCreate(pattern = "queryContext/query/beforeInit/call")
public class BeforeCall {
	/**
	 * 方法名
	 */
	@SetProperty(attributeName="command",pattern="queryContext/query/beforeInit/call")
	private String command;
	/**
	 * 方法参数
	 */
	@BeanPropertySetter(pattern="queryContext/query/beforeInit/call/param")
	private String param;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
