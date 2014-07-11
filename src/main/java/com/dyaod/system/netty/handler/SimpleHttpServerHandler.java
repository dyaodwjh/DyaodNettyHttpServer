package com.dyaod.system.netty.handler;

import com.dyaod.system.NettyServer;
import com.dyaod.system.netty.NettyService;
import com.dyaod.system.netty.tools.Tools;
import com.dyaod.system.netty.url.UrlBean;
import com.dyaod.system.netty.url.UrlMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by dyaod on 14-7-9.
 */
public class SimpleHttpServerHandler extends HttpServerHandler {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SimpleHttpServerHandler.class);

    private final static Charset UTF_8 = Charset.forName("UTF-8");

    @Autowired
    private UrlMap urlMap;

    @Override
    protected void deal(ChannelHandlerContext ctx, FullHttpRequest request, String url, Map<String, String> parameterMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        HttpMethod httpMethod = request.getMethod();

        UrlBean urlBean = urlMap.getUrlBeanMap().get(url);

        if (urlBean == null) {
            send404(ctx);
            return;
        }

        String result = "";
        if (httpMethod.equals(HttpMethod.GET)) {

            if (!urlBean.getType().equalsIgnoreCase(HttpMethod.GET.name())) {
                send405(ctx);
                return;
            }

            result = dealGet(ctx, request, url, parameterMap, urlBean);
        } else if (httpMethod.equals(HttpMethod.POST)) {

            if (!urlBean.getType().equalsIgnoreCase(HttpMethod.POST.name())) {
                send405(ctx);
                return;
            }

            result = dealPost(ctx, request, url, parameterMap, Tools.convert(request.content().toString(UTF_8)), urlBean);
        }

        sendSuccess(ctx, result);
    }


    private String dealGet(ChannelHandlerContext ctx, FullHttpRequest request, String url, Map<String, String> parameterMap, UrlBean urlBean) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Object object = NettyServer.context.getBean(urlBean.getBeanName());
        Class c = object.getClass();
        Method method = c.getMethod(urlBean.getMethod(), new Class[]{FullHttpRequest.class,Map.class});

        return (String) method.invoke(object, request, parameterMap);


    }


    private String dealPost(ChannelHandlerContext ctx, FullHttpRequest request, String url, Map<String, String> parameterMap, String content, UrlBean urlBean) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object object = NettyServer.context.getBean(urlBean.getBeanName());
        Class c = object.getClass();
        Method method = c.getMethod(urlBean.getMethod(), new Class[]{FullHttpRequest.class,Map.class,String.class});

        return (String) method.invoke(object, request, parameterMap,content);
    }


}
