package com.bc.spring.thymeleaf.study.common.mapper.account;



import com.bc.spring.thymeleaf.study.common.entity.account.Account;
import com.bc.spring.thymeleaf.study.common.entity.account.Menu;
import com.bc.spring.thymeleaf.study.common.entity.account.Power;
import com.bc.spring.thymeleaf.study.common.entity.account.SystemType;

import java.util.List;
import java.util.Map;

/**
 * 菜单mapper
 * Created by banchun on 2016/6/24 0024.
 */
public interface MenuMapper {
    /**
     * 查询指定权限的菜单
     * @return
     */
    List<Menu> queryMenuByPower(int powerId);

    /**
     * 查询指定权限的菜单
     * @return
     */
    public List<Menu> queryCloudMenuByPower(Map<String, Integer> param);

    /**
     * 根据系统类型获取可分配的菜单列表,供前台显示用
     * @param systemType
     * @return
     */
    public List<Menu> getMenuForAllocation(SystemType systemType);

    /**
     * 根据系统类型获取可分配的菜单列表,供前台显示用 (包含二级菜单)
     * @param params
     * @return
     */
    public List<Menu> getTwoLevelMenuForAllocation(Map<String, Object> params);
    /**
     * 获取当前指定用户的已经分配了的菜单列表
     * @param params
     * @return
     */
    public List<Menu> getMenuForAccount(Map<String, Object> params);


    /***
     * 给指定的Power对象添加中间表
     *
     */
    void addMenuPower(Map<String, Object> params);

    /**
     * 删除菜单权限中间表
     */
    void removePowerMenu(Map<String, Object> params);

    /**
     * 给指定的Power对象添加中间表
     *
     */
    void addOperatePower(Map<String, Object> params);

    /**
     * 删除操作权限中间表
     */
    void removePowerOperate(Map<String, Object> params);

    /**
     * 新增Power
     * @param account
     */
    Map<Integer, Power> addPowerByAccount(Account account);

    /**
     * 插入power
     * @param power
     */
    void insertPower(Power power);

    void insertPowerMenu(Map<String, Object> params);

    void insertAccountPower(Map<String, Object> params);

    /**
     * 获取系统指定用户名的相关权限
     * @param name
     * @return
     */
    List<Power> selectSecurityUserPowerByName(String name);


    public List<Map<String,Object>> queryMenuList4Page(Map<String, Object> params);

    public Integer queryMenuListCount4Page(Map<String, Object> params);

    public void insertAccountMenu(Menu menu);

    /**
     * 删除菜单
     * @param menuId
     */
    public void deleteMenuById(Integer menuId);

    public void deleteMenuPowerById(Integer menuId);
}
