# bc-spring-boot-thymeleaf-study
Spring Boot 、Spring security 4 、Thymeleaf 集成


本项目主要用于怎么样快速搭建一个Spring Boot、Spring Security 4 、Thymeleaf 的web环境，代码仅供参考！

## 使用spring boot Parent 
---

#### 引入spring boot pom 
 ``` java
 <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.2.RELEASE</version>
 </parent>
 ```
#### 引入spring cloud 
``` java
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Camden.SR6</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

#### 引入spring boot 相关依赖

``` java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<!-- 由于使用thymeleaf 则不需要引用spring-boot-starter-web,因为里面已经thymeleaf已经引入 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.1.1</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
#### 配置thymeleaf 模板
``` java

  thymeleaf:
    mode: HTML5
    cache: false  #开发时把缓存关闭，不然无法实时看见效果
    encoding: UTF-8
    content-type: text/html
```

#### 配置webmvc configuration
``` java
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
  //本示例仅仅提供了几个view
  public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
   }
     @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/static/**/").addResourceLocations("/resources/static/");
    }
}
```
#### 配置spring security 4 java config
``` java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
    //自定义校验用户
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider; 
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
          .authorizeRequests()
          .antMatchers("/home").permitAll()//访问：/home 无需登录认证权限
          .anyRequest().authenticated() //其他所有资源都需要认证，登陆后访问
          .antMatchers("/hello").hasAuthority("ROLE_ADMIN") //登陆后之后拥有“ADMIN”权限才可以访问/hello方法，否则系统会出现“403”权限不足的提示
          .and()
          .formLogin()
          .loginPage("/login")//指定登录页是”/login”
          .permitAll()
          .successHandler(loginSuccessHandler()) //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
          .and()
          .logout()
          .logoutSuccessUrl("/home") //退出登录后的默认网址是”/home”
          .permitAll()
          .invalidateHttpSession(true)
          .and()
          .rememberMe()//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
          .tokenValiditySeconds(1209600);
    }
    
     @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      //使用自定义权限校验器
      auth.authenticationProvider(myAuthenticationProvider);
    }
    //登录成功跳转
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }
}

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AccountService accountService; //你自己的获取用户service
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Account account = accountService.queryName(username) ;
        if (null == account)
            throw new BadCredentialsException("Username not found.");
        //加密过程在这里体现
        //加密盐值
        password = new Md5PasswordEncoder().encodePassword(password,username);
        if (!password.equals(account.getUserPwd())) {
            throw new BadCredentialsException("Wrong password.");
        }
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


public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        MyUser myUser = (MyUser)authentication.getPrincipal();
        System.out.println("管理员 " + myUser.getUsername() + " 登录");

        System.out.println("IP :"+getIpAddress(request));

        super.onAuthenticationSuccess(request, response, authentication);
    }

    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
```

####简单的login.html
``` html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Spring Security Example </title>
</head>
<body>
<div th:if="${param.error}">
    Invalid username and password.
</div>
<div th:if="${param.logout}">
    You have been logged out.
</div>
<form th:action="@{/login}" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>
</body>
</html>
```
