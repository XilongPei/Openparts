package com.cnpc.framework.filter;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/*
    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="filters">
            <map>
                <entry key="anyPerms" value-ref="anyPermissionsAuthorizationFilter" />
            </map>
        </property>

            <value>
                /admin/add = anyPerms["admin:delete","admin:add"]
                /** = anon
            </value>
        </property>
    </bean>

    http://shiro.apache.org/permissions.html
*/

/**
 * Filter that allows access if the current user has the permissions specified by the mapped value, or denies access
 * if the user does not have one of the permissions specified.
 *
 */
public class AnyPermissionsAuthorizationFilter extends AuthorizationFilter {

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        for (String perm : perms) {
            if (subject.isPermitted(perm)) {
                return true;
            }
        }

        return false;
    }
}
