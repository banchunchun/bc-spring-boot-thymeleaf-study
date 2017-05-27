/**
 * Project Name:gd-common
 * File Name:Menu.java
 * Package Name:com.jinzai.model.account
 * Date:2015年5月7日上午8:48:02
 * Copyright (c) 2015, yaoshu@mamhao.com All Rights Reserved.
 *
 */
package com.bc.spring.thymeleaf.study.common.entity.account;

import java.io.Serializable;
import java.util.List;

/**
 * 用户菜单实体类
 * 
 * @author 舒
 *
 */
public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5640625155117361793L;
	private int menuId;
	private Menu pMenu;		 				// 父级菜单
	private List<Menu> childMenus;			//子菜单
	private String menuName; 				//名称
	private String menuUrl;					//菜单url
	private String menuPic;					//菜单图片
	private int level;						//菜单层级 1 一级菜单	2 二级菜单
	private boolean status;
	private int sort;
	private SystemType sysType;				// 所属平台 1 Hr系统
	private List<Operate> operates;			// 菜单所包含的操作列表 用于权限分配

	public Menu(){
		
	}
	public Menu(Integer menuId){
		this.menuId = menuId;
	}
	
	public String getMenuPic() {
		return menuPic;
	}

	public void setMenuPic(String menuPic) {
		this.menuPic = menuPic;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public Menu getpMenu() {
		return pMenu;
	}

	public void setpMenu(Menu pMenu) {
		this.pMenu = pMenu;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public List<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public SystemType getSysType() {
		return sysType;
	}

	public void setSysType(SystemType sysType) {
		this.sysType = sysType;
	}

	public List<Operate> getOperates() {
		return operates;
	}

	public void setOperates(List<Operate> operates) {
		this.operates = operates;
	}
	
}
