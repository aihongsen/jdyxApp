package com.jdyx.app.util;
import com.jdyx.app.vo.EntitiesVo;
import com.jdyx.app.vo.RootVo;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.*;


public class NetUtil {
    public static void main(String[] args) throws IOException {

    }

    /**
     *
     * @param map
     * @return
     * @throws IOException
     */
    public static Object registerHunXinAccount(Map<String,Object> map) throws IOException{
        String url="https://a1.easemob.com/1169180327177665/jingdianyixian/users";
        //测试公司的API接口，将json当做一个字符串传入httppost的请求体
        String result = null;
        HttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        try {
            HttpPost post = new HttpPost(url);
            //设置请求头
            post.setHeader("Content-Type", "application/json");
            String body = "{\"username\": \""+map.get("username") +"\",\"password\": \""+map.get("password")+"\",\"nickname\": \""+map.get("nickname")+"\"}";
            //设置请求体
            post.setEntity(new StringEntity(body));
            //获取返回信息
            String respStr = null;
            HttpEntity entity = client.execute(post).getEntity();
            if (entity != null){
                respStr = EntityUtils.toString(entity, "UTF-8");
            }
            JSONObject jsonObject = JSONObject.fromObject(respStr);;
            Map<String, Class> classMap = new HashMap<String, Class>();
            classMap.put("entities", EntitiesVo.class);
            RootVo hunXinRootVo = (RootVo)JSONObject.toBean(jsonObject, RootVo.class,classMap);
            List<EntitiesVo> entitiesVos = hunXinRootVo.getEntities();
            EntitiesVo hunXinEntitiesVo = entitiesVos.get(0);
            //释放资源
            EntityUtils.consume(entity);
            return hunXinEntitiesVo;

        } catch (Exception e) {
            System.out.println("接口请求失败,或该用户已存在"+e.getStackTrace());
        }
        return null;
    }
}
