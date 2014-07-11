package com.dyaod.system;

import com.dyaod.system.netty.NettyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NettyServer {

    public static ApplicationContext context;

    public static void run() throws InterruptedException {
        context = new ClassPathXmlApplicationContext("ApplicatonContext.xml");
        NettyService nettyService = context.getBean(NettyService.class);
        nettyService.run();
    }
}
