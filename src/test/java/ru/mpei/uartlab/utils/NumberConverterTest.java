package ru.mpei.uartlab.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NumberConverterTest {

    @Test
    void convertShortTest() {
        short valueToConvert = 32000;
        byte[] bytes = NumberConverter.shortToByte(valueToConvert);
        short value = (short) (bytes[0] + bytes[1] * 256);
        Assertions.assertEquals(valueToConvert, value);
    }

    @Test
    void convertBytesToShort() {
        byte[] bytes = new byte[]{-1, -1, -2};
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes[2]);
        buffer.put(bytes[1]);
        buffer.put(bytes[0]);
        buffer.put((byte) 0);
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(buffer.getInt() >> 7);
    }
}
