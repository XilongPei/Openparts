package com.openparts.common.utils;

import org.abstractj.kalium.crypto.Point;
import org.abstractj.kalium.encoders.Encoder;

import static org.abstractj.kalium.NaCl.Sodium.CRYPTO_BOX_CURVE25519XSALSA20POLY1305_PUBLICKEYBYTES;
import static org.abstractj.kalium.NaCl.Sodium.CRYPTO_BOX_CURVE25519XSALSA20POLY1305_SECRETKEYBYTES;
import static org.abstractj.kalium.NaCl.sodium;
import static org.abstractj.kalium.crypto.Util.checkLength;
import static org.abstractj.kalium.crypto.Util.zeros;

public class KaliumKeyPair {

    private byte[] publicKey;
    private final byte[] secretKey;

    public KaliumKeyPair() {
        this.secretKey = zeros(CRYPTO_BOX_CURVE25519XSALSA20POLY1305_SECRETKEYBYTES);
        this.publicKey = zeros(CRYPTO_BOX_CURVE25519XSALSA20POLY1305_PUBLICKEYBYTES);
        sodium().crypto_box_curve25519xsalsa20poly1305_keypair(publicKey, secretKey);
    }

    public KaliumKeyPair(byte[] secretKey) {
        this.secretKey = secretKey;
        checkLength(this.secretKey, CRYPTO_BOX_CURVE25519XSALSA20POLY1305_SECRETKEYBYTES);
        Point point = new Point();
        this.publicKey = point.mult(secretKey).toBytes();
    }

    public KaliumKeyPair(String secretKey, Encoder encoder) {
        this(encoder.decode(secretKey));
    }

    public String getPublicKey() {
        return Base58.encode(publicKey);
    }

    public String getPrivateKey() {
        return Base58.encode(secretKey);
    }
}
