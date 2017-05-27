package com.bc.spring.thymeleaf.study.web;

import com.bc.spring.thymeleaf.study.web.configuration.event.ApplicationListenerEnvironmentPrepared;
import com.bc.spring.thymeleaf.study.web.configuration.event.ApplicationListenerFailed;
import com.bc.spring.thymeleaf.study.web.configuration.event.ApplicationListenerPrepared;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-10
 * Time:  下午 3:41.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@EnableDiscoveryClient
@EnableConfigurationProperties
@EnableCaching
//@EnableWebMvc //加了这个影响静态文件访问
@EnableScheduling
@EnableAsync
@EnableTransactionManagement
@EnableSpringDataWebSupport
@SpringBootApplication
@ComponentScan(value = {"com.bc.spring.thymeleaf.study","org.spring.data.framework"})
//@EnableMongoRepositories(basePackages = "com.mamahao.ebiz.user.persist.repository.mongo",repositoryFactoryBeanClass = BaseMongoRepositoryFactoryBean.class)
@EnableHystrix
@EnableHystrixDashboard
public class UserApplicationRunner implements CommandLineRunner{

    private static Logger logger = Logger.getLogger(UserApplicationRunner.class.getName());

    public static void main(String[] args) {
        StopWatch watch = new StopWatch("UserServiceApp");

        watch.start("SpringApplication");
        SpringApplication application = new SpringApplication(UserApplicationRunner.class);
        watch.stop();

        watch.start("AddListeners");
        application.addListeners(new ApplicationListenerPrepared());
        application.addListeners(new ApplicationListenerEnvironmentPrepared());
        application.addListeners(new ApplicationListenerFailed());
        watch.stop();

        watch.start("Settings");
        application.setRegisterShutdownHook(true);
        application.setWebEnvironment(true);
        watch.stop();

        watch.start("Running");
        application.run(args);
        watch.stop();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                logger.info("******************************bc-ebiz-user shutdown******************************");
            }
        });

        logger.info(watch.prettyPrint());
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.warning("******************************bc-ebiz-user startup******************************");
    }
}
