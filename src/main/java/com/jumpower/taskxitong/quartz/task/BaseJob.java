package com.jumpower.taskxitong.quartz.task;


import com.ktamr.service.HaConfigService;
import com.ktamr.service.HaErrrecordService;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseJob {

    @Autowired
    protected HaConfigService haConfigService;


    @Autowired
    protected HaErrrecordService haErrrecordService;

    protected Object getSessionValue(HttpSession session){
        Object attribute =session.getAttribute("operatorCode");
        return attribute;
    }

    protected long getRemainder(long n, Integer uploadNumber){
        if(n>0){
            if(n % uploadNumber == 0){
                n = n / uploadNumber;
            }else{
                n = n / uploadNumber + 1;
            }
        }
        return n;
    }

    protected void getQueryAndSleep(String s,String v){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void updateConfigByV(String s,String v,String k){
        Map<String,Object> map = new HashMap<>();
        map.put("s",s);
        map.put("k",k);
        map.put("v",v);

    }

    protected String getClient(String sendUrl,String uploadData) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        String charset = "utf-8";
        try {
            HttpPost httpPost = new HttpPost(sendUrl);
            StringEntity entity = new StringEntity(uploadData,charset);
            result = getAcquireValue(httpPost,entity,httpclient,charset);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected String getClient(String sendUrl,String unitCode,String factoryCode,String uploadData) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        String charset = "GBK";
        try {
            HttpPost httpPost = new HttpPost(sendUrl);
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("unitCode",unitCode));
            list.add(new BasicNameValuePair("factoryCode",factoryCode));
            list.add(new BasicNameValuePair("uploadData",uploadData));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            result = getAcquireValue(httpPost,entity,httpclient,charset);
        }finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected String getClientHw(String url, String ticket, String xaAppId, String xaAppSecret,String xaApiSecret,String xaEnvironment,String uploadData) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        String charset = "GBK";
        try {
            url+="?ticket="+ticket+"&xaAppId="+xaAppId+"&xaAppSecret="+xaAppSecret+
                    "&xaApiSecret="+xaApiSecret+"&xaEnvironment="+xaEnvironment;
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(uploadData,charset);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                if(response != null){
                    HttpEntity resEntity = response.getEntity();
                    if(resEntity != null){
                        result = EntityUtils.toString(resEntity,charset);
                    }
                }
            } finally {
                response.close();
            }
        }finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected String getClientGet(String url,String logincode,String password,String clientid,
                                  String clientsecret,String xaAppId,String xaAppSecret,String xaApiSecret,
                                  String xaEnvironment) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        String charset = "GBK";
        try {
            url+="?logincode="+logincode+"&password="+password+"&clientid="+clientid+
                    "&clientsecret="+clientsecret+"&xaAppId="+xaAppId+
                    "&xaAppSecret="+xaApiSecret+"&xaApiSecret="+xaApiSecret+"&xaEnvironment="+xaEnvironment;
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                if(response != null){
                    HttpEntity resEntity = response.getEntity();
                    if(resEntity != null){
                        result = EntityUtils.toString(resEntity,charset);
                    }
                }
            } finally {
                response.close();
            }
        }finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected String getAcquireValue(HttpPost httpPost,Object obj,CloseableHttpClient httpclient,String charset) throws Exception{
        String result = "";
        if(obj instanceof UrlEncodedFormEntity){
            httpPost.setEntity((UrlEncodedFormEntity)obj);
        }else if(obj instanceof StringEntity){
            httpPost.setEntity((StringEntity)obj);
        }
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        } finally {
            response.close();
        }
        return result;
    }

    protected Map<String,Object> getJSONData(){
        Map<String,Object> map = new HashMap<>();

        return map;
    }
}
