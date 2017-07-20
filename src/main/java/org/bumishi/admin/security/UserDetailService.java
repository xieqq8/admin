package org.bumishi.admin.security;

import org.bumishi.admin.domain.modle.Role;
import org.bumishi.admin.domain.modle.User;
import org.bumishi.admin.domain.repository.RoleRepository;
import org.bumishi.admin.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author qiang.xie
 * @date 2016/9/27
 */
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("no user");
        }
        SecurityUser userDetails = new SecurityUser(user.getId(), username, user.getPassword(), !user.isDisabled(), true, true, true, grantedAuthorities(user.getId()), user.getSalt(), user.getEmail());
        return userDetails;
    }

    protected Collection<GrantedAuthority> grantedAuthorities(String userId){
        List<Role> resources=roleRepository.getRoles(userId);
        if(CollectionUtils.isEmpty(resources)){
            return new ArrayList<>();
        }
        Collection<GrantedAuthority> authorities=new HashSet<>();
        //忽略已经禁用的角色
        resources.stream().filter(role -> !role.isDisabled()).forEach((resource -> {
            authorities.add(new SimpleGrantedAuthority(resource.getName()));
        }));
        return authorities;
    }

}
