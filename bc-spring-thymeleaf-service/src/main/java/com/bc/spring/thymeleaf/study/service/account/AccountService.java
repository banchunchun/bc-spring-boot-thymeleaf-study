package com.bc.spring.thymeleaf.study.service.account;



import com.bc.spring.thymeleaf.study.common.entity.account.Account;
import com.bc.spring.thymeleaf.study.common.entity.account.Menu;
import com.bc.spring.thymeleaf.study.common.entity.account.Power;
import com.bc.spring.thymeleaf.study.common.entity.account.SystemType;
import com.bc.spring.thymeleaf.study.common.page.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by banchun on 2016/6/24 0024.
 */
public interface AccountService {

    public Account queryAccount(String username, SystemType systemType);

    public Account queryAccountByName(String username);

    public Account queryAccount(Account account);

    /**
     * 根据赋予的菜单列表，插入新增用户对应的权限
     *
     * @param account
     * @param menuIds
     */
    void addAccount(Account account, int[] menuIds);

    /**
     * 获取指定用户的权限
     * @param username
     * @return
     */
    Map<Integer,Power> findSecurityUserPowerByName(String username);

    List<Menu> queryMenuByPower(Integer powerId);

    /**********************************华丽分割线***************************************/
    /**
     * 查询帐号列表
     * @param pageUtil
     * @return
     */
    public List<Account> queryAccountList4Page(PageUtil pageUtil);

    /**
     * 根据账户修改该账户的权限菜单
     * @param account
     * @param menuIds
     */
    public void updateAccount(Account account, int[] menuIds);

    public void addNewMenu4Manager(String userName, Menu menu) throws Exception;

    /**
     * 给指定的用户添加菜单权限
     * @param userName
     * @param menuId
     */
    public void addMenuPower(String userName, Integer menuId) throws Exception;

    /**
     * 移除指定用户的菜单权限
     * @param userName
     * @param menuId
     */
    public void removeMenuPower(String userName, Integer menuId) throws Exception;
}
