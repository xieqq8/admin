package com.xxx.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xiexx on 2016/12/11.
 */
@Configuration
public class BlogConfig {

    @Value("${blog.host}")
    private String blogHost;

    public String getBlogHost() {
        return blogHost;
    }
}
