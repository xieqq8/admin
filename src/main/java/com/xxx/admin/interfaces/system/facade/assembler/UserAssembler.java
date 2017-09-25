package com.xxx.admin.interfaces.system.facade.assembler;

import com.xxx.admin.domain.modle.User;
import com.xxx.admin.domain.modle.UserMb;
import com.xxx.admin.infrastructure.BeanUtil;
import com.xxx.admin.interfaces.system.facade.commondobject.ProfileCommand;
import com.xxx.admin.interfaces.system.facade.commondobject.UserCommond;
import com.xxx.admin.interfaces.system.facade.dto.UserDto;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiexx
 * @date 2016/9/29
 */
public class UserAssembler {

    public static UserMb commondToDomain(UserCommond commond) {
        UserMb user=new UserMb();
        BeanUtil.copeProperties(commond,user);
        return user;
    }

    public static UserMb commondToDomain(String uid, UserCommond commond) {
        UserMb user = new UserMb();
        BeanUtil.copeProperties(commond, user);
        user.setId(uid);
        return user;
    }

    public static UserMb profileToDomain(String uid, ProfileCommand commond) {
        UserMb user = new UserMb();
        BeanUtil.copeProperties(commond, user);
        user.setId(uid);
        return user;
    }


    public static UserDto domainToDto(UserMb user){
        UserDto dto=new UserDto();
        BeanUtil.copeProperties(user,dto);
        return dto;
    }

    public static List<UserDto> domainToDto(List<UserMb> user){
       if(CollectionUtils.isEmpty(user)){
           return null;
       }
        List<UserDto> dtos=new ArrayList<>(user.size());
        user.stream().forEach(user1 -> {
            dtos.add(domainToDto(user1));
        });
        return dtos;
    }

}
