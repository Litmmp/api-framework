/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.handler;

import me.zhxing.model.ApiRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP请求处理者
 */
public class RequestHandler {

    /**
     * 校验HTTP请求
     *
     * @param request 请求
     * @return 通过校验true，校验失败false
     */
    public static boolean validHttpRequest(HttpServletRequest request) {
        // TODO: 16/12/24 校验参数
        return true;
//        String servletPath = request.getServletPath();
//        return !(servletPath == null || servletPath.isEmpty());
    }

    /**
     * 转换HTTP请求为API请求
     *
     * @param request HTTP请求
     * @return API请求
     */
    public static ApiRequest getApiRequest(HttpServletRequest request) {
        return new ApiRequest(request);
    }

    /**
     * 验证请求参数
     *
     * @param request API请求
     * @return 校验通过返回true，失败返回false
     */
    public static boolean validRequestParams(ApiRequest request) {
        // TODO: 16/12/17 暂时还没有校验功能，有待增加
        return true;
    }
}
