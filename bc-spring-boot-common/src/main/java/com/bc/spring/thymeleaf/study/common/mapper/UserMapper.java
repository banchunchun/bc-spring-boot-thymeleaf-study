package com.bc.spring.thymeleaf.study.common.mapper;

import com.bc.spring.thymeleaf.study.common.entity.User;
import org.spring.data.framework.mybatis.bean.IMapper;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-10
 * Time:  下午 2:59.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public interface UserMapper extends IMapper<User> {


    User find(int id);

}
