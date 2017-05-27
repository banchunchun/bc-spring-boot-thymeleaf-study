package com.bc.spring.thymeleaf.study.service.user;


import com.bc.spring.thymeleaf.study.common.entity.User;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-10
 * Time:  下午 3:20.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {
    User findById(int id);
}
