package com.example.courses.music.util;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import nl.basjes.parse.useragent.classify.DeviceClass;
import nl.basjes.parse.useragent.classify.UserAgentClassifier;

import static nl.basjes.parse.useragent.classify.DeviceClass.DESKTOP;

/**
 * user agent工具类
 */
public class UserAgentUtil {
    /**
     * 是否是移动设备
     *
     * @param data
     * @return
     */
    public static boolean isMobile(String data) {
        data = data.toLowerCase();

        if (data.contains("iphone") || data.contains("android")) {
            return true;
        }
        return false;
    }

    /**
     * 是否是电脑
     *
     * @param data
     * @return
     */
    public static boolean isDesktop(String data) {
        UserAgentAnalyzer uaa = UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withCache(10000)
                .build();

        UserAgent agent = uaa.parse(data);
        DeviceClass deviceClass = UserAgentClassifier.getDeviceClass(agent);
        return deviceClass == DESKTOP;
    }
}