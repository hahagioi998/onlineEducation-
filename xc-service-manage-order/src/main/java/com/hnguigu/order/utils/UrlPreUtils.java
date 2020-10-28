package com.hnguigu.order.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentLengthStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.omg.IOP.Encoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springfox.documentation.spring.web.json.Json;

import java.awt.print.Pageable;
import java.io.IOException;

public class UrlPreUtils {
    private final static String ENCODING="utf-8";

    private final static Logger LOGGER= LoggerFactory.getLogger(HttpClient.class);

    public static String post(String url, String json){
        StringBuffer requestText = new StringBuffer();
        CloseableHttpResponse response=null;

        CloseableHttpClient client=null;

        HttpPost httpPost=new HttpPost(url);

        StringEntity entity=null;

        try {
            entity = new StringEntity(json,"UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type","type/json;charset=ISO-8859-1");
            client= HttpClients.createDefault();
            response=client.execute(httpPost);
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            requestText.append(new String(bytes,"utf-8"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }finally {
            LOGGER.info("Request"+requestText.toString());
        }
        return requestText.toString();
    }

    public static String get(String url){
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText="";
        CloseableHttpResponse response=null;

        try {
            HttpGet get = new HttpGet(url);
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity!=null){
                responseText= EntityUtils.toString(entity, ENCODING);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
