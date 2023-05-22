package com.jinli.jinrui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ServletComponentScan
//开启事物注解的支持
@EnableTransactionManagement
//开启Spring Cache注解方式的缓存功能
@EnableCaching
public class JinruiTakeOutApplication {
	public static void main(String[] args) {
		SpringApplication.run(JinruiTakeOutApplication.class, args);
	}
}
