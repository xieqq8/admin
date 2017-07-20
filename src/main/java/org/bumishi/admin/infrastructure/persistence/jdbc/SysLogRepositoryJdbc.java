package org.bumishi.admin.infrastructure.persistence.jdbc;

import org.bumishi.admin.domain.modle.SysLog;
import org.bumishi.admin.domain.repository.SysLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author qiang.xie
 * @date 2016/9/18
 */
@Repository
public class SysLogRepositoryJdbc implements SysLogRepository {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public void add(SysLog sysLog) {
        jdbcTemplate.update("INSERT syslog (uid,content,operation,createTime,user) VALUES (?,?,?,?,?)",sysLog.getUid(),sysLog.getContent(),sysLog.getOperation(),sysLog.getCreateTime(),sysLog.getUser());
    }

    @Override
    public List<SysLog> list() {
        return jdbcTemplate.query("select * from syslog", BeanPropertyRowMapper.newInstance(SysLog.class));
    }

    @Override
    public void clear() {
        jdbcTemplate.update("DELETE FROM syslog");
    }
}
