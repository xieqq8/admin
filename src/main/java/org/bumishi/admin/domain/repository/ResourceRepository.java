package org.bumishi.admin.domain.repository;

import org.bumishi.admin.domain.modle.Resource;

import java.util.List;

/**
 * Created by xieqiang on 2016/9/17.
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
