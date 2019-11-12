package com.jqsoft.nursing.util;

/**
 * Created by admin on 2018/2/7.
 */

public enum SchemeType {
    TRIGLE,
    INDEX,
    BACKGROUND;

    public static SchemeType valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }

}
