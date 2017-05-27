/**
 * 
 */
package com.bc.spring.thymeleaf.study.common.entity.account;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 扩展Spring Security User用户对象信息，增加userId属性
 * @author bowen
 * @date 2015年4月27日 下午2:05:21 
 */
public class MyUser extends User {
	
	private static final long serialVersionUID = 1L;

	private long userId;	//account user_id
	 
	private String job;						//用户职位
	private RoleType roleType;				//用户角色类型
	
	private int menuPower;					//菜单权限编号
	private int operatePower;				//操作权限编号
	
	private List<Menu> menus;				//用户菜单
	private List<Operate> operates;			//用户的所有可用权限
	

	
	private String realName; //真是姓名


	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/***
	 * 登录错误返回
	 * @param account： 用户信息
	 * @param enabled：true（用户启用），false:（用户未启用，不可用）
	 * @param accountNonExpired：true(账号没有过期) ,false(账号过期)
	 * @param credentialsNonExpired：true(证书没有过期),false(证书过期)
	 * @param accountNonLocked：true(账号未锁定)，false(账号被锁定)
	 */
	public MyUser(Account account, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked){
		super(account.getUserName(),account.getUserPwd(),enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,new ArrayList<GrantedAuthority>());
	}
	/**
	 * @param account
	 * @param auths
	 */
	public MyUser(Account account, Collection<GrantedAuthority> auths) {
		super(account.getUserName(), account.getUserPwd(), account.isAvailable(), true, true, true, auths);


		this.job = account.getJob();
		this.userId = account.getUserId();
		this.roleType = account.getRoleType();
		this.realName = account.getRealName();
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Operate> getOperates() {
		return operates;
	}

	public void setOperates(List<Operate> operates) {
		this.operates = operates;
	}

	public int getMenuPower() {
		return menuPower;
	}

	public void setMenuPower(int menuPower) {
		this.menuPower = menuPower;
	}

	public int getOperatePower() {
		return operatePower;
	}

	public void setOperatePower(int operatePower) {
		this.operatePower = operatePower;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	
}
