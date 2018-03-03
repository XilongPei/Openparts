package com.cnpc.framework.base.controller;

import com.cnpc.framework.constant.ErrorConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

/**
 * @author billjiang 475572229@qq.com
 * @create 18-2-24
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = "/redis/connect",method = RequestMethod.GET)
    private ModelAndView redisConnectFailure(ModelAndView modelAndView, HttpServletRequest request) {
        String basePath = request.getContextPath();

        request.setAttribute("basePath", basePath);
        modelAndView.addObject("errorName","redisConnectionFailure");
        modelAndView.addObject("message","连接redis错误，请确认redis配置正确，且设置了正确的密码");
        modelAndView.addObject("detail", ErrorConstant.ERROR_DETAIL);
        modelAndView.setViewName("base/error/error");
        return modelAndView;
    }
}
