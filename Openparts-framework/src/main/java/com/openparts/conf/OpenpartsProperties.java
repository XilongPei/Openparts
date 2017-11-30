package com.openparts.conf;

import java.io.InputStream;
import java.util.Properties;

public class OpenpartsProperties {

    public final static Properties props = new Properties();

    static {
        OpenpartsProperties.read("openparts.properties");
    }


    public static void read(String path) {
        try {
            InputStream in = OpenpartsProperties.class.getClassLoader()
                    .getResourceAsStream(path);

            props.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getValue(String key) {
        try {
            String returnStr = props.getProperty(key);

            if (returnStr != null) {
                return returnStr;
            }

            return "";

        } catch (Exception e) {
            return "";
        }

    }
}
