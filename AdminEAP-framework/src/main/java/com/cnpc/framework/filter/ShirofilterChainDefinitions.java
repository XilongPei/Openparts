package com.cnpc.framework.filter;

import org.apache.shiro.config.Ini;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by billJiang on 2017/5/5.
 * e-mail:475572229@qq.com  qq:475572229
 */
public class ShirofilterChainDefinitions implements FactoryBean<Ini.Section> {

    private String filterChainDefinitions;


    @Override
    public Ini.Section getObject() throws Exception {
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection("urls");
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection("");
        }
        return section;
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }


    public String getFilterChainDefinitions() {
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }


}
