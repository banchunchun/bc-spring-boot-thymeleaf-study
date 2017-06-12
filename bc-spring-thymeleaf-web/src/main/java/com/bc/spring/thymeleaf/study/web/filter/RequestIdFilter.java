package com.bc.spring.thymeleaf.study.web.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 8:38.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class RequestIdFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestId = RequestIdUtil.getRequestId((HttpServletRequest) request);
        MDC.put("requestId", requestId);
        chain.doFilter(request, response);
        RequestIdUtil.requestIdThreadLocal.remove();
        MDC.remove("requestId");
    }

    @Override
    public void destroy() {

    }
}
