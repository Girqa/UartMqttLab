package ru.mpei.uartlab.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NumberConverter {

    public static byte[] shortToByte(short value) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putShort(value);
        return buffer.array();
    }
}
