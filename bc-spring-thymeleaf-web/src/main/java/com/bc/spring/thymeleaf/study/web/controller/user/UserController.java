package com.bc.spring.thymeleaf.study.web.controller.user;

import com.alibaba.fastjson.JSON;
import com.bc.logger.ILog;
import com.bc.logger.LogBusinessModule;
import com.bc.logger.LogFactory;
import com.bc.spring.thymeleaf.study.common.entity.User;
import com.bc.spring.thymeleaf.study.service.user.UserService;
import com.bc.spring.thymeleaf.study.web.microservice.bc_ebiz_user.UserServiceQueryApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    private ILog logger = LogFactory.getLog(UserController.class, LogBusinessModule.TRACE_LOG);

//    @Autowired
//    private UserService userService;

    @Autowired
    private UserServiceQueryApi userServiceQueryApi;

    @Autowired
    private OkHttpClient client;

    @RequestMapping(value = "/index/{id}")
    public String index(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "id") int id) throws IOException {
        String json = userServiceQueryApi.findById(id);

        logger.info("index.{},now time:{}",id,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return json;
    }

    @RequestMapping("/zipkin")
    public String zipkin() throws InterruptedException, IOException {
        int sleep= new Random().nextInt(100);
        TimeUnit.MILLISECONDS.sleep(sleep);
        Request request = new Request.Builder().url("http://localhost:8881/V1/user/query?id=4").get().build();
        Response response = client.newCall(request).execute();
        return " [service1 sleep " + sleep+" ms]" + response.body().toString();
    }

}
