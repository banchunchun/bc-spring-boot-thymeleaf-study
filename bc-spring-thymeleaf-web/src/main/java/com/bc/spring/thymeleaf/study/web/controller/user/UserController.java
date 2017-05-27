package com.bc.spring.thymeleaf.study.web.controller.user;

import com.alibaba.fastjson.JSON;
import com.bc.spring.thymeleaf.study.common.entity.User;
import com.bc.spring.thymeleaf.study.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-11
 * Time:  下午 4:36.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index/{id}")
    public String index(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "id") int id) throws IOException {
        User user = userService.findById(id);

        return JSON.toJSONString(user);
    }
}
