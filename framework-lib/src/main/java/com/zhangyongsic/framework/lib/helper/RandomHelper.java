package com.zhangyongsic.framework.lib.helper;

public class RandomHelper {

    private static final String RANDOM_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final java.util.Random RANDOM = new java.util.Random();

    public static String randomString() {
        return randomString(16);
    }

    public static String randomString(int size) {
        if (size <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(RANDOM_STR.charAt(RANDOM.nextInt(RANDOM_STR.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(randomString());
    }
}
