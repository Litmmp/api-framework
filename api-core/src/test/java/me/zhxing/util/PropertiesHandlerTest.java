/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.util;

import org.junit.Test;

public class PropertiesHandlerTest {
    @Test
    public void load() throws Exception {
        PropertyUtil.load(new String[]{"db.properties"}, "UTF-8");
        System.out.println(PropertyUtil.getValue("db.url"));
    }

    @Test
    public void getByKey() throws Exception {

    }

}