package com.bc.spring.thymeleaf.study.web.microservice.bc_ebiz_user.hystrix;

import com.bc.spring.thymeleaf.study.web.microservice.bc_ebiz_user.UserServiceQueryApi;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-22
 * Time:  上午 11:08.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserServiceQueryHystrix implements UserServiceQueryApi{
    @Override
    public String findById(@RequestParam Integer id) {
        return "{\"id\":1,\"name\":\"banchun default\"}";
    }
}
