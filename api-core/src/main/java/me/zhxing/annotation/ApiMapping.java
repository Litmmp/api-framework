/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.annotation;

import java.lang.annotation.*;

/**
 * Api映射注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiMapping {

    /**
     * 请求目标
     *
     * @return api映射路径
     */
    String value();

}
