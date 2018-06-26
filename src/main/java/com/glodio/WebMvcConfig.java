package com.glodio;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置spring mvc的一些 配置 ，这里只配置了静态资源的访问路径
 * @author Administrator
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer   {
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
		//根据需要配置静态文件
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");
        registry.addResourceHandler("/src/**").addResourceLocations("classpath:/src/");
        registry.addResourceHandler("/index.html").addResourceLocations("classpath:/index.html");
	}
	
	
	
	
}
