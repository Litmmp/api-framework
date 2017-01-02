/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.controller;

import me.zhxing.annotation.ApiMapping;

public class TestController {

    @ApiMapping("/login")
    public Object login(String name, String pwd) {
        System.out.println(name + "    " + pwd);
        return null;
    }

}
