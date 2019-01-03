package com.jdyx.app.usermanage;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@MapperScan("com.jdyx.app.usermanage.mapper")   //扫描所有mybatis的mapper接口文件
@SpringBootApplication
public class AppUserInfomanageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppUserInfomanageApplication.class, args);
//        log.info(new String("测试Slf4j在控制台打印"));
    }

}

