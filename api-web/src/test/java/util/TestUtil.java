/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package util;

import com.alibaba.fastjson.JSONObject;

public class TestUtil {

    public static void print(Object object) {
        System.out.println(JSONObject.toJSONString(object, true));
    }

}
