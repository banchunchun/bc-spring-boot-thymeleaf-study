package com.bc.spring.thymeleaf.study.web.microservice.bc_ebiz_user;

import com.bc.spring.thymeleaf.study.web.microservice.ServiceIds;
import com.bc.spring.thymeleaf.study.web.microservice.bc_ebiz_user.hystrix.UserServiceQueryHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-22
 * Time:  上午 10:52.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@FeignClient(name = ServiceIds.EBIZ_USER_SERVICE,fallback = UserServiceQueryHystrix.class)
public interface UserServiceQueryApi {


    @RequestMapping(value = "V1/user/query",method = RequestMethod.GET)
    String findById(@RequestParam("id") Integer id);

}
