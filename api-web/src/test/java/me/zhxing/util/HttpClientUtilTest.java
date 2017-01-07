/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.util;

import me.zhxing.util.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpClientUtilTest {

    @Test
    public void post() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("param1", "1");
        params.put("param2", "2");
        HttpClientUtil.post("http://www.baidu.com", params);
    }

    @Test
    public void get() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("param1", "1");
        params.put("param2", "2");
        HttpClientUtil.get("http://www.baidu.com", params);
    }

}