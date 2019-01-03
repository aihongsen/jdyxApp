package com.jdyx.app.usermanage;

import com.jdyx.app.usermanage.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppUsermanageApplicationTests {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Test
    public void contextLoads() {
//        userInfoMapper.selectList(null);
//        for (UserInfo o : userInfoMapper.selectList(null)) {
//            System.out.println(o.toString());
//        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("15801332983");
        userInfo.setName("小寒");
        UserInfo user = userInfoMapper.getUserByPhone(userInfo);
        System.out.println(userInfo);
    }

}

