package com.openparts.common;

public enum OP_Errors {
	SUCCESS(0, "请求成功"),
	SYSBUSY(-1,	"系统繁忙，此时请开发者稍候再试"),
    FAIL(-2, "请求失败");

	private final int errno;
	private final String errInfo;

	OP_Errors(int errno, String errInfo) {
		this.errno = errno;
		this.errInfo = errInfo;
	}
}
