package com.xxx.admin.config;

import com.alibaba.fastjson.JSON;
import com.xxx.admin.security.UserDetailService;
import com.xxx.toolbox.model.RestResponse;
import org.apache.commons.lang3.StringUtils;
import com.xxx.admin.security.UrlSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 * @author xiexx
 * @date 2016/9/22
 */

@Configuration
@EnableWebSecurity //注释掉可以既能享受到springboot的自动配置又能覆盖某些配置
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected UserDetailService userDetailService;


    @Autowired
    protected Md5PasswordEncoder md5PasswordEncoder;

    @Value("${remember.key}")
    private String key = "weixin:javajidi_com";


    @Bean
    public UrlSecurityInterceptor urlSecurityInterceptor() {
        return new UrlSecurityInterceptor(); //拦截器UrlSecurityInterceptor
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * spring security 学习记录  使用security3以及升级到security4的时候遇到的问题记录下来。
     * http://blog.csdn.net/levelmini/article/details/54924396
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 跨域 HTTP 请求  禁用后更安全
        http.cors().disable();

        http.headers().disable();

        http.jee().disable();

        http.x509().disable();

        http.servletApi().disable();

        http.anonymous().disable();

        http.requestCache().disable();

        // CSRF是用于防止跨域攻击的，在security3中是默认关闭的，但是在security4中默认开启。开启之后在登陆时候的form中要加一个input放csrf的token  用thymeleaf会自动加上，所以不用写
        // 这个是配置忽略csrf验证的列表
        http.csrf().ignoringAntMatchers("/upload/*");

        http.rememberMe().userDetailsService(userDetailService).key(key).useSecureCookie(false).alwaysRemember(true);

        http.addFilterAt(urlSecurityInterceptor(), FilterSecurityInterceptor.class);//处理自定义的权限

        //http.authorizeRequests()对应FilterSecurityInterceptor，不配置就不会加入FilterSecurityInterceptor
        http.formLogin().
                loginProcessingUrl("/login").       // 执行登录页
                loginPage("/to-login").             // 登录页
                defaultSuccessUrl("/").             // 登录成功页
                successHandler(new AuthenticationSuccessHandler());

//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
//        ;
//        Servlet中，"/"代表Web应用的跟目录。和物理路径的相对表示。例如："./" 代表当前目录,

//        前端用的是jsp吗，如果是在路径前加${pageContext.request.contextPath}，如${pageContext.request.contextPath}/setting/style.css

//        http://www.cnblogs.com/blogonfly/p/3958946.html?utm_source=tuicool  Spring读取配置文件，地址问题，绝对路径，相对路径
//        HttpServletRequest req = (HttpServletRequest) request
//        getContextPath();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("./to-login").
                logoutSuccessHandler(new LogoutSuccessHandler());

        http.exceptionHandling().
                authenticationEntryPoint(new MyAuthenticationEntryPoint()).     // 登录判断
                accessDeniedHandler(new MyAccessDeniedHandler());               // 无权访问


/**
 * http://blog.csdn.net/kangguowei/article/details/54342729 Spring Security——退出登录logout
 *  https://github.com/ChinaSilence/any-spring-security/blob/master/security-login-no-db/src/main/java/com/spring4all/config/WebSecurityConfig.java
 * 匹配 "/" 路径，不需要权限即可访问
 * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
 * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
 * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
 * 默认启用 CSRF
 */
//        http
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/user/**").hasRole("USER")
//                .and()
//                .formLogin().loginPage("/login").defaultSuccessUrl("/user")
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(md5PasswordEncoder);
        provider.setUserDetailsService(userDetailService);
        ReflectionSaltSource saltSource = new ReflectionSaltSource();
        saltSource.setUserPropertyToUse("getSalt");
        provider.setSaltSource(saltSource);
        auth.authenticationProvider(provider);

    }
//    Spring的决策管理器，其接口为AccessDecisionManager，抽象类为AbstractAccessDecisionManager
    @Bean
    protected AccessDecisionManager accessDecisionManager() {
        RoleVoter roleVoter = new RoleVoter();
        roleVoter.setRolePrefix("");
        List voters = new ArrayList<>();
        voters.add(roleVoter);
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);
        return accessDecisionManager;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/*.js", "/**/*.js.map", "/**/*.ts", "/**/*.css", "/**/*.css.map", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.fco", "/**/*.woff", "/**/*.woff2", "/**/*.font", "/**/*.svg", "/**/*.ttf", "/**/*.pdf","/*.ico", "/admin/api/**", "/404", "/401","/403", "/error");
    }

    //由于springboot默认会将所要的servlet,filter,listenr等标准servlet组件自动加入到servlet的过滤器链中，自定义的UrlSecurityInterceptor只希望加入security的过滤器链，中，所以这里配置不向servlet容器中注册
    @Bean
    public FilterRegistrationBean registration(UrlSecurityInterceptor filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    protected boolean isAjax(HttpServletRequest request) {
        return StringUtils.isNotBlank(request.getHeader("X-Requested-With"));
    }

    /**
     * 登陆成功后
     */
    private class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication)
                throws ServletException, IOException {
            // 清除之前的
            clearAuthenticationAttributes(request);
            if (!isAjax(request)) {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        }
    }

    /**
     * 注销成功
     */
    private class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest request,
                                    HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {

//            clearAuthenticationAttributes(request);

            if (!isAjax(request)) {
                super.onLogoutSuccess(request, response, authentication);
            }
        }
    }

    private class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) throws IOException {
            response.setCharacterEncoding("utf-8");
            if (isAjax(request)) {
                response.setContentType("application/json");
                response.getWriter().println(JSON.toJSONString(RestResponse.fail("请登录")));
            } else {
                response.sendRedirect("./to-login");
            }

        }
    }

    private class MyAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            response.setCharacterEncoding("utf-8");
            if (isAjax(request)) {
                response.setContentType("application/json");
                response.getWriter().println(JSON.toJSONString(RestResponse.fail("您无权访问")));
            } else {
                System.out.println("--------redirect 403");
                response.sendRedirect("./403");
            }

        }
    }


}
