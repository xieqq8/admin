package com.xxx.admin.domain.repository;

import com.xxx.admin.domain.modle.Role;

import java.util.List;

/**
 * Created by xiexx on 2016/9/17.
 */
public interface RoleRepository {

    void add(Role role);

    void update(Role role);

    void updateMenus(String rid, List<String> mids);

    void updateResources(String rid, List<String> resources);

    boolean contains(String roleName);

    Role get(String id);

    List<Role> list();

    void remove(String id);

    void removeRoleMenuByMenuId(String menuId);

    void removeRoleResourceByResourceId(String resourceId);

    void switchStatus(String id, boolean disabled);

    List<Role> getRoles(String userId);


}
