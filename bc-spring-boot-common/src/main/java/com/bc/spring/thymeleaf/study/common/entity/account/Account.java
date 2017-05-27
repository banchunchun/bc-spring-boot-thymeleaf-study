/**
 * 
 */
package com.bc.spring.thymeleaf.study.common.entity.account;


import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * HR系统帐号
 */
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 631185719136969902L;
	private Long userId;
	private String userName;
	private String realName;										//真实姓名
	private String userPwd;
	private String job;											//职位信息
	private RoleType roleType;										//角色类型
	private boolean available; 										//账号状态  1：正常 0：锁定不能登录（冻结中）
	private Date createTime;										//创建时间
	private Date modifyTime;										//修改时间
	private Date lastLoginTime;                                     //最后登录时间
	private List<Menu> myTopMenu;									//获取我自己的顶级菜单列表
	

	public Account(){
		super();
	}

	
	public Account(Long userId, Date lastLoginTime){
		this.userId=userId;
		this.lastLoginTime=lastLoginTime;
	}
	public Account(String userName, String userPwd){
		this.userName=userName;
		this.userPwd=userPwd;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userPwd
	 */
	public String getUserPwd() {
		return userPwd;
	}
	/**
	 * @param userPwd the userPwd to set
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}


	public Account(long userId) {
		super();
		this.userId = userId;
	}
	
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	public List<Menu> getMyTopMenu() {
		return myTopMenu;
	}
	public void setMyTopMenu(List<Menu> myTopMenu) {
		this.myTopMenu = myTopMenu;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	
}
