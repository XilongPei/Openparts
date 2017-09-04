package com.cnpc.framework.utils;

import static org.apache.commons.digester3.binder.DigesterLoader.newLoader;

import java.io.IOException;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.annotations.FromAnnotationsRuleModule;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xml.sax.InputSource;

public final class ConfigurationUtil {

    private static Logger log = Logger.getLogger(ConfigurationUtil.class);

    private static PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver();

    private static String dominator;

    public static Object parseXMLObject(final Class clazz, Resource resource) throws RuntimeException {

        try {
            InputSource inputSource;
            Digester digester;
            java.io.InputStream is = resource.getInputStream();
            inputSource = new InputSource(is);
            DigesterLoader loader = newLoader(new FromAnnotationsRuleModule() {

                @Override
                protected void configureRules() {

                    bindRulesFrom(clazz);
                }

            });

            digester = loader.newDigester();
            return digester.parse(inputSource);
        } catch (Exception e) {
            log.error(
                    String.format("Fail to transfer Java Object[class: {" + clazz.getSimpleName() + "}] from {" + resource + "}, reason: "),
                    e);
            e.printStackTrace();
            throw new RuntimeException((new StringBuilder()).append("Fail to parse XML Object of ").append(resource).toString(), e);
        }
    }

    public static Resource[] getSortedResources(String location) {

        Resource resources[];
        location = prepareLocation(location);
        resources = null;
        try {
            resources = loader.getResources(location);
            if (resources.length == 0) {
                log.warn("Can't find any [{" + location + "}] :");
                return null;
            }
            int flag = 0;
            boolean handledFile = false;
            if (resources.length > 1) {
                for (int i = 0; i < resources.length || flag < 2; i++) {
                    Resource resource = resources[i];
                    if (isDominator(resource) && !handledFile) {
                        if (i != resources.length - 1) {
                            resources[i] = resources[resources.length - 1];
                            resources[resources.length - 1] = resource;
                            handledFile = true;
                        }
                    } else if (resource.getURI().toString().indexOf("pmsbase") > -1 && i != 0) {
                        resources[i] = resources[0];
                        resources[0] = resource;
                    }
                    flag++;
                }

            }
            log.debug("Get [{" + location + "}] configuration at {" + resources.toString() + "}");
        } catch (IOException e) {
            log.error("Fail to find [{" + location + "}] :", e);
        } finally {
            return resources;
        }
    }

    public static Resource[] getAllResources(String location) {

        location = prepareLocation(location);
        Resource resources[] = null;
        try {
            resources = loader.getResources(location);
            log.debug("Get [{" + location + "}] configuration at {" + location + "}");
        } catch (IOException e) {
            log.error("Fail to find [{" + location + "}] :", e);
        }
        return resources;
    }

    private static String prepareLocation(String location) {

        if (!location.startsWith("classpath"))
            location = (new StringBuilder()).append("classpath*:").append(location).toString();
        return location;
    }

    private static boolean isDominator(Resource resource) throws IOException {

        if (dominator == null)
            return resource.getURL().getProtocol().equals("file");
        if (resource.getURL().getProtocol().equals("file"))
            return true;
        else
            return resource.getURL().getFile().contains(dominator);
    }

    public static void setDominator(String d) {

        if (dominator == null) {
            log.info("Set Dominative Configurer Path: {" + d + "}");
            dominator = d;
        } else {
            log.warn("The dominator has already bean set as {" + dominator + "}, and now try to set as {" + d + "} which will be ignored.");
        }
    }

}
