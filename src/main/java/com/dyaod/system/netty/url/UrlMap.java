package com.dyaod.system.netty.url;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dyaod on 14-7-11.
 */
public class UrlMap {

    private Map<String,String> urlMap = new HashMap<String, String>();

    private Map<String,UrlBean> urlBeanMap = new HashMap<String, UrlBean>();

    public Map<String, String> getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(Map<String, String> urlMap) {
        this.urlMap = urlMap;
    }

    public Map<String, UrlBean> getUrlBeanMap() {
        return urlBeanMap;
    }

    public void setUrlBeanMap(Map<String, UrlBean> urlBeanMap) {
        this.urlBeanMap = urlBeanMap;
    }

    public void init(){

       Set<Map.Entry<String,String>> urlSet  = urlMap.entrySet();

        for(Map.Entry<String,String> urlEntry :urlSet){

         String[] urlInfo =  urlEntry.getValue().split("#");
            urlBeanMap.put(urlEntry.getKey(),new UrlBean(urlInfo));
        }

    }
}
