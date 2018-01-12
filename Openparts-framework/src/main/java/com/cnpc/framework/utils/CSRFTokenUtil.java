package com.cnpc.framework.utils;

import javax.servlet.http.HttpServletRequest;
import com.cnpc.framework.utils.UuidIdentifierGenerator;

public class CSRFTokenUtil {

    public static String generate(HttpServletRequest request) {
        return UuidIdentifierGenerator.randomShortUUID();
    }
}
