package com.bc.spring.thymeleaf.study.web.security.config;

import com.bc.spring.thymeleaf.study.common.entity.account.*;
import com.bc.spring.thymeleaf.study.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * spring boot security provider
 * Created by banchun on 2016/8/29 0029.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountService accountService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Account account = accountService.queryAccount(username, SystemType.HR);
        if (null == account)
            return null;
        Map<Integer, Power> ur = accountService.findSecurityUserPowerByName(username);
        // 这里添加的是用户可以操作的权限编号
        auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        auths.add(new SimpleGrantedAuthority(String.valueOf(ur.get(Power.POWER_OPERATOR).getPowerId())));
        // 获取用户的菜单权限编号
        int menuPowerId = Integer.valueOf(String.valueOf(ur.get(Power.POWER_MENU).getPowerId()));

        List<Menu> menuList = accountService.queryMenuByPower(menuPowerId);
        MyUser myUser = new MyUser(account, auths);
        myUser.setMenus(menuList);
        return new UsernamePasswordAuthenticationToken(myUser,password,auths);
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return true;
    }
}
