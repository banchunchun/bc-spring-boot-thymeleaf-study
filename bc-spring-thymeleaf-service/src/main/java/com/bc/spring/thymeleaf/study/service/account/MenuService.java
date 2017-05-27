package com.bc.spring.thymeleaf.study.service.account;


import com.bc.spring.thymeleaf.study.common.entity.account.Account;
import com.bc.spring.thymeleaf.study.common.entity.account.Menu;
import com.bc.spring.thymeleaf.study.common.entity.account.SystemType;
import com.bc.spring.thymeleaf.study.common.page.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by banchun on 2016/6/27 0027.
 */
public interface MenuService {

    public List<Menu> getTwoLevelMenuForAllocation(SystemType systemType);

    public List<Menu> getMenuList4Account(Account account);

    /**
     * 查询菜单列表
     * @param level 1 一级菜单 2 子菜单
     * @param pMenuId 父菜单id
     * @param pageUtil
     * @return
     */
    public List<Map<String,Object>> queryMenuList4Page(Integer level, Integer pMenuId, PageUtil pageUtil);

    /**
     * 删除菜单
     * @param menuId
     */
    public void deleteMenuById(Integer menuId);



}
