package com.jollychic.data.service.util;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Author: Boris
 * Date: 2018/7/5 17:21
 * Copyright (C), 2017-2018
 * Description:
 */
public class HttpClientAsynUtil extends UtilConstant {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HttpClientAsynUtil.class);

    private static CloseableHttpAsyncClient httpclient;

    private static ContentType DefaultContentType;

    static Map<String, String> HEADERS = new HashMap<>(1, 1);

    static {
        if(httpclient == null){
            init();
        }
    }

    public static String get(String url) throws Exception {
        Future<HttpResponse> rsp ;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HttpHeaders.CONTENT_TYPE, DefaultContentType.getMimeType());
        for (Map.Entry<String, String> e : HEADERS.entrySet()) {
            httpGet.setHeader(e.getKey(), e.getValue());
        }
        rsp = httpclient.execute(httpGet, null);

        return EntityUtils.toString(rsp.get().getEntity(), UtilConstant.httpCharset);
    }

    public static void init(){
        try {
            HEADERS.put("Api-Lang", "java");
            DefaultContentType = ContentType.create("application/x-www-form-urlencoded",
                    Charset.forName(UtilConstant.httpCharset));

            //配置io线程
            IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors())
                    .setConnectTimeout(UtilConstant.httpConnTimeout)//连接超时,连接建立时间,三次握手完成时间
                    .setSoTimeout(UtilConstant.httpSoTimeout).build();//请求超时,数据传输过程中数据包之间间隔的最大时间

            //设置连接池大小
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
            PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor);
            ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Charset.forName(UtilConstant.httpCharset)).build();
            connManager.setDefaultConnectionConfig(connectionConfig);

            //连接池中最大连接数
            connManager.setMaxTotal(UtilConstant.httpConnMaxtotal);

            /**
             * 分配给同一个route(路由)最大的并发连接数,route为运行环境机器到目标机器的一条线路,
             * 举例来说,我们使用HttpClient的实现来分别请求 www.baidu.com 的资源和 www.bing.com 的资源那么他就会产生两个route;
             */
            connManager.setDefaultMaxPerRoute(UtilConstant.httpConnMaxpreroute);
            HttpAsyncClientBuilder httpAsyncClientBuilder = HttpAsyncClients.custom().setConnectionManager(connManager);
            httpclient = httpAsyncClientBuilder.build();
            httpclient.start();
        } catch (Exception e) {
            logger.error("init HttpAsyncClient fail:",e);
        }
    }

    public static void close() {
        if (httpclient != null) {
            try {
                httpclient.close();
                logger.info("httpAsynclient closed");
            } catch (IOException e) {
                logger.error("httpAsynclient close fail:",e);
            }
        }
    }
}
