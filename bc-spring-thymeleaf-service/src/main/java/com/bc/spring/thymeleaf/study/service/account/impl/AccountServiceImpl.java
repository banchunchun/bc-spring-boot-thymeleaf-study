package com.bc.spring.thymeleaf.study.service.account.impl;

import com.bc.spring.thymeleaf.study.common.entity.account.*;
import com.bc.spring.thymeleaf.study.common.mapper.account.AccountMapper;
import com.bc.spring.thymeleaf.study.common.mapper.account.MenuMapper;
import com.bc.spring.thymeleaf.study.common.page.PageUtil;
import com.bc.spring.thymeleaf.study.service.account.AccountService;
import com.bc.spring.thymeleaf.study.service.account.MenuService;
import com.bc.spring.thymeleaf.study.service.utils.Md5Util;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by banchun on 2016/6/24 0024.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuService menuService;
    /**
     * 根据赋予的菜单列表，插入新增用户对应的权限
     *
     * @param account
     * @param menuIds
     */
    @Override
    public void addAccount(Account account, int[] menuIds) {

        account.setUserPwd(Md5Util.getPwd(account.getUserName(), account.getUserPwd()));
        accountMapper.addAccount(account);
        if (menuIds == null && account.getRoleType() != RoleType.HR_ADMIN && account.getRoleType() != RoleType.HR_SUB_ACCOUNT) {
            return;
        }

        if (account.getRoleType() == RoleType.HR_SUB_ACCOUNT) {
            insert(account,parseInt(menuIds));
        }
        if (account.getRoleType() == RoleType.HR_ADMIN) {
            //管理员
            List<Menu> menus = menuService.getTwoLevelMenuForAllocation(SystemType.HR);
            List<Integer> list = new ArrayList<Integer>();
            for (Menu menu : menus) {
                list.add(menu.getMenuId());
                if (menu.getChildMenus() != null && menu.getChildMenus().size() > 0) {
                    for (Menu subMenu : menu.getChildMenus()) {
                        list.add(subMenu.getMenuId());
                    }
                }
            }
            insert(account,list.toArray(new Integer[] {}));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateAccount(Account account, int[] menuIds) {
        //更新账户信息
        accountMapper.updateAccount(account);

        List<Power> powerList = menuMapper.selectSecurityUserPowerByName(account.getUserName());

        List<Integer> oldMenuIdList = new ArrayList<Integer>();

        //获取Account原有的Menu
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId",account.getUserId());
        List<Menu> oldMenus = menuMapper.getMenuForAccount(params);
        if (oldMenus != null) {
            for (Menu oldMenu : oldMenus) {
                if(oldMenu != null){
                    oldMenuIdList.add(oldMenu.getMenuId());
                }
            }
        }
        List<Integer> editMenuIdList = Arrays.asList(parseInt(menuIds));
        // 将要新增的MenuIds
        Collection<Integer> willAddMenuList = CollectionUtils.subtract(editMenuIdList, oldMenuIdList);
        // 获取要移除的MenuIds
        Collection<Integer> willRemoveMenuList = CollectionUtils.subtract(oldMenuIdList, editMenuIdList);

        //查询这个账号是否为管理员
        Account accountIsAdmin = accountMapper.queryAccountByName(account.getUserName());

        // 如果是管理员，则依然有账号中心菜单
        if(accountIsAdmin.getRoleType() == RoleType.HR_ADMIN){
            if(willRemoveMenuList.contains(5)){
                willRemoveMenuList.remove(5);
            }
        }

        addAccountPower(powerList, willAddMenuList.toArray(new Integer[] {}));
        removeAccountPower(powerList, willRemoveMenuList.toArray(new Integer[] {}));



    }

    /**
     * 新增用户权限
     * @param powerList
     * @param willAddMenuIds
     */
    private void addAccountPower(List<Power> powerList, Integer[] willAddMenuIds) {
        if (willAddMenuIds == null || willAddMenuIds.length == 0)
            return;
        Power powerO = new Power();
        Power powerM = new Power();
        for(Power power: powerList){
            if(power.getPowerType() == 0){
                powerO = power;
            }else{
                powerM = power;
            }
        }
        // 添加用户菜单权限
        Map<String, Object> param = new HashMap<>();
        param.put("powerId", powerM.getPowerId());
        param.put("menuIds", willAddMenuIds);
        menuMapper.addMenuPower(param);
        // 获取指定willAddMenuIds 对应的所有操作编号列表
        Map<String, Integer[]> map = new HashMap<>();
        map.put("menuIds", willAddMenuIds);
        List<Integer> operateIds = accountMapper.queryOperatesByMenuIds(map);

//        if (operateIds == null || operateIds.size() == 0)
//            return;
//        //添加用户操作权限
//        Map<String, Object> param1 = new HashMap<>();
//        param.put("powerId", powerO.getPowerId());
//        param.put("operateIds", willAddMenuIds);
//        menuMapper.addOperatePower(param1);

    }

    /**
     * 移除用户权限
     * @param powerList
     * @param willRemoveMenuIds
     */
    private void removeAccountPower(List<Power> powerList, Integer[] willRemoveMenuIds) {
        if (willRemoveMenuIds == null || willRemoveMenuIds.length == 0)
            return;
        // 移除用户菜单权限
        Power powerO = new Power();
        Power powerM = new Power();
        for(Power power: powerList){
            if(power.getPowerType() == 0){
                powerO = power;
            }else{
                powerM = power;
            }
        }
        Map<String, Object> param = new HashMap<>();
        param.put("powerId", powerM.getPowerId());
        param.put("menuIds", willRemoveMenuIds);
        menuMapper.removePowerMenu(param);

        // 获取指定willRemoveMenuIds 对应的所有操作编号列表
//        Map<String, Integer[]> map = new HashMap<>();
//        map.put("menuIds", willRemoveMenuIds);
//        List<Integer> operateIds = accountMapper.queryOperatesByMenuIds(map);

        // 移除用户操作权限
//        Map<String, Object> param1 = new HashMap<>();
//        param.put("powerId", powerO.getPowerId());
//        param.put("operateIds", willRemoveMenuIds);
//        menuMapper.removePowerOperate(param1);

 }

    private Integer[] parseInt(int [] arr){
        Integer[] integer = new Integer[arr.length];
        for (int i = 0 ;i < arr.length ;i ++) {
            integer[i] = new Integer(arr[i]);
        }
        return integer;
    }

    private void insert(Account account,Integer[] menuIds){
        Power power = new Power(Power.POWER_OPERATOR); // 权限
        menuMapper.insertPower(power);
        Power power1 = new Power(Power.POWER_MENU); // 菜单
        menuMapper.insertPower(power1);

        Map<String,Object> tempParams = Maps.newHashMap();
        tempParams.put("powerId",power1.getPowerId());
        tempParams.put("menuIds",menuIds);
        menuMapper.insertPowerMenu(tempParams);

        Map<String, Object> params = Maps.newHashMap();
        params.put("accountId", account.getUserId());
        params.put("powerId", power.getPowerId());
        menuMapper.insertAccountPower(params);
        params.put("powerId", power1.getPowerId());
        menuMapper.insertAccountPower(params);
    }

    @Override
    public Account queryAccount(String username, SystemType systemType) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("userName", username);
        params.put("systemType", systemType.getCode());
        params.put("available",1);
        return accountMapper.queryAccount(params);
    }

    /**
     * 获取指定用户的权限
     *
     * @param username
     * @return
     */
    @Override
    public Map<Integer, Power> findSecurityUserPowerByName(String username) {
        Map<Integer, Power> result = Maps.newHashMap();
        List<Power> list = menuMapper.selectSecurityUserPowerByName(username);
        for (Power power: list) {
            Power temp = new Power();
            temp.setPowerId(power.getPowerId());
            temp.setPowerType(power.getPowerType());
            result.put(power.getPowerType(),temp);
        }
        return result;
    }

    @Override
    public List<Menu> queryMenuByPower(Integer powerId) {
        return menuMapper.queryMenuByPower(powerId);
    }

    @Override
    public Account queryAccountByName(String username) {

        return accountMapper.queryAccountByName(username);
    }

    /**
     * 查询帐号列表
     *
     * @param pageUtil
     * @return
     */
    @Override
    public List<Account> queryAccountList4Page(PageUtil pageUtil) {
        Map<String,Object> params = Maps.newHashMap();
        params.put("start",(pageUtil.getPage() - 1) * pageUtil.getSize());
        params.put("limit",pageUtil.getSize());
        Integer count = accountMapper.queryAccountListCount4Page(params);
        pageUtil.setRecordCount(count);
        return accountMapper.queryAccountList4Page(params);
    }

    @Override
    public Account queryAccount(Account account) {
        Map<String,Object> params = Maps.newHashMap();
        params.put("userId",account.getUserId());
        return accountMapper.queryAccount(params);
    }

    @Override
    public void addNewMenu4Manager(String userName, Menu menu) throws Exception {
        Account account = this.queryAccountByName(userName);
        if(null == account){
            throw new Exception("用户{"+userName+"}不存在，请检查");
        }
        //插入菜单
        menuMapper.insertAccountMenu(menu);

        Integer powerId = accountMapper.queryPowerIdByUserName(userName);

        Map<String,Object> tempParams = Maps.newHashMap();
        tempParams.put("powerId",powerId);
        tempParams.put("menuIds",new Integer[]{menu.getMenuId()});
        menuMapper.insertPowerMenu(tempParams);
    }

    /**
     * 给指定的用户添加菜单权限
     *
     * @param userName
     * @param menuId
     */
    @Override
    public void addMenuPower(String userName, Integer menuId) throws Exception {
        Account account = this.queryAccountByName(userName);
        if(null == account){
            throw new Exception("用户{"+userName+"}不存在，请检查");
        }
        Integer powerId = accountMapper.queryPowerIdByUserName(userName);

        Map<String,Object> tempParams = Maps.newHashMap();
        tempParams.put("powerId",powerId);
        tempParams.put("menuIds",new Integer[]{menuId});
        menuMapper.insertPowerMenu(tempParams);

    }

    /**
     * 移除指定用户的菜单权限
     *
     * @param userName
     * @param menuId
     */
    @Override
    public void removeMenuPower(String userName, Integer menuId) throws Exception {
        Account account = this.queryAccountByName(userName);
        if(null == account){
            throw new Exception("用户{"+userName+"}不存在，请检查");
        }
        Integer powerId = accountMapper.queryPowerIdByUserName(userName);
        Map<String, Object> param = new HashMap<>();
        param.put("powerId", powerId);
        param.put("menuIds", new Integer[]{menuId});
        menuMapper.removePowerMenu(param);
    }
}
