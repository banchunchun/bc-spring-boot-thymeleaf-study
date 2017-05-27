/**
 * Project Name:gd-common
 * File Name:Power.java
 * Package Name:com.jinzai.model.account
 * Date:2015年5月6日下午1:33:47
 * Copyright (c) 2015, yaoshu@mamhao.com All Rights Reserved.
 *
 */
package com.bc.spring.thymeleaf.study.common.entity.account;

import java.io.Serializable;


/**
 * 权限实体
 * @author banchun
 *
 */
public class Power implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2991838651251492966L;
	public static int POWER_OPERATOR = 0;
	public static int POWER_MENU = 1;
	
	private Integer powerId;
	private Integer powerType;				//0 : 表示是 Operator操作  1： 表示是系统菜单

	public Power(){}
	public Power(Integer powerType){
		this.powerType = powerType;
	}

	public Power(Integer powerId, Integer powerType) {
		this.powerId = powerId;
		this.powerType = powerType;
	}

	public Integer getPowerId() {
		return powerId;
	}
	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}
	public Integer getPowerType() {
		return powerType;
	}
	public void setPowerType(Integer powerType) {
		this.powerType = powerType;
	}

	
	

}
