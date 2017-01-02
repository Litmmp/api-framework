/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.model;

import java.lang.reflect.Method;

/**
 * API方法模型
 */
public class ApiMethod {
    /**
     * 调用对象
     */
    private Object invokeObject;
    /**
     * API映射名
     */
    private String mapping;
    /**
     * API方法
     */
    private Method method;
    /**
     * API参数类型
     */
    private Class<?>[] paramClasses;
    /**
     * API参数名称
     */
    private String[] paramNames;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public Object getInvokeObject() {
        return invokeObject;
    }

    public void setInvokeObject(Object invokeObject) {
        this.invokeObject = invokeObject;
    }


    public String[] getParamNames() {
        return paramNames;
    }

    public void setParamNames(String[] paramNames) {
        this.paramNames = paramNames;
    }

    public Class<?>[] getParamClasses() {
        return paramClasses;
    }

    public void setParamClasses(Class<?>[] paramClasses) {
        this.paramClasses = paramClasses;
    }
}
