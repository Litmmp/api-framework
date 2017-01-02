/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.servlet;

import me.zhxing.annotation.RequestModel;
import me.zhxing.config.ApiConfig;
import me.zhxing.handler.RequestHandler;
import me.zhxing.handler.ResponseHandler;
import me.zhxing.model.ApiMethod;
import me.zhxing.model.ApiRequest;
import me.zhxing.model.ApiResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class ApiServlet extends HttpServlet {

    //待定，匹配请求URL是否符合API规范
    private static Pattern getPattern = Pattern.compile("");
    private static Pattern postPattern = Pattern.compile("");



    @Override
    public void init() throws ServletException {
        super.init();
        String file = getInitParameter("config");
        //初始化Api配置
        ApiConfig.init(file);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        process(req, resp);
    }

    /**
     * 暂不实现
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    /**
     * 暂不实现
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * 处理API请求
     *
     * @param request
     * @param response
     */
    private void process(HttpServletRequest request, HttpServletResponse response) {
        ApiResponse result = new ApiResponse();
        if (!RequestHandler.validHttpRequest(request)) {//请求校验不通过
            result.setCode("9999");
            result.setMsg("请求不合法");
            ResponseHandler.handleHttpResponse(request, response, result);
        } else {//请求校验通过
            ApiRequest apiRequest = RequestHandler.getApiRequest(request);
            //校验请求参数
            if (RequestHandler.validRequestParams(apiRequest)) {
                //得到Api方法模型
                ApiMethod apiMethod = ApiConfig.getApiMethod(apiRequest.getApiMapping());
                Object invokeObject = apiMethod.getInvokeObject();
                Method method = apiMethod.getMethod();
                Class<?>[] apiParamClasses = apiMethod.getParamClasses();
                String[] apiParamNames = apiMethod.getParamNames();
                Object[] apiParamValues = new Object[apiParamClasses.length];
                for (int i = 0; i < apiParamNames.length; i++) {
                    Map<String, String> params = apiRequest.getParams();
                    apiParamValues[i] = convertValue(params.get(apiParamNames[i]), apiParamClasses[i]);
                }
                try {
                    //结果
                    Object invoke = method.invoke(invokeObject, apiParamValues);
                    ApiResponse apiResponse = new ApiResponse();
                    apiResponse.setCode("0000");
                    apiResponse.setMsg("成功");
                    apiResponse.setContent(invoke);
                    ResponseHandler.handleHttpResponse(request, response, apiResponse);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                // TODO: 16/12/18 校验参数不通过
            }
        }
    }

    /**
     * 验证API请求是否规范
     *
     * @param request
     * @return
     */
    private boolean validRequestURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        return requestURL == null ? false : true;
    }

    /**
     * 转换参数值
     *
     * @param value
     * @param clazz
     * @return
     */
    private Object convertValue(String value, Class<?> clazz) {
        if (value == null) {
            return null;
        } else if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.TYPE) {
            return Integer.parseInt(value);
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == Long.TYPE) {
            return Long.parseLong(value);
        } else if (clazz == Long.class) {
            return Long.valueOf(value);
        } else if (clazz == Double.TYPE) {
            return Double.parseDouble(value);
        } else if (clazz == Double.class) {
            return Double.valueOf(value);
        } else if (clazz == Float.TYPE) {
            return Float.parseFloat(value);
        } else if (clazz == Float.class) {
            return Float.valueOf(value);
        } else if (clazz == Boolean.TYPE) {
            return Boolean.parseBoolean(value);
        } else if (clazz == Boolean.class) {
            return Boolean.valueOf(value);
        } else if (clazz == Short.TYPE) {
            return Short.parseShort(value);
        } else if (clazz == Short.class) {
            return Short.valueOf(value);
        } else if (clazz == Byte.TYPE) {
            return Byte.parseByte(value);
        } else if (clazz == Byte.class) {
            return Byte.valueOf(value);
        } else {
            Annotation annotation = clazz.getAnnotation(RequestModel.class);
            if (annotation != null) {
                // TODO: 16/12/24 处理对象类型参数
            }
        }
        return null;
    }


}
