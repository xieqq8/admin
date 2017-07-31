package com.xxx.admin.security;

import com.xxx.admin.domain.modle.Resource;
import com.xxx.admin.domain.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * spring security默认的url授权方式需要预先硬编码在配置中，这里改写默认方式
 * @author xiexx
 * @date 2016/9/23
 */

public class UrlSecurityInterceptor extends FilterSecurityInterceptor {

    @Autowired
    protected ResourceRepository resourceRepository;

    private AntPathMatcher pathMatcher=new AntPathMatcher();

//    PathMatcher matcher = new AntPathMatcher();
//    // 完全路径url方式路径匹配
//    String requestPath="/user/list.htm?username=aaa&departmentid=2&pageNumber=1&pageSize=20";//请求路径
//    String patternPath="/user/list.htm**";//路径匹配模式
//
//    // 不完整路径uri方式路径匹配
//    // String requestPath="/app/pub/login.do";//请求路径
//    // String patternPath="/**/login.do";//路径匹配模式
//    // 模糊路径方式匹配
//    // String requestPath="/app/pub/login.do";//请求路径
//    // String patternPath="/**/*.do";//路径匹配模式
//    // 包含模糊单字符路径匹配
//    //String requestPath = "/app/pub/login.do";// 请求路径
//    //String patternPath = "/**/lo?in.do";// 路径匹配模式
//    boolean result = matcher.match(patternPath, requestPath);
//    assertTrue(result);

//    1、主要鉴权方法是调用父类中accessDecisionManager的decide值，所以我们需要自己实现一个accessDecisionManager
//2、父类中存在抽象方法public abstract SecurityMetadataSource obtainSecurityMetadataSource();作用是获取URL及用户角色对应的关系。我们需要加入自己的实现。

    @Autowired
    @Override
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager newManager) {
        super.setAuthenticationManager(newManager);
    }

    /**
     * 安全拦截器所需的SecurityMetadataSource类型是FilterInvocationSecurityMetadataSource
     * @param newSource
     */
    @Autowired
    @Override
    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
        super.setSecurityMetadataSource(newSource);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        if (((HttpServletRequest) request).getServletPath().equals("/to-login")) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || authentication instanceof AnonymousAuthenticationToken){
            //没有认证的，直接就结束
            throw new AuthenticationCredentialsNotFoundException("please login");
        }

        String currentUser = authentication.getName();
        if ("root".equalsIgnoreCase(currentUser)) {//不处理root账户的授权
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }else{
            // 核心的InterceptorStatusToken token = super.beforeInvocation(fi);
            // 会调用我们定义的accessDecisionManager:decide(Object object)
            // 和securityMetadataSource:getAttributes(Object object)方法。

            InterceptorStatusToken token = super.beforeInvocation(fi);

            if (token != null ) {
                System.out.println("--------------token is not null 设置个权限的时候 getAttributes 返回个数:" + token);
            } else {
                System.out.println("--------------token null 表示用户的角色对这个路径没有");
            }
            //只保护在resource表中配置的资源               实时改的token会用null
            if(token==null && isSecurityUrl(((HttpServletRequest) request).getServletPath())){
                throw new AccessDeniedException("only root can accsess");
            }
            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }
    }

    /**
     * 判断URL , 非然没有权限，但如果是安全的UR，也可以访问
     *
     * 资源管理	/resource/**	资源管理所有权限	禁用
     *                     禁用 表示 这个不用判数权限
     * @param url
     * @return
     */
    private boolean isSecurityUrl(String url){
        List<Resource> resources=resourceRepository.getEnableResources();
//        System.out.println("--------------isSecurityUrl resources: " + resources.size());
        resources.stream().forEach(role -> {
            System.out.println("--------------isSecurityUrl resources url: " + role.getUrl());
        });
        System.out.println("--------------isSecurityUrl url: " + url);

        if(CollectionUtils.isEmpty(resources)){
            System.out.println("--------------isSecurityUrl: CollectionUtils resources isEmpty");
            return false;
        }

        // Java8中使用filter()过滤列表，使用collect将stream转化为list http://blog.csdn.net/huludan/article/details/54316387

        // 这里多写一个 ! 是为了判断 资源禁用， 不需要经过权限认证就可以访问  相当于没有把该资源加入权限认证，只要配了菜单就可以访问
        //
        boolean bret = !resources.stream()                             // convert list to stream
                .filter(item->pathMatcher.match(item.getUrl(),url))     // filter the item which equals to "url"
                .collect(Collectors.toList())                           // collect the output and convert streams to a list
                .isEmpty();
        System.out.println("--------------isSecurityUrl true throw out:" + bret);
        return bret;

        // 如何拦截所有以.do结尾的请求.     正确答案是/**/*.do  匹配规则: http://blog.csdn.net/haoyifen/article/details/52679576
    }

    @Override
    public void destroy() {

    }
}
