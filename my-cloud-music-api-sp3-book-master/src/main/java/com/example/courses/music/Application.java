package com.example.courses.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动类
 */
//这个注解
//他是多个注解合集
//作用是代替xml配置
//idea：查看文档注释快捷键f1（mac），路径为：View | Quick Documentation Lookup
//自动显示文档注释：settings-editor-general，勾选show quick documention ...
@SpringBootApplication

//开启定时任务，这里主要用来自动关闭订单
@EnableScheduling
@MapperScan("com.example.courses.music.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
