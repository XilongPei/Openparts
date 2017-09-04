package com.cnpc.framework.filter;

import com.cnpc.framework.base.entity.User;
import com.cnpc.framework.base.service.FunctionService;
import com.cnpc.framework.base.service.RoleService;
import com.cnpc.framework.base.service.UserService;
import com.cnpc.framework.utils.PropertiesUtil;
import com.cnpc.framework.utils.StrUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author 系统安全认证实现类
 */
@Service
public class SystemAuthorizingRealm extends AuthorizingRealm {

    /**
     * 认证回调函数, 登录时调用
     */
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private FunctionService functionService;

    /**
     * 用户认证
     *
     * @param authcToken 含登录名密码的信息
     * @return 认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        if (authcToken == null)
            throw new AuthenticationException("parameter token is null");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 校验用户名密码
        String password=String.copyValueOf(token.getPassword());
        User user= userService.getUserByLoginName(token.getUsername());
        if (user!=null) {
            if(!password.equals(user.getPassword())&& isNeedPassword()){
                throw new IncorrectCredentialsException();
            }
            //这样前端页面可取到数据
            SecurityUtils.getSubject().getSession().setAttribute("user",user);
            // 注意此处的返回值没有使用加盐方式,如需要加盐，可以在密码参数上加
            return new SimpleAuthenticationInfo(user.getId(), token.getPassword(), token.getUsername());
        }
        throw new UnknownAccountException();
    }


    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用 shiro 权限控制有三种
     * 1、通过xml配置资源的权限
     * 2、通过shiro标签控制权限
     * 3、通过shiro注解控制权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principals);
            SecurityUtils.getSubject().logout();
            return null;
        }

        if (principals == null) {
            throw new AuthorizationException("parameters principals is null");
        }
        //获取已认证的用户名（登录名）
        String userId=(String)super.getAvailablePrincipal(principals);
        if(StrUtil.isEmpty(userId)){
            return null;
        }
        Set<String> roleCodes=roleService.getRoleCodeSet(userId);
        //默认用户拥有所有权限
        Set<String> functionCodes=functionService.getAllFunctionCode();
       /* Set<String> functionCodes=functionService.getFunctionCodeSet(roleCodes);*/
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleCodes);
        authorizationInfo.setStringPermissions(functionCodes);
        return authorizationInfo;
    }

    //是否需要校验密码登录，用于开发环境 0默认为开发环境，其他为正式环境（1，或者不配）
    public boolean isNeedPassword(){
         String version=PropertiesUtil.getValue("system.version");
        if("0".equals(version))
            return false;
        else
            return true;
    }
}
