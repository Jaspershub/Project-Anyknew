package com.springboot.news.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

public class WXCahtUtil {
    /**
     * 获取用户Session_Key和OpenId
     *
     * @param code
     * @return
     */
    public static Map<String, String> getSessionKeyOropenid(String code) {

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
                if (jsonObject != null) {
                    map.put("session_key", jsonObject.get("session_key").toString());
                    map.put("openid", jsonObject.get("openid").toString());
                }


            }
            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 解密获取用户信息
     *
     * @param encryptedData
     * @param sessionkey
     * @param iv
     * @return
     */
    public static JSONObject getUserInfo(String encryptedData, String sessionkey, String iv) {
        // 被加密的数据
        byte[] dataByte = new byte[0];
        // 加密秘钥
        byte[] keyByte = new byte[0];
        // 偏移量
        byte[] ivByte = new byte[0];
        try {
            dataByte = Base64Util.decodeString(encryptedData);
            keyByte = Base64Util.decodeString(sessionkey);
            ivByte = Base64Util.decodeString(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }


}
