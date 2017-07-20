package org.bumishi.admin.config;

import org.bumishi.toolbox.qiniu.QiNiuApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiang.xie
 * @date 2016/12/9
 */
@Configuration
public class ApplicationConfig {

    @Value("${spring.application.qiniu.access-key}")
    private String qiniu_access_key;

    @Value("${spring.application.qiniu.securt-key}")
    private String qiniu_securt_key;

    @Value("${spring.application.qiniu.bucket}")
    private String qiniu_bucket;

    @Value("${spring.application.qiniu.domain}")
    private String qiniu_domain;//图片地址的访问域名

    @Bean
    public QiNiuApi qiNiuApi() {
        return new QiNiuApi(qiniu_access_key, qiniu_securt_key);
    }

    public String getQiniu_bucket() {
        return qiniu_bucket;
    }

    public String getQiniu_domain() {
        return qiniu_domain;
    }
}
