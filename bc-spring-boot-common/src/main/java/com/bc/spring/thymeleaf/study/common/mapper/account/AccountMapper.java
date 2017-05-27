package com.bc.spring.thymeleaf.study.common.mapper.account;


import com.bc.spring.thymeleaf.study.common.entity.account.Account;

import java.util.List;
import java.util.Map;

/**
 * 帐号系统
 * Created by banchun on 2016/6/24 0024.
 */
public interface AccountMapper {


    public void addAccount(Account account);

    public Account queryAccount(Map<String, Object> params);

    public Account queryAccountByName(String username);

    public List<Account> queryAccountList4Page(Map<String, Object> params);

    public Integer queryAccountListCount4Page(Map<String, Object> params);

    public void updateAccount(Account account);

    /**
     * 获取属于指定菜单的所有操作编号列表
     * @param params
     * @return
     */
    List<Integer> queryOperatesByMenuIds(Map<String, Integer[]> params);

    /**
     * 获取指定用户的powerId
     * @param userName
     * @return
     */
    public Integer queryPowerIdByUserName(String userName);
}
