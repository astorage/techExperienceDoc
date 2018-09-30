package com.gmv.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    /**
     * 参数字段名userName
     */
    public static final String FIELD_USER_NAME = "userName";
    /**
     * 参数字段名verifyInfo
     */
    public static final String FIELD_VERIFYINFO = "verifyInfo";
    /**
     * 参数字段名dataInfos
     */
    public static final String FIELD_DATAINFOS = "dataInfos";
    /**
     * 请求http地址
     */
    public static final String FIELD_URL = "url";

    /**
     * Content-Type
     */
    private static final String CONTENT_TYPE = "Content-Type";
    /**
     * Content-Type 的值
     */
    private static final String HTTP_HEADER_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * 字符集
     */
    private static final String CHARSET = "UTF-8";


    private static HttpClient httpClient;
    private static HttpPost httpPost = new HttpPost();

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(10000);
        cm.setDefaultMaxPerRoute(10000);
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(globalConfig);
        httpClient = httpClientBuilder.build();

        httpPost.addHeader(CONTENT_TYPE, HTTP_HEADER_CONTENT_TYPE);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
    }

    /**
     * 用表单方式请求http接口
     * @param param 接口需要的参数
     * @return 接口返回结果字符串
     * @throws Exception
     */
    public static String sendPostWithForm (Map<String, String> param,  Logger logger)throws Exception{
        try {
            httpPost.setURI(URI.create(param.get(FIELD_URL)));
            //表单方式
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs,CHARSET);
            httpPost.setEntity(urlEncodedFormEntity);

            Long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpPost);
            Long endTime = System.currentTimeMillis();
            logger.info("本次调用接口耗时：" + (endTime - startTime) + "毫秒");

            HttpEntity httpEntity = response.getEntity();
            String resultStr = EntityUtils.toString(httpEntity, CHARSET);
            return resultStr;
        }finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }


    }
}
