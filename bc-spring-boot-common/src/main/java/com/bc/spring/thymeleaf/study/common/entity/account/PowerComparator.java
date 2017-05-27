/**
 * Project Name:gd-common
 * File Name:PowerComparator.java
 * Package Name:com.jinzai.model.account
 * Date:2015年5月6日下午4:23:13
 * Copyright (c) 2015, yaoshu@mamhao.com All Rights Reserved.
 *
 */
package com.bc.spring.thymeleaf.study.common.entity.account;

import java.util.Comparator;

/**
 * @author banchun
 *
 */
public class PowerComparator implements Comparator<Power> {
	
	public int compare(Power p1, Power p2) {
		return p1.getPowerId() - p2.getPowerId();
	}

}
