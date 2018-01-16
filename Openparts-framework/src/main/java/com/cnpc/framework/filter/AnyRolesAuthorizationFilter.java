package com.cnpc.framework.filter;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/*
    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="filters">
            <map>
                <entry key="anyRoles" value-ref="anyRolesAuthorizationFilter" />
            </map>
        </property>
        <property name="filterChainDefinitions">
            <value>
                /admin = anyRoles[admin1,admin2]
                /** = anon
            </value>
        </property>
    </bean>
*/

/**
 * Filter that allows access if the current user has the roles specified by the mapped value, or denies access
 * if the user does not have one of the roles specified.
 *
 */
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {

    @SuppressWarnings({"unchecked"})
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;

        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for (String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}
