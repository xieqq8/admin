package com.xxx.admin.domain.repository;

import com.xxx.admin.domain.modle.User;

import java.util.List;

/**
 * Created by xieqiang on 2016/9/17.
 */
public interface UserRepository {

    void add(User user);

    void update(User user);

    void updateRoles(String uid, List<String> rids);

    User get(String id);

    boolean contains(String name);

    List<User> list();

    boolean hasResourcePermission(String userId, String resourceCode);

    void remove(String id);

    void switchStatus(String id, boolean disabled);

    User findByUserName(String username);



}
