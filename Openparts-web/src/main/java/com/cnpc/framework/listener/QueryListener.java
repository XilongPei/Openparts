package com.cnpc.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cnpc.framework.query.pojo.QueryDefinition;

public class QueryListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent context) {

    }

    public void contextInitialized(ServletContextEvent context) {

        QueryDefinition.getInstance().initQuery();
    }

}
