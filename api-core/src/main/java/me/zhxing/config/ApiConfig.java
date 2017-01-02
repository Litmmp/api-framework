/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.config;

import me.zhxing.annotation.ApiMapping;
import me.zhxing.model.ApiMethod;
import me.zhxing.util.ClassUtil;
import me.zhxing.util.PropertyUtil;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * API配置信息
 */
public class ApiConfig {

    private static String API_PACKAGE = "api.package";

    //API配置
    private static Properties prop = new Properties();
    //API处理方法
    private static Map<String, ApiMethod> apiMethods = new HashMap<>();

    /**
     * 根据映射名获取API方法
     *
     * @param mappingName api映射名称
     * @return 对应的API方法模型
     */
    public static ApiMethod getApiMethod(String mappingName) {
        return apiMethods.get(mappingName);
    }

    /**
     * 初始化API配置
     *
     * @param file 属性配置文件
     */
    public static void init(String file) {
        PropertyUtil.load(prop, file);
        initApiMethod(prop.getProperty(API_PACKAGE));
    }

    /**
     * 初始化API方法
     *
     * @param apiPackage api包路径
     */
    private static void initApiMethod(String apiPackage) {
        try {
            Set<Class> classes = ClassUtil.getClasses(apiPackage);
            for (Class clazz : classes) {
                Method[] methods = clazz.getMethods();//只获取public method
                if (methods != null && methods.length > 0) {
                    Object object = clazz.newInstance();
                    for (Method method : methods) {
                        ApiMapping annotation = method.getAnnotation(ApiMapping.class);
                        if (annotation != null) {
                            addApiMethod(object, method, annotation);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增API方法
     *
     * @param object     调用对象
     * @param method     被调用方法
     * @param annotation API映射注解
     */
    private static void addApiMethod(Object object, Method method, ApiMapping annotation) {
        String mappingName = annotation.value();
        ApiMethod apiMethod = new ApiMethod();
        apiMethod.setInvokeObject(object);
        apiMethod.setMapping(mappingName);
        apiMethod.setMethod(method);
        apiMethod.setParamClasses(getParamClasses(method));
        apiMethod.setParamNames(getParamNames(method));
        apiMethods.put(mappingName, apiMethod);
    }

    /**
     * 获取参数类型
     *
     * @param method
     * @return
     */
    private static Class<?>[] getParamClasses(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        return parameterTypes;
    }

    /**
     * 获取参数名称
     *
     * @param method
     * @return
     */
    private static String[] getParamNames(Method method) {
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        return discoverer.getParameterNames(method);
    }

}