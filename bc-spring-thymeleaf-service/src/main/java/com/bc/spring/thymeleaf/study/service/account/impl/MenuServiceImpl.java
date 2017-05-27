package com.bc.spring.thymeleaf.study.service.account.impl;

import com.bc.spring.thymeleaf.study.common.entity.account.Account;
import com.bc.spring.thymeleaf.study.common.entity.account.Menu;
import com.bc.spring.thymeleaf.study.common.entity.account.SystemType;
import com.bc.spring.thymeleaf.study.common.mapper.account.MenuMapper;
import com.bc.spring.thymeleaf.study.common.page.PageUtil;
import com.bc.spring.thymeleaf.study.service.account.MenuService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by banchun on 2016/6/27 0027.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getTwoLevelMenuForAllocation(SystemType systemType) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("sysCode", systemType.getCode());
        params.put("level", 1);
        return menuMapper.getTwoLevelMenuForAllocation(params);
    }

    @Override
    public List<Menu> getMenuList4Account(Account account) {
        Map<String,Object> params = Maps.newHashMap();
        params.put("userId",account.getUserId());
        return menuMapper.getMenuForAccount(params);
    }

    /**
     * 查询菜单列表
     *
     * @param level    1 一级菜单 2 子菜单
     * @param pMenuId  父菜单id
     * @param pageUtil
     * @return
     */
    @Override
    public List<Map<String, Object>> queryMenuList4Page(Integer level, Integer pMenuId, PageUtil pageUtil) {
        Map<String,Object> params = Maps.newHashMap();
        params.put("level",level);
        params.put("pMenuId",pMenuId);
        Integer count = menuMapper.queryMenuListCount4Page(params);
        pageUtil.setRecordCount(count);
        params.put("start",(pageUtil.getPage() - 1) * pageUtil.getSize());
        params.put("limit",pageUtil.getSize());
        return menuMapper.queryMenuList4Page(params);
    }

    @Override
    public void deleteMenuById(Integer menuId) {
        //先删除菜单权限
        menuMapper.deleteMenuPowerById(menuId);
        //删除菜单
        menuMapper.deleteMenuById(menuId);
    }

}
