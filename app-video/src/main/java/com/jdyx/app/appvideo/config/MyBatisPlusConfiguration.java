package com.jdyx.app.appvideo.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfiguration {

    @Bean//逻辑删除
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

}
