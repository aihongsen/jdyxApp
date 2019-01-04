package com.jdyx.app.util;

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
import org.json.JSONObject;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetUtil {
    public static void main(String[] args) throws IOException {
        String url="https://a1.easemob.com/1169180327177665/jingdianyixian/users";
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", "text1111111");
        map.put("password", "text1111111");
        map.put("nickname", "text1111111");
        String body = send(url, map,"utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body);

        System.out.println("-----------------------------------");

        //设置请求体
//        ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
//        parameters.add(new BasicNameValuePair("username","text1111111"));
//        parameters.add(new BasicNameValuePair("password","text1111111"));
//        parameters.add(new BasicNameValuePair("nickname","text1111111"));
//        JSONObject jsonParam = new JSONObject();
//        jsonParam.put("username","text15801332983");
//        jsonParam.put("password","text15801332983");
//        jsonParam.put("nickname","text15801332983");
//        StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");
//        entity.setContentEncoding("UTF-8");
//        entity.setContentType("application/json");
//        //添加内容
//        httpPost.setEntity(new UrlEncodedFormEntity(jsonParam));
//        //执行 并接受请求参数
//        CloseableHttpResponse response = httpClient.execute(httpPost);
//        String html = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
//        System.out.println(html);

//        //1.拿到httpClient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        //2.设置请求方式和请求信息
//        HttpGet httpGet= new HttpGet("http://www.qq.com");
//        //3.执行请求
//        CloseableHttpResponse response = httpClient.execute(httpGet);
//        //4.获取返回值
//        String html = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
//        //5.打印
//        System.out.println(html);
    }
    /*
     * @param url       资源地址
     * @param map   参数列表
     * @param encoding  编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String send(String url, Map<String,String> map,String encoding) throws IOException{
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/json");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
}
