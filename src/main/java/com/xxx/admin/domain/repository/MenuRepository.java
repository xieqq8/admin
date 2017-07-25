package com.xxx.admin.domain.repository;

import com.xxx.admin.domain.modle.Menu;

import java.util.List;

/**
 * Created by xiexx on 2016/9/17.
 */
public interface MenuRepository {

    void add(Menu menu);

    void update(Menu menu);

    Menu get(String code);

    boolean contains(String code);

    List<Menu> list();

    void remove(String code);

    void switchStatus(String code, boolean disabled);

    List<Menu> roleMenus(String roleId);

    List<Menu> getNavMenus(String userId);
}
