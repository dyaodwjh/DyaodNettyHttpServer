package com.dyaod.system.netty.url;

/**
 * Created by dyaod on 14-7-11.
 */
public class UrlBean {

    public UrlBean(String[] urlInfo) {
        this.beanName = urlInfo[0];
        this.method = urlInfo[1];
        this.type = urlInfo[2];
    }

    public static  enum REQUEST_METHOD_TYPE{GET,POST};

    private String url;
    private String beanName;
    private String method;
    private String type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
