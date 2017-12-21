package com.openparts.common;

import com.cnpc.framework.utils.PropertiesUtil;


public class CommonConstants {

    public static final String FS_SECURITY_KEY = "ELO7hccMHrZr3D6NrgwSOEwFycWh1Y9u";

    public static final int ACCESS_TOKEN_LIFE_SECONDS = PropertiesUtil.getIntValue("access_token.life_seconds", 1000);

}
