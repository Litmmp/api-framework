/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.util;

import java.io.*;
import java.util.Properties;

public class PropertyUtil {

    private static Properties prop = new Properties();

    /**
     * 加载属性文件
     *
     * @param prop    属性对象
     * @param file    属性文件
     * @param charset 编码格式
     */
    public static void load(Properties prop, String file, String charset) {
        InputStreamReader reader = null;
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        try {
            reader = new InputStreamReader(input, charset);
            prop.load(reader);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 加载属性文件
     *
     * @param prop 属性对象
     * @param file 属性文件
     */
    public static void load(Properties prop, String file) {
        load(prop, file, "utf-8");
    }

    /**
     * 加载属性配置文件
     *
     * @param paths
     * @param charset
     */
    public static void load(String[] paths, String charset) {
        if (paths != null && paths.length > 0) {
            if (prop == null) {
                prop = new Properties();
            }
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                load(prop, path, charset);
            }
        }
    }

    /**
     * 根据key获取property
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return "me.zhxing.controller";
//        return prop.getProperty(key);
    }
}
