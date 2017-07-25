package com.xxx.admin.domain.repository;

import com.xxx.admin.domain.modle.Resource;

import java.util.List;

/**
 * Created by xiexx on 2016/9/17.
 */
public interface ResourceRepository {

    void add(Resource resource);

    void update(Resource resource);

    Resource get(String code);

    List<Resource> list();

    void remove(String code);

    void switchStatus(String code, boolean disabled);

    List<Resource> listByRole(String roleId);

    List<Resource> getEnableResources();



}
