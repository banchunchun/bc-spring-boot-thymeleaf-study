package com.bc.spring.thymeleaf.study.service;

import com.bc.spring.thymeleaf.study.common.entity.account.*;
import com.bc.spring.thymeleaf.study.service.account.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 自定义用户实现
 * Created by banchun on 2016/6/24 0024.
 */

@Component
public class SecurityUserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory
            .getLogger(SecurityUserService.class);
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        System.out.print(username + "试图获取权限");
        logger.info(username + "试图获取权限");
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }
        Account account = accountService.queryAccount(username, SystemType.HR);
        if (null == account)
            return null;
        Set<GrantedAuthority> authorities = new HashSet<>();
        Map<Integer, Power> ur = accountService.findSecurityUserPowerByName(username);
        // 这里添加的是用户可以操作的权限编号
        auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        auths.add(new SimpleGrantedAuthority(String.valueOf(ur.get(Power.POWER_OPERATOR).getPowerId())));
        // 获取用户的菜单权限编号
        int menuPowerId = Integer.valueOf(String.valueOf(ur.get(Power.POWER_MENU).getPowerId()));

        List<Menu> menuList = accountService.queryMenuByPower(menuPowerId);
        MyUser myUser = new MyUser(account, auths);
        myUser.setMenus(menuList);
        return myUser;
    }
}
