package com.springboot.news.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
@RestController
@RequestMapping("/hotstory")
public class HotstoryController {
    @RequestMapping("/universal")
    public String getUniversal() throws Exception{

        return hotNewsContent("https://www.anyknew.com/api/v1/sites/universal");

    }
    @RequestMapping("/zhihu")
    public String getZhihu() throws Exception{

        return hotNewsContent("https://www.anyknew.com/api/v1/sites/zhihu");

    }
    @RequestMapping("/baidu")
    public String getBaidu() throws Exception{

        return hotNewsContent("https://www.anyknew.com/api/v1/sites/baidu");

    }
    @RequestMapping("/weibo")
    public String getWeibo() throws Exception{
        return hotNewsContent("https://www.anyknew.com/api/v1/sites/weibo");

    }
    @RequestMapping("/163")
    public String get163() throws Exception{

        return hotNewsContent("https://www.anyknew.com/api/v1/sites/163");

    }
    @RequestMapping("/wallstreetcn")
    public String getWallstreetcn() throws Exception{

        return hotNewsContent("https://www.anyknew.com/api/v1/sites/wallstreetcn");
    }
    @RequestMapping("/qdaily")
    public String getQdaily() throws Exception{


        return hotNewsContent("https://www.anyknew.com/api/v1/sites/qdaily");
    }

    /**
     * 360热点
     * @return
     * @throws Exception
     */
    @RequestMapping("/360")
    public String get360() throws Exception{

       return hotNewsContent("https://www.anyknew.com/api/v1/sites/360");
    }


    public String hotNewsContent(String urlStr){
        URL url = null;
        String line=null;
        try {
            url = new URL(urlStr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            line = reader.readLine();
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }
}
