package com.springboot.news.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.springboot.news.model.*;
import com.springboot.news.service.CollectionService;
import com.springboot.news.service.CommentService;
import com.springboot.news.service.ReadingService;
import com.springboot.news.global.ExceptionMessage;
import com.springboot.news.service.ThumbupService;
import com.springboot.news.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@Validated
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ReadingService readingService;
    @Autowired
    CommentService commentService;
    @Autowired
    ThumbupService thumbupService;
    @Autowired
    CollectionService collectionService;

    @RequestMapping("/readNum")
    /**
     *获取文章阅读量
     * @param nid,ntitle,channelid
     * @return
     */
    public String getArticleReading(@NotBlank(message = ExceptionMessage.NID_BLANK) @RequestParam(defaultValue = "") String nid, @NotBlank(message = ExceptionMessage.NTITLE_BALNK) @RequestParam(defaultValue = "") String ntitle, @NotBlank(message = ExceptionMessage.CHANNNELID_BLANK) @RequestParam(defaultValue = "") String channelid) {


        Map<String, String> readNumMap = new HashMap<String, String>();
        readNumMap.put("readNum", getReading(nid, ntitle, channelid));
        return JSONResult.ok(readNumMap);


    }

    /**
     * 获取文章评论
     *
     * @param nid
     * @param page
     * @param num
     * @return
     */
    @RequestMapping("/comment")
    public String getArticleComment(@RequestParam String nid, @Min(value = 1, message = ExceptionMessage.PAGE_MIN) @RequestParam(defaultValue = "1") int page, @Min(value = 0, message = ExceptionMessage.NUM_MIN) @RequestParam(defaultValue = "10") int num) {


        List<Comment> list = commentService.getCommentByNid(String.valueOf(nid), page, num);
        List<Map<String, Object>> commentList = new LinkedList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Comment comment = list.get(i);
            Map<String, Object> commentMap = new HashMap<String, Object>();
            commentMap.put("cid", comment.getcId());
            commentMap.put("nid", comment.getcNid());
            if (comment.getUser() != null) {
                commentMap.put("wname", comment.getUser().getWname());
                commentMap.put("headimg", comment.getUser().getHeadimg());
                commentMap.put("content", comment.getContent());
            }


            String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(comment.getcDate());
            System.out.println("日期：" + formatStr);
            commentMap.put("date", formatStr);
            if (comment.getToComment() != null) {
                commentMap.put("toUserName", comment.getToComment().getUser().getWname());
                commentMap.put("toContent", comment.getToComment().getContent());
                commentMap.put("toUserImg", comment.getToComment().getUser().getHeadimg());
            }
            commentList.add(commentMap);
        }
        Map<String, Object> mainMap = new HashMap<String, Object>();
        mainMap.put("count", list.size());
        mainMap.put("commentList", commentList);

        return JSONResult.ok(mainMap);

    }

    /**
     * 获取新闻详情
     * @param nid
     * @return
     */
    @RequestMapping("/detail")
    public String getArticleDetail(@NotBlank(message = ExceptionMessage.NID_BLANK) @RequestParam(defaultValue = "") String nid, @Min(value = 0, message = ExceptionMessage.UID_MIN) @RequestParam(defaultValue ="0") int uid) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("yiyuanAPI");
        String url = resourceBundle.getString("url");
        Map<String, String> param = new HashMap<String, String>();
        param.put("showapi_appid", resourceBundle.getString("showapi_appid"));
        param.put("showapi_sign", resourceBundle.getString("showapi_sign"));
        param.put("id", nid);
        param.put("needAllList", "1");
        param.put("maxResult", "1");
        Map<String, Object> map = new HashMap<String, Object>();
       // String readNum = getReading(nid, ntitle, channelid);
       // map.put("readNum", readNum);
JSONObject jsonObject=HttpClientUtil.sendGet(url, param);
if(jsonObject!=null){
    JSONArray allList = getAllListFromYiyuan(jsonObject);
    map.put("content", allList);

}else{
    map.put("content", null);
}

        if(uid==0){
            map.put("thumbup",100);
            map.put("collection",100);
        }else{
            map.put("thumbup",getThumbup(uid,nid));
            map.put("collection",getCollection(uid,nid));
        }


        return JSONResult.ok(map);
    }

    /**
     * 获取易源接口的allList
     * @param jsonObject
     * @return
     */

    public JSONArray getAllListFromYiyuan(JSONObject jsonObject){
        JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
        JSONObject pagebean = showapi_res_body.getJSONObject("pagebean");
        JSONArray contentlist = pagebean.getJSONArray("contentlist");
        JSONArray allList = contentlist.getJSONObject(0).getJSONArray("allList");
        return allList;
    }

    /**
     * 文章内容转换为带小程序样式的文本
     *
     * @param jsonObject
     * @return
     */
    public String articleContentSwitch(JSONObject jsonObject, String readNum) {
        String endView = "</div>";
        String startView = "<div>";
        String mainView = "<div style='padding:0 6px 0 6px;margin-bottom:20px;'>";
        String titleView = "<div style='font-size: 24px;font-weight: 700;margin: 12px 0 16px 0;'>";
        String infoView = "<div style='font-size: 14px;margin: 0px 0 16px 0;'><img style='width: 12px;height: 12px; margin-right: 8px;' src='http://static.ws.126.net/cnews/css13/img/end_news.png'/>";
        String detailView = "<div style='font-size: 18px;'>";
        String startImg = "<div style='margin: 12px 0 12px 0;'><img style='width: 100%;'   src='";
        String endImg = "'/></div>";

        if (jsonObject != null) {
            StringBuffer stringBuffer = new StringBuffer();
            JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
            JSONObject pagebean = showapi_res_body.getJSONObject("pagebean");
            JSONArray contentlist = pagebean.getJSONArray("contentlist");
            String title = contentlist.getJSONObject(0).getString("title");
            String pubDate = null;
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(contentlist.getJSONObject(0).getString("pubDate"));
                pubDate = new SimpleDateFormat("MM/dd HH:mm").format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String channelName = contentlist.getJSONObject(0).getString("channelName");
            stringBuffer.append(mainView).append(titleView).append(title).append(endView).append(infoView).append(channelName).append("\t|\t").append(pubDate).append("\t|\t").append(readNum).append("℃").append(endView);
            stringBuffer.append(detailView);

            JSONArray allList = contentlist.getJSONObject(0).getJSONArray("allList");
            for (int i = 0; i < allList.size(); i++) {
                String str = allList.get(i).toString();
                if (str.indexOf("{") == 0 && str.lastIndexOf("}") == str.length() - 1) {
                    boolean check = false;
                    for (int k = i; k < allList.size(); k++) {
                        String str1 = allList.get(k).toString();
                        if (str1.indexOf("{") != 0 && str1.lastIndexOf("}") != str1.length() - 1) {
                            check = true;
                            break;
                        }
                    }
                    if (check) {
                        JSONObject jsonObject1 = (JSONObject) allList.get(i);
                        if (jsonObject1.containsKey("url")) {
                            String url = ((JSONObject) allList.get(i)).getString("url");
                            stringBuffer.append(startImg).append(url).append(endImg);

                        }
                    }

                } else {
                    stringBuffer.append(startView).append(str).append(endView);
                }


            }
            stringBuffer.append(endView).append(endView);
            return stringBuffer.toString();
        }
        return null;

    }

    /**
     * 获取新闻点赞状态
     * @param uid
     * @param nid
     * @return
     */
    @RequestMapping("/thumbup")
    public String getArticleThumbup(@Min(value = 1, message = ExceptionMessage.UID_MIN) @RequestParam int uid,@NotBlank(message = ExceptionMessage.NID_BLANK) @RequestParam(defaultValue = "") String nid){
        Map<String,Integer> map=new HashMap<>();
        map.put("thumbup",getThumbup(uid,nid));
        return JSONResult.ok(map);
    }

    public int getThumbup(int uid,String nid){
        Thumbup thumbup= thumbupService.getThumbupByUidAndNid(uid,nid);
        if(thumbup==null){
            return 100;
        }else{
            return  101;
        }
    }

    public int getCollection(int uid,String nid){
        Collect collect=collectionService.selectCollectionByUidAndNid(uid,nid);
        if(collect==null){
            return 100;
        }else{
            return  101;
        }
    }

    public String getReading(String nid, String ntitle, String channelid) {
        String readNum = readingService.getReadingByNid(nid);
        if (readNum == null) {
            Reading reading = new Reading();
            reading.setNum(0);
            reading.setrNid(nid);
            reading.setrNtitle(ntitle);
            reading.setCharnnelid(channelid);
            readingService.saveArticleToReading(reading);
            return "0";

        } else {
            readingService.modifyArticleReading(nid, ntitle, channelid);
            return readNum;

        }


    }


}
