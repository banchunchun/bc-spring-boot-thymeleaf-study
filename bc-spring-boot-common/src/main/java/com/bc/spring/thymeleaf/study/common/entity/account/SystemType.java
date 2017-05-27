/**
 * Project Name:gd-common
 * File Name:SystemType.java
 * Package Name:com.jinzai.model.account
 * Date:2015年5月11日下午2:26:40
 * Copyright (c) 2015, yaoshu@mamhao.com All Rights Reserved.
 *
 */
package com.bc.spring.thymeleaf.study.common.entity.account;

/**
 * 系统类型
 * @author banchun
 *
 */
public enum SystemType {
	
	HR(1),
	PLATFORM(2),
	SHOP(3),
	DEPT(4);
	private int code;
	private SystemType(int type) {
		this.code = type;
	}
	public int getCode() {
		return code;
	}

}
