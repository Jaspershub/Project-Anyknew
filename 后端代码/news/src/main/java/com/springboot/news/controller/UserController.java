package com.springboot.news.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.news.model.*;
import com.springboot.news.service.CollectionService;
import com.springboot.news.service.CommentService;
import com.springboot.news.service.ThumbupService;
import com.springboot.news.service.UserService;
import com.springboot.news.global.ExceptionMessage;
import com.springboot.news.util.HttpClientUtil;
import com.springboot.news.util.RandomUtil;
import com.springboot.news.util.RedisUtil;
import com.springboot.news.util.WXCahtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.*;



@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    CollectionService collectionService;
 @Autowired
    ThumbupService thumbupService;

    /**
     * 用户登录
     *
     * @param code
     * @param encryptedData
     * @param iv
     * @return
     */
    @RequestMapping("/login")
    public String Login(HttpServletRequest request, @NotBlank(message = ExceptionMessage.CODE_BALNK) @RequestParam(defaultValue = "") String code, @NotBlank(message = ExceptionMessage.EN_BALNK) @RequestParam(defaultValue = "") String encryptedData, @NotBlank(message = ExceptionMessage.IV_BALNK) @RequestParam(defaultValue = "") String iv) {

        Map<String, String> map = WXCahtUtil.getSessionKeyOropenid(code);
        JSONObject jsonObject = WXCahtUtil.getUserInfo(encryptedData, map.get("session_key"), iv);
        if (jsonObject != null) {
            String uid = userService.getUidByOpenId(map.get("openid"));
            User user = new User();
            Map<String, Object> userMap = new HashMap<String, Object>();
            user.setAge(19);
            user.setWname(jsonObject.getString("nickName").replaceAll("[\\x{10000}-\\x{10FFFF}]", ""));
            userMap.put("wname", jsonObject.getString("nickName"));
            user.setSex(jsonObject.getIntValue("gender"));
            userMap.put("sex", jsonObject.getIntValue("gender"));
            user.setCountry(jsonObject.getString("country"));
            userMap.put("country", jsonObject.getString("country"));
            user.setProvince(jsonObject.getString("province"));
            userMap.put("province", jsonObject.getString("province"));
            user.setCity(jsonObject.getString("city"));
            userMap.put("city", jsonObject.getString("city"));
            user.setHeadimg(jsonObject.getString("avatarUrl"));
            userMap.put("headimg", jsonObject.getString("avatarUrl"));
            user.setOpenid(map.get("openid"));
            String token = RandomUtil.generateString(26);
            RedisUtil.set(token, map.get("session_key") + map.get("openid"), 3600);
            userMap.put("token", token);
            if (uid != null && !"".equals(uid)) {
                user.setuId(Integer.valueOf(uid));
                userMap.put("uid", uid);
                if (userService.modifyUser(user) == 1) {
                    return JSONResult.ok(userMap);
                }

            } else {
                if (userService.saveUser(user) == 1) {
                    user.setuId(Integer.valueOf(userService.getUidByOpenId(map.get("openid"))));
                    return JSONResult.ok(userMap);
                }


            }
        }


        return null;
    }

    /**
     * 获取用户评论
     *
     * @param uid
     * @param page
     * @param num
     * @return
     */
    @RequestMapping("/myComment")
    public String getUesrComment(@Min(value = 1, message = ExceptionMessage.UID_MIN) @RequestParam int uid, @Min(value = 1, message = ExceptionMessage.PAGE_MIN) @RequestParam(defaultValue = "1") int page, @Min(value = 0, message = ExceptionMessage.NUM_MIN) @RequestParam(defaultValue = "10") int num) {

        List<Comment> list = commentService.getCommentByUid(String.valueOf(uid), page, num);
        List<Map<String, Object>> commentList = new LinkedList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Comment comment = list.get(i);
            Map<String, Object> commentMap = new HashMap<String, Object>();
            commentMap.put("cid", comment.getcId());
            commentMap.put("nid", comment.getcNid());
            commentMap.put("ntitle",getArticleTitle(comment.getcNid()));
            commentMap.put("content", comment.getContent());
            commentMap.put("wname",comment.getUser().getWname());
            commentMap.put("headImg", comment.getUser().getHeadimg());
            String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(comment.getcDate());
            commentMap.put("date", formatStr);
            if (comment.getToComment() != null) {
                commentMap.put("toUserName", comment.getToComment().getUser().getWname());
                commentMap.put("toContent", comment.getToComment().getContent());
                commentMap.put("toUserImg", comment.getToComment().getUser().getHeadimg());
               // System.out.println(comment.getToComment().getUser().getWname());
            }
            commentList.add(commentMap);
        }
        Map<String, Object> mainMap = new HashMap<String, Object>();
        mainMap.put("count", list.size());
        mainMap.put("commentList", commentList);

        return JSONResult.ok(mainMap);
    }

    /**
     * 用户评论
     *
     * @param from_uid
     * @param nid
     * @param content
     * @param to_cid
     * @return
     */
    @RequestMapping("/comment")
    public String comment(@Min(value = 1, message = ExceptionMessage.UID_MIN) @RequestParam int from_uid, @NotBlank(message = ExceptionMessage.NID_BLANK) @RequestParam String nid, @NotBlank(message = ExceptionMessage.CONTENT_BLANK) @RequestParam String content, @RequestParam(defaultValue = "0") int to_cid) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (userService.getUserByUid(from_uid) != null) {


            int check = commentService.saveComment(nid, from_uid, content, to_cid);

            if (check == 1) {
                map.put("result", 100);
                return JSONResult.ok(map);

            } else {
                map.put("result", 101);
                return JSONResult.ok(map);
            }
        } else {
            map.put("result", 101);

            return JSONResult.build(1001, "无此用户id信息", map);
        }

    }

    /**
     * 删除评论
     *
     * @param cid
     * @return
     */
    @RequestMapping("/deleteComment")
    public String deleteComment(@RequestParam int cid) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (commentService.getCommentByCid(cid) != null) {


            int check = commentService.deleteComment(cid);

            if (check == 1) {
                map.put("result", 100);
                return JSONResult.ok(map);

            } else {
                map.put("result", 101);
                return JSONResult.ok(map);
            }
        } else {
            map.put("result", 101);
            return JSONResult.build(1001, "删除失败，无此评论id", map);
        }


    }

    /**
     * 用户收藏
     * @param uid
     * @param nid
     * @param ntitle
     * @param col_check
     * @return
     */
    @RequestMapping("/collection")
    public String collecting(@Min(value = 1, message = ExceptionMessage.UID_MIN) @RequestParam int uid,@RequestParam String nid,@NotBlank(message = ExceptionMessage.NTITLE_BALNK) @RequestParam String ntitle, @RequestParam int col_check){
          int num=0;
        Map<String,Object> map=new HashMap<String,Object>();
        if(col_check==100){

            Collect collect=new Collect();
            collect.setColNid(nid);
            collect.setColNtitle(ntitle);
            collect.setColUid(uid);
            collect.setColDate(new Date());
            num=collectionService.saveCollection(collect);
            if(num==1){
                map.put("result",100);
                return JSONResult.ok(map);
            }else{
                map.put("result",102);
                return JSONResult.ok(map);
            }

        }else if(col_check==101){
           num= collectionService.deleteCollection(uid,nid);
            if(num==1){
                map.put("result",101);
                return JSONResult.ok(map);
            }
            else{
                map.put("result",102);
                return JSONResult.ok(map);
            }
        }
        else{
            map.put("result",102);
            return JSONResult.ok(map);
        }
    }

    /**
     * 获取用户收藏
     * @param uid
     * @param page
     * @param num
     * @return
     */
    @RequestMapping("/myCollection")
    public String getUserAllCollection(@Min(value = 1, message = ExceptionMessage.UID_MIN) @RequestParam int uid, @Min(value = 1,message = ExceptionMessage.PAGE_MIN) @RequestParam(defaultValue = "1") int page, @Min (value = 0,message = ExceptionMessage.NUM_MIN) @RequestParam(defaultValue = "10") int num){

        List<Collect> list =collectionService.getAllCollectionByUid(String.valueOf(uid),page,num);
        List<Map<String, Object>> collectionList = new LinkedList<Map<String, Object>>();
        Map<String,Object> mainMap=new HashMap<String,Object>();

        for (int i = 0; i < list.size(); i++) {
            Collect collect = list.get(i);
            if(collect!=null){

                Map<String,Object> map=new HashMap<String,Object>();
                map.put("nid",collect.getColNid());
                map.put("ntitle",collect.getColNtitle());
                String formatStr = new SimpleDateFormat("yyyy-MM-dd").format(collect.getColDate());
                map.put("date",formatStr);
                collectionList.add(map);
            }


        }
        mainMap.put("collectionList",collectionList);
        mainMap.put("collectionNum",list.size());

        return JSONResult.ok(mainMap);
    }

    /**
     * 用户点赞
     * @param uid
     * @param nid
     * @param thumbup_check
     * @return
     */
    @RequestMapping("/thumbup")
    public String thumbuping(@Min(value = 1, message = ExceptionMessage.UID_MIN) @RequestParam int uid,@RequestParam String nid, @RequestParam int thumbup_check){

        int num=0;
        Map<String,Object> map=new HashMap<String,Object>();
        if(thumbup_check==100){
            Thumbup thumbup=new Thumbup();
            thumbup.setThuNid(nid);
            thumbup.setThuUid(uid);

            num=thumbupService.saveThumbup(thumbup);
            if(num==1){
                map.put("result",100);
                return JSONResult.ok(map);
            }else{
                map.put("result",102);
                return JSONResult.ok(map);
            }

        }else if(thumbup_check==101){
            num= thumbupService.deleteThumbup(uid,nid);
            if(num==1){
                map.put("result",101);
                return JSONResult.ok(map);
            }else{
                map.put("result",102);
                return JSONResult.ok(map);
            }
        }else{
            map.put("result",102);
            return JSONResult.ok(map);
        }

    }

    /**
     * 通过nid获取易源接口文章标题
     * @param nid
     * @return
     */
    public  String getArticleTitle(String nid){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("yiyuanAPI");
        String url = resourceBundle.getString("url");
        Map<String, String> param = new HashMap<String, String>();
        param.put("showapi_appid", resourceBundle.getString("showapi_appid"));
        param.put("showapi_sign", resourceBundle.getString("showapi_sign"));
        param.put("id", nid);
        param.put("needAllList", "1");
        param.put("maxResult", "1");
        JSONObject jsonObject= HttpClientUtil.sendGet(url, param);
        if(jsonObject!=null){
            JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
            if(showapi_res_body!=null){
                JSONObject pagebean = showapi_res_body.getJSONObject("pagebean");
                if(pagebean!=null){
                    JSONArray contentlist = pagebean.getJSONArray("contentlist");
                    if(contentlist.size()!=0){
                        String title = contentlist.getJSONObject(0).getString("title");
                        return  title;
                    }else{
                        return  null;
                    }
                }else{
                    return null;
                }
            }else{
                return  null;
            }

           // System.out.println(title);

        }else{
            return  null;
        }



    }

}
