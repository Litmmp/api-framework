/*
 * Copyright Litmmp and other contributors
 * Released under the MIT license
 *
 * Author: Litmmp
 * Email: litmmp@163.com
 */

package me.zhxing.util.httpclient;

public class ResponseEntity {
    private int statusCode;
    private String reasonPhrase;
    private String content;

    public int getStatusCode() {
        return statusCode;
    }

    protected void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    protected void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }
}
