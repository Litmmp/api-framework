/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.controller;

import base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import util.TestUtil;

import static org.junit.Assert.*;


public class TestControllerTest extends BaseTest {

    @Autowired
    private TestController controller;

    @Test
    public void login() throws Exception {
        Object obj = controller.login("zhxing", "pwd");
        TestUtil.print(obj);
    }

}