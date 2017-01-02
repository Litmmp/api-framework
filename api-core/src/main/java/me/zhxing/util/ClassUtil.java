/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类对象工具
 */
public class ClassUtil {

    /**
     * 根据包名获取类对象，只获取该包下的类对象，不获取子包类对象
     *
     * @param packageName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Set<Class> getClasses(String packageName) throws IOException, ClassNotFoundException {
        return getClasses(packageName, false);
    }

    /**
     * 根据包名获取类对象
     *
     * @param packageName
     * @param recursion
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Set<Class> getClasses(String packageName, boolean recursion) throws IOException, ClassNotFoundException {
        Set<Class> classes = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL resource = loader.getResource(packagePath);
        if (resource != null) {
            String protocol = resource.getProtocol();
            if ("file".equals(protocol)) {
                classes = getClassFromDir(resource.getPath(), packageName, recursion);
            } else if ("jar".equals(protocol)) {
                JarFile jarFile = ((JarURLConnection) resource.openConnection()).getJarFile();
                if (jarFile != null) {
                    classes = getClassFromJar(jarFile.entries(), packageName, recursion);
                }
            }
        } else {
            classes = getCLassFromJars(((URLClassLoader) loader).getURLs(), packageName, recursion);
        }
        return classes;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls
     * @param packageName
     * @param recursion
     * @return
     * @throws ClassNotFoundException
     */
    private static Set<Class> getCLassFromJars(URL[] urls, String packageName, boolean recursion) throws IOException, ClassNotFoundException {
        Set<Class> classes = new HashSet<>();
        for (int i = 0; i < urls.length; i++) {
            String classPath = urls[i].getPath();
            //不必搜索classes文件夹
            if (classPath.endsWith("classes/")) {
                continue;
            }
            JarFile jarFile = new JarFile(classPath.substring(classPath.indexOf("/")));
            if (jarFile != null) {
                classes.addAll(getClassFromJar(jarFile.entries(), packageName, recursion));
            }
        }
        return classes;
    }

    /**
     * 从jar包获取类对象
     *
     * @param entries
     * @param packageName
     * @param recursion
     * @return
     * @throws ClassNotFoundException
     */
    private static Set<Class> getClassFromJar(Enumeration<JarEntry> entries, String packageName, boolean recursion) throws ClassNotFoundException {
        Set<Class> classes = new HashSet<>();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (!jarEntry.isDirectory()) {
                // FIXME: 16/12/17 先把"/" 转成 "." 再判断 ".class" 的做法可能会有bug
                String entryName = jarEntry.getName().replace("/", ".");
                if (entryName.endsWith(".class") && !entryName.contains("$") && entryName.startsWith(packageName)) {
                    entryName = entryName.replace(".class", "");
                    if (recursion) {
                        classes.add(Class.forName(entryName));
                    } else if (!entryName.replace(packageName + ".", "").contains(".")) {
                        classes.add(Class.forName(entryName));
                    }
                }
            }
        }
        return classes;
    }

    /**
     * 从目录获取类对象
     *
     * @param filePath
     * @param packageName
     * @param recursion
     * @return
     * @throws ClassNotFoundException
     */
    private static Set<Class> getClassFromDir(String filePath, String packageName, boolean recursion) throws ClassNotFoundException {
        Set<Class> classes = new HashSet<>();
        File[] files = new File(filePath).listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (recursion) {
                    classes.addAll(getClassFromDir(file.getPath(), packageName + "." + file.getName(), recursion));
                }
            } else {
                String fileName = file.getName();
                if (fileName.endsWith(".class") && !fileName.contains("$")) {
                    String className = packageName + "." + fileName.replace(".class", "");
                    classes.add(Class.forName(className));
                }
            }
        }
        return classes;
    }

}