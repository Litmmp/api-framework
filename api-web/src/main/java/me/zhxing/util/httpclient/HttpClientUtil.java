/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.util.httpclient;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * get request
     *
     * @param url    origin url
     * @param params request paramters
     * @return ResponseEntity object
     */
    public static ResponseEntity get(String url, Map<String, String> params) {
        if (!checkURL(url)) return null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        ResponseEntity entity = null;
        try {
            // get complete url, include params
            URL temp = new URL(url);
            URIBuilder builder = new URIBuilder();
            builder.setScheme(temp.getProtocol()).setHost(temp.getHost()).setPath(temp.getPath());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }
            HttpGet httpget = new HttpGet(builder.toString());
            logger.debug("Executing get request {}", httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            entity = toResponseEntity(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.debug("Releasing http client: {}", httpclient.getClass().getName());
        }
        return entity;
    }

    /**
     * post request
     *
     * @param url    origin url
     * @param params request paramters
     * @return ResponseEntity object
     */
    public static ResponseEntity post(String url, Map<String, String> params) {
        if (!checkURL(url)) return null;
        // post method
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        // post parameter
        List<NameValuePair> parameters = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        ResponseEntity entity = null;
        try {
            httppost.setEntity(new UrlEncodedFormEntity(parameters));
            logger.debug("Executing post request {}", httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            entity = toResponseEntity(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.debug("Releasing http client: {}", httpclient.getClass().getName());
        }
        return entity;
    }

    /**
     * check url
     *
     * @param url origin url
     * @return true or false
     */
    private static boolean checkURL(String url) {
        if (url == null || url.isEmpty()) {
            logger.debug("Parameter is error: url is null or empty");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 转换成自定义响应
     *
     * @param response Http response
     * @return ResponseEntity object
     * @throws IOException IOException
     */
    private static ResponseEntity toResponseEntity(CloseableHttpResponse response) throws IOException {
        ResponseEntity entity = new ResponseEntity();
        entity.setStatusCode(response.getStatusLine().getStatusCode());
        entity.setReasonPhrase(response.getStatusLine().getReasonPhrase());
        entity.setContent(EntityUtils.toString(response.getEntity()));
        return entity;
    }

}