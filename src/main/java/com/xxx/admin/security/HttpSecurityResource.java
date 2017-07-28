package com.xxx.admin.security;

import com.xxx.admin.domain.modle.Resource;
import com.xxx.admin.domain.modle.Role;
import com.xxx.admin.domain.repository.ResourceRepository;
import com.xxx.admin.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * 定义受保护的http资源
 * 默认情况下，需要在配置文件中定义url与所需的权限，不能从数据库加载
 * @author xiexx
 * @date 2016/9/27
 */
@Component
public class HttpSecurityResource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected ResourceRepository resourceRepository;

    private AntPathMatcher pathMatcher=new AntPathMatcher();

    // decide 方法是判定是否拥有权限的决策方法，
    //authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
    //object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
    //configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    //访问某个资源object需要什么角色
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> attributes=new HashSet<>();
        FilterInvocation invocation=(FilterInvocation)object;//对于http资源来说，object是FilterInvocation
        List<Role> roles=roleRepository.list();
        if(CollectionUtils.isEmpty(roles)){
            return new HashSet<>();
        }
        String requestUrl=invocation.getRequestUrl();
        roles.stream().forEach(role -> {
            List<Resource> resources=resourceRepository.listByRole(role.getId());
            if(CollectionUtils.isEmpty(resources)){
                return;
            }
            resources.stream().filter(resource -> !resource.isDisabled()).forEach(resource -> {
                if(pathMatcher.match(resource.getUrl(),requestUrl)) {
                    attributes.add(new SecurityConfig(role.getName()));
                    return;
                }
            });

        });
        // 这个 = 0 时会访问不了
        System.out.println("--------------getAttributes size is " + attributes.size());
        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<Role> allHttpResources=roleRepository.list();
        Collection<ConfigAttribute> attributes=new HashSet<>();
        allHttpResources.stream().forEach(role -> {
            attributes.add(new SecurityConfig(role.getName()));
        });
        return attributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
