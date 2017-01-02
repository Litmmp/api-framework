/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.handler;

import me.zhxing.model.ApiResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * HTTP响应处理者
 */
public class ResponseHandler {

    /**
     * 处理HTTP响应
     *
     * @param request
     * @param response
     * @param apiResponse
     */
    public static void handleHttpResponse(HttpServletRequest request, HttpServletResponse response, ApiResponse apiResponse) {
        // TODO: 16/12/24 返回响应
        response.setHeader("content-type", "text/html;charset=UTF-8");
        try {
            OutputStream out = response.getOutputStream();
            String msg = "成功";
            out.write(msg.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}