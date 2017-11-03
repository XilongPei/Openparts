package com.cnpc.framework.filter;

import com.cnpc.framework.utils.SpringContextUtil;
import com.cnpc.framework.utils.StrUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

/**
 * filter过滤器，获取项目路径，设置ajax超时标识
 *
 * @author billJiang QQ:475572229
 */
public class SystemFilter implements Filter {


    private static List<String> anonlist;

    private static PatternMatcher pathMatcher=new AntPathMatcher();


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println(request.getRequestURL());
        String basePath = request.getContextPath();
        request.setAttribute("basePath", basePath);
        request.setAttribute("isauth",SecurityUtils.getSubject().isAuthenticated());
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            //判断session里是否有用户信息
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                //如果是ajax请求响应头会有，x-requested-with
                //如果是anno的请求，则直接通过
                if (!matchAnonPath(request.getRequestURI(),basePath)) {
                    response.setHeader("session-status", "timeout");//在响应头设置session状态
                    return;
                }
            }
        }
        filterChain.doFilter(request, servletResponse);

    }

    @Override
    public void destroy() {

        // TODO Auto-generated method stub

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

        // TODO Auto-generated method stub

    }


    public List<String> getAnonlist() {
        if (anonlist != null) {
            return anonlist;
        } else {
            Ini.Section section = (Ini.Section) SpringContextUtil.getBean("filterChainDefinitionClass");
            anonlist = new ArrayList<>();
            for (String key : section.keySet()) {
                if (section.get(key).equals("anon")) {
                    anonlist.add(key);
                }
            }
            return anonlist;
        }
    }

    public boolean matchAnonPath(String path,String basePath) {
        path=path.replace(basePath,"");
        List<String> alist = this.getAnonlist();
        boolean match = false;
        for (String s : alist) {
            if (pathMatches(s,path)) {
                match = true;
                break;
            }
        }
        return match;
    }

    protected boolean pathMatches(String pattern, String path) {
        return pathMatcher.matches(pattern, path);
    }
}
