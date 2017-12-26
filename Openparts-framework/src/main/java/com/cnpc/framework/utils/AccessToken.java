package com.cnpc.framework.utils;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents an Openparts access token and its expiration information.
 */
public class AccessToken implements Serializable {

	private static final long serialVersionUID = -8514239465808977353L;

	private final String key;
	private final String value;

	public AccessToken() {
        // UUID是由一个十六进制形式的数字组成,表现出来的形式例如
        // 550E8400-E29B-11D4-A716-446655440000
        String s = UUID.randomUUID().toString();

		this.key = s.replaceAll("-", "");

		Long expirationTimeMillis = System.currentTimeMillis() + 1000;
        this.value = expirationTimeMillis.toString();
	}

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static boolean checkExpiresIn(String value) {
        if (value == null) {
            return false;
        }

        Long myExpirationTimeMillis = Long.parseLong(value);
        if (myExpirationTimeMillis > System.currentTimeMillis())
            return true;

        return false;
    }
}
