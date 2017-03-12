package com.example.phoenix.nab.common;

/**
 * Created by Phoenix on 3/11/17.
 */

public class AppUtils {
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
