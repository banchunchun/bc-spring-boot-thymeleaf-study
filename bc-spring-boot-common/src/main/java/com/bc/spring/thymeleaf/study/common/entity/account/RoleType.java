/**
 * Project Name:gd-common
 * File Name:RoleType.java
 * Package Name:com.jinzai.model.account
 * Date:2015年5月8日上午11:01:59
 * Copyright (c) 2015, yaoshu@mamhao.com All Rights Reserved.
 *
 */
package com.bc.spring.thymeleaf.study.common.entity.account;

/**
 * @author banchun
 *
 */
public enum RoleType {
	UNUSE(0),					//未分配
	HR_ADMIN(1),                //HR系统管理员
	HR_SUB_ACCOUNT(2)           //HR系统子帐号
	;
	private int code;
	private RoleType(int type) {
		this.code = type;
	}
	public int getCode() {
		return code;
	}
}
