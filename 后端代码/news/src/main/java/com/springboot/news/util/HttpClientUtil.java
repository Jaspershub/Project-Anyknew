package com.springboot.news.util;

import com.alibaba.fastjson.JSONObject;

//import com.sun.javafx.fxml.builder.URLBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Map;


public class HttpClientUtil {

    /**
     * GET请求方式
     * @param url
     * @param param
     * @return
     */
    public static JSONObject  sendGet(String url, Map<String, String> param) {

        Map<String, String> map = null;
        JSONObject jsonObject=null;
        try {

            URIBuilder uriBuilder = new URIBuilder(url);
            if (param != null) {
                for (String str : param.keySet()) {
                    uriBuilder.addParameter(str, param.get(str));
                }
            }
            URI uri = uriBuilder.build();

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(uri);

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null) {
                jsonObject = (JSONObject) FastJSONUtil.textToJson(EntityUtils.toString(httpEntity, "utf-8"));


            }
            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

   /* public static Map<String, String> sendPost(String code) {

        ResourceBundle resource = ResourceBundle.getBundle("wxConfig");
        String url = resource.getString("url") + "?appid=" + resource.getString("wxspAppid") + "&secret=" + resource.getString("wxspSecret") + "&js_code=" + code + "&grant_type=" + resource.getString("grant_type");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        Map<String, String> map = new HashMap<String, String>();
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null) {
                JSONObject jsonObject = (JSONObject) FastJSONUtil.textToJson(EntityUtils.toString(httpEntity, "utf-8"));

                map.put("session_key", jsonObject.get("session_key").toString());
                map.put("openid", jsonObject.get("openid").toString());

            }
            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }*/

}
