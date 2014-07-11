package com.dyaod.demo.controller;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;

/**
 * Created by dyaod on 14-7-11.
 */
public class Test2Controller {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Test2Controller.class);

    public void init(){
        logger.info("Test2Controller init");
    }

    public String hello(FullHttpRequest request, Map<String, String> parameterMap){

        return parameterMap.get("name");
    }

}
