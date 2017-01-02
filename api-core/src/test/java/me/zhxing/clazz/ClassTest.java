/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.clazz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassTest {

    public void getString(String str1, Integer num) {
        System.out.println(str1 + "    " + num);
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method method = ClassTest.class.getMethod("getString", String.class, Integer.class);
        ClassTest object = new ClassTest();
        Object invoke = method.invoke(object, new Object[]{"string", new Integer(2)});
        System.out.println(invoke);
    }


}
