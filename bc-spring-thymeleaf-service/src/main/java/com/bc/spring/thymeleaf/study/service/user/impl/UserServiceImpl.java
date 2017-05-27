package com.bc.spring.thymeleaf.study.service.user.impl;

import com.bc.spring.thymeleaf.study.common.entity.User;
import com.bc.spring.thymeleaf.study.common.mapper.UserMapper;
import com.bc.spring.thymeleaf.study.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-10
 * Time:  下午 3:21.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findById(int id) {
        return userMapper.find(id);
    }
}
