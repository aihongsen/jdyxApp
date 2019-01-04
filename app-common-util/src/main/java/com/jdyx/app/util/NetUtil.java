package com.jdyx.app.util;

import com.alibaba.dubbo.common.json.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdyx.app.vo.HunXinEntities;
import com.jdyx.app.vo.HunXinRoot;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetUtil {
    public static void main(String[] args) throws IOException {
        String url="https://a1.easemob.com/1169180327177665/jingdianyixian/users";
        //测试公司的API接口，将json当做一个字符串传入httppost的请求体
        String result = null;
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        URI uri = null;
        try {
            HttpPost post = new HttpPost(url);
            //设置请求头
            post.setHeader("Content-Type", "application/json");
            String body = "{\"username\": \"text2222222\",\"password\": \"text2222222\",\"nickname\": \"text2222222\"}";
            //设置请求体
            post.setEntity(new StringEntity(body));
            //获取返回信息
            String str = client.execute(post).toString();
            System.out.println("======================================= " + str);
            HunXinRoot root = null;
            List list = null;
            ObjectMapper objectMapper=new ObjectMapper();
            HunXinRoot roots=objectMapper.readValue(str,HunXinRoot.class);
            list=roots.getEntities();
            System.out.println("====================");
            System.out.println("roots = " + roots.toString());
            System.out.println("====================");
        } catch (Exception e) {
            System.out.println("接口请求失败"+e.getStackTrace());
        }
//        System.out.println(result);

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
