package org.bumishi.admin.domain.repository;

import org.bumishi.admin.domain.modle.SysLog;

import java.util.List;

/**
 * Created by xieqiang on 2016/9/17.
 */
public interface SysLogRepository {

    void add(SysLog sysLog);

    List<SysLog> list();

    void clear();
}
