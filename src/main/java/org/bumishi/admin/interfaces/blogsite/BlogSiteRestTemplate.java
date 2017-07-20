package org.bumishi.admin.interfaces.blogsite;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import org.bumishi.admin.config.BlogConfig;
import org.bumishi.toolbox.model.PageModel;
import org.bumishi.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author qiang.xie
 * @date 2016/12/19
 */
@Component
public class BlogSiteRestTemplate extends RestTemplate {

    @Autowired
    protected BlogConfig blogConfig;

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_UTF8_VALUE));
        return super.postForObject(blogConfig.getBlogHost() + url, new HttpEntity(request, httpHeaders), responseType, uriVariables);
    }

    public RestResponse post(String url, Object request, Object... uriVariables) {
        return this.postForObject(url, request, RestResponse.class, uriVariables);
    }

    @Override
    public void delete(String url, Map<String, ?> urlVariables) throws RestClientException {
        super.delete(blogConfig.getBlogHost() + url, urlVariables);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        return super.getForObject(blogConfig.getBlogHost() + url, responseType, urlVariables);
    }

    public RestResponse postWithEmptyBody(String url, Object... uriVariables) {
        return this.post(url, null, uriVariables);
    }

    public RestResponse getForObject(String url, Object... uriVariables) {
        return getForObject(url, RestResponse.class, uriVariables);
    }

    public <T> RestResponse<PageModel> getForPageModelResponseObject(String url, Object... uriVariables) {
        String json = getForObject(url, String.class, uriVariables);
        return JSON.parseObject(json, new TypeReference<RestResponse<PageModel>>() {
        });
    }

    @PostConstruct
    protected void setDefaultHeader(){
       this.setInterceptors(Lists.newArrayList(new HttpHeaderInterceptor("X-Requested-With","admin")));
    }


}
