package com.jqsoft.nursing.util;

import java.util.Collection;

/**
 * @author yedong
 * @date 2019/1/7
 */
public class CommentUtil {

    /**
     * 判断数组是否为null
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断集合是否为空
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isEmpty(Collection<T> array) {
        return array == null || array.isEmpty();
    }

}
