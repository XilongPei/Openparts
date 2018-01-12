package com.cnpc.framework.utils;

import com.google.common.base.MoreObjects;
import com.cnpc.framework.utils.CompressEncoding;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import com.cnpc.framework.utils.UuidIdentifierGenerator;

/**
 * Represents an Openparts access token and its expiration information.
 */
public class AccessToken implements Serializable {

	private static final long serialVersionUID = -8514239465808977353L;

	private final String key;
	private final String value;

	public AccessToken(String str) {
		this.key = CompressEncoding.CompressNumber(System.currentTimeMillis(),6) + "-" + UuidIdentifierGenerator.randomShortUUID();
        this.value = str;
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
