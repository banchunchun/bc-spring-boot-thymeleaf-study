package com.bc.spring.thymeleaf.study.web.filter;

import com.bc.logger.ILog;
import com.bc.logger.LogBusinessModule;
import com.bc.logger.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-06-12
 * Time:  下午 8:44.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class RequestIdUtil {
    public static final String REQUEST_ID_KEY = "requestId";
    public static ThreadLocal<String> requestIdThreadLocal = new ThreadLocal<String>();

    private static final ILog logger = LogFactory.getLog(RequestIdUtil.class, LogBusinessModule.TRACE_LOG);

    /**
     * 获取requestId
     * @Title getRequestId
     * @Description TODO
     * @return
     *
     * @author sunhaojie 3113751575@qq.com
     * @date 2016年8月31日 上午7:58:28
     */
    public static String getRequestId(HttpServletRequest request) {
        String requestId = null;
        String parameterRequestId = request.getParameter(REQUEST_ID_KEY);
        String headerRequestId = request.getHeader(REQUEST_ID_KEY);

        if (parameterRequestId == null || headerRequestId == null) {
            logger.info("request parameter 和header 都没有requestId入参");
            requestId = UUID.randomUUID().toString();
        } else {
            requestId = parameterRequestId != null ? parameterRequestId : headerRequestId;
        }

        requestIdThreadLocal.set(requestId);

        return requestId;
    }
}
