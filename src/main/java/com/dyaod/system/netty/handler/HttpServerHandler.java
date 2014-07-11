package com.dyaod.system.netty.handler;

import com.dyaod.system.netty.Constant;
import com.dyaod.system.netty.tools.Tools;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by dyaod on 14-7-9.
 */
public abstract class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private Logger logger = Logger.getLogger(HttpServerHandler.class);

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) {

        try {
            if (!request.getDecoderResult().isSuccess()) {
                sendError(ctx, BAD_REQUEST);
                return;
            }
//        if (request.getMethod() != GET) {
//            sendError(ctx, METHOD_NOT_ALLOWED);
//            return;
//        }

            final String uri = URLDecoder.decode(request.getUri(), "utf-8");

            /****
             * 记录请求的信息,并通过logger输出
             */
//        {
//            SocketAddress remoteAddress =  ctx.channel().remoteAddress();
//
//            logger.info("client Request Id:"+ ctx.channel().id());
//            logger.info("Request method:"+ request.getMethod().name());
//            logger.info("client Information:"+remoteAddress.toString());
            logger.info(" Threads count " + Thread.activeCount() + " || Current Thread :" + Thread.currentThread().getName());
//            logger.info("requestUri:" + uri);
//            logger.info("==========Headers Information  start ======================");
//            HttpHeaders httpHeaders = request.headers();
//            List<Map.Entry<String, String>> headerEntryList = httpHeaders.entries();
//            for (Map.Entry<String, String> entry : headerEntryList) {
//                logger.info(entry.getKey() + ":" + entry.getValue());
//            }
//            logger.info("==========Headers Information  end ======================");
//        }


            Map<String, String> parameterMap = new HashMap<String, String>();
            String url = dealUri(uri, parameterMap);

            deal(ctx, request, url, parameterMap);
        } catch (Exception e) {
          e.printStackTrace();
          sendError(ctx,e.getMessage());
        }
    }



    /**
     * 解析URL信息,提取参数并放到map中
     *
     * @param uri
     * @return
     */
    private String dealUri(String uri, Map<String, String> paramMap) {

        int paramIndex = uri.indexOf("?");

        String url = uri;

        if (paramIndex != -1) {
            String paramStr = Tools.convert(uri.substring(paramIndex + 1));

            String[] params = paramStr.split("&");

            for (String param : params) {

                String[] p = param.split("=");
                if (p.length == 2) {
                    paramMap.put(p[0], p[1]);
                    System.out.println(p[0] + "=" + p[1]);
                }

            }
            url = uri.substring(0, paramIndex + Constant.PREFIX.length() - 3);
        }

        logger.info("url:" + url);

        return url;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }


    protected void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void sendError(ChannelHandlerContext ctx, String message) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        response.content().writeBytes(message.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


    protected void sendSuccess(ChannelHandlerContext ctx, String msg) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                OK, Unpooled.copiedBuffer("Success: " + OK.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.content().writeBytes(msg.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    protected void send404(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    protected void send405(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.METHOD_NOT_ALLOWED);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


    /**
     * 用来处理实际的业务逻辑
     *
     * @param ctx
     * @param request
     * @param parameterMap
     */
    protected abstract void deal(ChannelHandlerContext ctx, FullHttpRequest request, String url, Map<String, String> parameterMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;


}
