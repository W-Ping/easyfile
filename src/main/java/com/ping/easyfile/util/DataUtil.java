package com.ping.easyfile.util;

import java.util.Arrays;

/**
 * @author liu_wp
 * @date Created in 2019/3/6 19:35
 * @see
 */
public class DataUtil {
    public static <T> T[] concatAll(T[] first, T[]... rest) {
        if (rest == null || rest.length == 0) {
            return first;
        }
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static <T> T[] concatAll(T[] first, T... rest) {
        if (rest == null || rest.length == 0) {
            return first;
        }
        int totalLength = first.length;
        T[] result = Arrays.copyOf(first, rest.length + totalLength);
        int offset = first.length;
        System.arraycopy(rest, 0, result, offset, rest.length);
        return result;
    }
}
