/**
 * Project Name:gd-common
 * File Name:Operator.java
 * Package Name:com.jinzai.model.account
 * Date:2015年5月6日下午2:11:02
 * Copyright (c) 2015, yaoshu@mamhao.com All Rights Reserved.
 *
 */
package com.bc.spring.thymeleaf.study.common.entity.account;

import java.io.Serializable;


/**
 * 操作实体
 * @author banchun
 *
 */
public class Operate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3386532321204056635L;
	private Long operatorId;			//操作编号
	private String operateName;			//操作名称
	private boolean available = true;	//是否可用
	private boolean hidden=false;		//是否为隐藏配置
	private Operate depend;				//依赖的其他Power
	private Menu belongMenu;			//所属菜单
	private String url;					//链接相对路径
	private SystemType systemType;		//系统类型
	
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public Operate getDepend() {
		return depend;
	}
	public void setDepend(Operate depend) {
		this.depend = depend;
	}
	public Menu getBelongMenu() {
		return belongMenu;
	}
	public void setBelongMenu(Menu belongMenu) {
		this.belongMenu = belongMenu;
	}
	public SystemType getSystemType() {
		return systemType;
	}
	public void setSystemType(SystemType systemType) {
		this.systemType = systemType;
	}
	
	
}
