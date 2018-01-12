package com.cnpc.framework.utils;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 */
public class UuidIdentifierGenerator implements IdentifierGenerator {
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};

    @Override
    public Serializable generate(SessionImplementor s, Object obj) {
        long number1, number2;

        UUID uuid = UUID.randomUUID();
        number1 = uuid.getMostSignificantBits();
        number2 = uuid.getLeastSignificantBits();

        char[] buf = new char[64];
        int charPos = 64;
        long mask = (1 << 6) - 1;
        int i;

        for (i = 0;  i < (64+5)/6; i++) {
            buf[--charPos] = digits[(int) (number1 & mask)];
            number1 >>>= 6;
        }

        for (i = 0;  i < (64+5)/6; i++) {
            buf[--charPos] = digits[(int) (number2 & mask)];
            number2 >>>= 6;
        }

        return new String(buf, charPos, (64 - charPos));
    }
}
