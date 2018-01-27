package com.cnpc.framework.utils;

import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.util.UUID;
import org.apache.commons.lang.math.RandomUtils;
import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

/**
 * The highest bit of the generated UUID is reserved, it means the first letter A-Z is reserved.
 * This design is for UUID could be manually edited.
 */
public class UuidIdentifierGenerator implements IdentifierGenerator {
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'};

    private static volatile NoArgGenerator timeBasedGenerator = null;

    @Override
    public Serializable generate(SessionImplementor s, Object obj) {
        return randomShortUUID();
    }

    public static String randomShortUUID() {
        long number1, number2;
        int intRandom = RandomUtils.nextInt(8);

        /*
         * UUID Version 4：随机UUID
         * 根据随机数，或者伪随机数生成UUID。这种UUID产生重复的概率是可以计算出来的，但随机的东
         * 西就像是买彩票：你指望它发财是不可能的，但狗屎运通常会在不经意中到来。
         *
         * UUID uuid = UUID.randomUUID();
         *
         * UUID Version 1：基于时间的UUID
         * 基于时间的UUID通过计算当前时间戳、随机数和机器MAC地址得到。由于在算法中使用了MAC地
         * 址，这个版本的UUID可以保证在全球范围的唯一性。但与此同时，使用MAC地址会带来安全性问
         * 题，这就是这个版本UUID受到批评的地方。如果应用只是在局域网中使用，也可以使用退化的算
         * 法，以IP地址来代替MAC地址－－Java的UUID往往是这样实现的（当然也考虑了获取MAC的难度）。
         */

        //Generate custom UUID from network interface
        NoArgGenerator ng = timeBasedGenerator;
        if (ng == null) {
            timeBasedGenerator = ng = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        }
        UUID uuid = ng.generate();

        number1 = uuid.getMostSignificantBits();
        number2 = uuid.getLeastSignificantBits();

        char[] buf = new char[32];
        int charPos = 32;
        long mask = (1 << 6) - 1;
        int i;

        for (i = 0;  i < 64/6; i++) {
            buf[--charPos] = digits[(int)(number1 & mask)];
            number1 >>>= 6;
        }
        buf[--charPos] = digits[(int)(number1 & mask) | ((intRandom & 0x3) << 4)];

        for (i = 0;  i < 64/6; i++) {
            buf[--charPos] = digits[(int)(number2 & mask)];
            number2 >>>= 6;
        }
        buf[--charPos] = digits[(int)(number2 & mask) | ((intRandom & 0x4) << 2)];

        return new String(buf, charPos, (32 - charPos));
    }
}
