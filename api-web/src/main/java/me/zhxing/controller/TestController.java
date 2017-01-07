/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.controller;

import com.alibaba.fastjson.JSONObject;
import me.zhxing.annotation.ApiMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @ApiMapping("/login")
    public Object login(String name, String pwd) {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("pwd", pwd);
        return object;
    }

}
