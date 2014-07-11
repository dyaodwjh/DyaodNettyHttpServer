package com.dyaod.system.netty;

import com.dyaod.system.NettyServer;
import com.dyaod.system.config.AppConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class NettyService {

    private Logger logger = Logger.getLogger(NettyService.class);

    @Autowired
    private AppConfig appConfig;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap serverBootstrap;

    private LinkedHashMap<String,String> channelHandlerLinkedMap = new LinkedHashMap<String, String>();

    public boolean init() {
        logger.info("Netty 服务器 init");
        this.bossGroup = new NioEventLoopGroup(appConfig.getBossGroupSize());
        this.workerGroup = new NioEventLoopGroup(appConfig.getWorkerGroupSize());
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        Iterator<Map.Entry<String, String>> iterator = channelHandlerLinkedMap.entrySet().iterator();
                        while(iterator.hasNext()){
                            Map.Entry<String, String> channelHandlerEntry = iterator.next();

                            ch.pipeline().addLast(channelHandlerEntry.getKey(), (ChannelHandler) NettyServer.context.getBean(channelHandlerEntry.getValue()));
                        }

                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, false)
                .childOption(ChannelOption.TCP_NODELAY, true);


        return true;
    }

    public void run() throws InterruptedException {
        try {
            ChannelFuture future = this.serverBootstrap.bind(appConfig.getIp(), appConfig.getPort()).sync();
            logger.info("Netty 服务器启动，网址是 : " + "http://" + appConfig.getIp() + ":" + appConfig.getPort());
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public boolean sayHello() {
        System.out.println("Hello I am netty");
        return false;
    }

    public LinkedHashMap<String, String> getChannelHandlerLinkedMap() {
        return channelHandlerLinkedMap;
    }

    public void setChannelHandlerLinkedMap(LinkedHashMap<String, String> channelHandlerLinkedMap) {
        this.channelHandlerLinkedMap = channelHandlerLinkedMap;
    }
}
