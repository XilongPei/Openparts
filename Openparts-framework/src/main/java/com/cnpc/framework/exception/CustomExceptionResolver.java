package com.cnpc.framework.exception;

import com.cnpc.framework.constant.ErrorConstant;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 *
 * @author billjiang 475572229@qq.com
 * @create 18-2-24
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse, Object o, Exception ex) {
        ModelAndView model = new ModelAndView();
        if (ex instanceof QueryException) {
            model.addObject("errorName", "查询异常");
            model.addObject("message", ex.getMessage().toString());
            model.addObject("detail", ErrorConstant.ERROR_DETAIL);
        } else if (ex instanceof ClassCastException) {
            model.addObject("errorName", "404");
            model.addObject("message", "访问地址不存在，请确认URL地址是否正确");
            model.addObject("detail", ex.getMessage());
        } else {
            model.addObject("errorName", ex.getClass().getSimpleName());
            model.addObject("message", ex.getMessage().toString());
            model.addObject("detail", ErrorConstant.ERROR_DETAIL);
        }
        model.setViewName("base/error/error");
        return model;
    }
}
