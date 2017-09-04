package com.cnpc.framework.interceptor;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.cnpc.framework.annotation.RefreshCSRFToken;
import com.cnpc.framework.annotation.VerifyCSRFToken;
import com.cnpc.framework.base.pojo.ResultCode;
import com.cnpc.framework.constant.CodeConstant;
import com.cnpc.framework.utils.CSRFTokenUtil;
import com.cnpc.framework.utils.StrUtil;

/**
 * CSRFInterceptor 防止跨站请求伪造拦截器
 *
 * @author cnpc 2016年10月6日 下午8:14:40
 */
public class CSRFInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("---------->" + request.getRequestURI());
        //System.out.println(request.getHeader("X-Requested-With"));
        // 提交表单token 校验
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        VerifyCSRFToken verifyCSRFToken = method.getAnnotation(VerifyCSRFToken.class);
        // 如果配置了校验csrf token校验，则校验
        if (verifyCSRFToken != null) {
            // 是否为Ajax标志
            String xrq = request.getHeader("X-Requested-With");
            // 非法的跨站请求校验
            if (verifyCSRFToken.verify() && !verifyCSRFToken(request)) {
                if (StrUtil.isEmpty(xrq)) {
                    // form表单提交，url get方式，刷新csrftoken并跳转提示页面
                    String csrftoken = CSRFTokenUtil.generate(request);
                    request.getSession().setAttribute("CSRFToken", csrftoken);
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print("<script>model.error('非法请求')</script>");
                    response.flushBuffer();
                    return false;
                } else {
                    // 刷新CSRFToken，返回错误码，用于ajax处理，可自定义 前端SUCCESS处理
                    String csrftoken = CSRFTokenUtil.generate(request);
                    request.getSession().setAttribute("CSRFToken", csrftoken);
                    ResultCode rc = CodeConstant.CSRF_ERROR;
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print(JSONObject.toJSONString(rc));
                    response.flushBuffer();
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        // 第一次生成token
        if (modelAndView != null) {
            if (request.getSession(false) == null || StrUtil.isEmpty((String) request.getSession(false).getAttribute("CSRFToken"))) {
                request.getSession().setAttribute("CSRFToken", CSRFTokenUtil.generate(request));
                return;
            }
        }
        // 刷新token
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RefreshCSRFToken refreshAnnotation = method.getAnnotation(RefreshCSRFToken.class);

        // 跳转到一个新页面 刷新token
        String xrq = request.getHeader("X-Requested-With");
        if (refreshAnnotation != null && refreshAnnotation.refresh() && StrUtil.isEmpty(xrq)) {
            request.getSession().setAttribute("CSRFToken", CSRFTokenUtil.generate(request));
            return;
        }

        // 校验成功 刷新token 可以防止重复提交  xrq不为空则为ajax方法
        VerifyCSRFToken verifyAnnotation = method.getAnnotation(VerifyCSRFToken.class);
        if (verifyAnnotation != null) {
            if (verifyAnnotation.verify()) {
                if (StrUtil.isEmpty(xrq)) {
                    request.getSession().setAttribute("CSRFToken", CSRFTokenUtil.generate(request));
                } else {
                    //前端ERROR 处理
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("CSRFToken", CSRFTokenUtil.generate(request));
                    response.setContentType("application/json;charset=UTF-8");
                    OutputStream out = response.getOutputStream();
                    out.write((",'csrf':" + JSONObject.toJSONString(map) + "}").getBytes("UTF-8"));
                }
            }
        }
    }

    /**
     * 处理跨站请求伪造 针对需要登录后才能处理的请求,验证CSRFToken校验
     * 
     * @param request
     */
    protected boolean verifyCSRFToken(HttpServletRequest request) {

        // 请求中的CSRFToken
        String requstCSRFToken = request.getHeader("CSRFToken");
        if (StrUtil.isEmpty(requstCSRFToken)) {
            //return false;
            //if there is no csrftoken from web ,then no csrf check
            return true;
        }
        String sessionCSRFToken = (String) request.getSession().getAttribute("CSRFToken");
        if (StrUtil.isEmpty(sessionCSRFToken)) {
            return false;
        }
        return requstCSRFToken.equals(sessionCSRFToken);
    }
}
