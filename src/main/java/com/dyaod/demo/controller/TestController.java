package com.dyaod.demo.controller;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;

/**
 * Created by dyaod on 14-7-11.
 */
public class TestController {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TestController.class);

    public void init(){
        logger.info("TestController init");
    }

    public String index(FullHttpRequest request, Map<String, String> parameterMap,String content ){

        logger.info("index:"+content);
        return content;
    }

    public String hello(FullHttpRequest request, Map<String, String> parameterMap,String content){


        return content;
    }
}
