package com.cnpc.framework.filter;

import com.cnpc.framework.utils.FunctionRightsUtil;
import com.cnpc.framework.utils.SpringContextUtil;
import com.cnpc.framework.utils.AccessToken;
import com.cnpc.framework.base.dao.RedisDao;
import com.cnpc.framework.constant.RedisConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.subject.Subject;
import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
/**
 * filter过滤器，获取项目路径，设置ajax超时标识
 *
 * @author billJiang QQ:475572229
 */
public class SystemFilter implements Filter {

    private RedisDao redisDao = null;

    private static List<String> anonlist;
    private static List<String> access_token_list;
    private static PatternMatcher pathMatcher = new AntPathMatcher();
    static final Logger log = Logger.getLogger(SystemFilter.class);

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.debug(request.getRequestURL());

        String basePath = request.getContextPath();
        Subject subject = SecurityUtils.getSubject();
        boolean isauth = (subject.isAuthenticated() || subject.isRemembered());

        request.setAttribute("basePath", basePath);
        request.setAttribute("isauth", isauth);
        if (!isauth) {

            String path = request.getRequestURI();
            path = path.replace(basePath, "");

            if (matchAccessTokenPath(path)) {
                String access_token = request.getParameter("access_token");
                String key = RedisConstant.ACCESS_TOKEN_PRE + access_token;

                if (redisDao == null) {
                    redisDao = (RedisDao)SpringContextUtil.getBean("redisDao");
                }
                String accessStr = redisDao.get(key);

                if (!AccessToken.checkExpiresIn(accessStr) || !FunctionRightsUtil.getFunctionRights(subject, path)) {
                    // 在响应头设置session状态
                    response.setHeader("session-status", "timeout");
                    return;
                }
            }
            else {
                String str = request.getHeader("x-requested-with");
                if ( str != null && str.equalsIgnoreCase("XMLHttpRequest")) {
                    // 如果是ajax请求响应头会有，x-requested-with
                    // 如果是anno的请求，则直接通过
                    if (!matchAnonPath(path)) {
                        // 在响应头设置session状态
                        response.setHeader("session-status", "timeout");
                        return;
                    }
                }

                // FILTER setting: spring-shiro.xml
                // /**=authc
                //
                // authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
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

    public boolean matchAnonPath(String path) {
        List<String> alist = this.getAnonlist();
        boolean match = false;
        for (String s : alist) {
            if (pathMatches(s, path)) {
                match = true;
                break;
            }
        }
        return match;
    }

    public List<String> getAccessTokenList() {
        if (access_token_list != null) {
            return access_token_list;
        } else {
            Ini.Section section = (Ini.Section) SpringContextUtil.getBean("filterChainDefinitionClass");
            access_token_list = new ArrayList<>();
            for (String key : section.keySet()) {
                if (section.get(key).equals("access_token")) {
                    access_token_list.add(key);
                }
            }
            return access_token_list;
        }
    }

    public boolean matchAccessTokenPath(String path) {
        List<String> alist = this.getAccessTokenList();
        boolean match = false;
        for (String s : alist) {
            if (pathMatches(s, path)) {
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
