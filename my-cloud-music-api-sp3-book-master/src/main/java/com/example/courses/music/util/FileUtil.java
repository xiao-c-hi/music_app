package com.example.courses.music.util;

/**
 * 文件工具类
 */
public class FileUtil {
    public static String suffix(String data) {
        int indexOf = data.indexOf(".");
        if (indexOf != -1) {
            return data.substring(indexOf);
        }

        return ".u";
    }
}
