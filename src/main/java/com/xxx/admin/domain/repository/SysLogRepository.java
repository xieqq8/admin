package com.xxx.admin.domain.repository;

import com.xxx.admin.domain.modle.SysLog;

import java.util.List;

/**
 * Created by xieqiang on 2016/9/17.
 */
public interface SysLogRepository {

    void add(SysLog sysLog);

    List<SysLog> list();

    void clear();
}
