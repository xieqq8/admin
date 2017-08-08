package com.xxx.admin.domain.mapper;

import com.xxx.admin.domain.modle.SysLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysLogMapper {
    @Insert({
        "insert into syslog (uid, content, ",
        "operation, createTime, ",
        "user)",
        "values (#{uid,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, ",
        "#{operation,jdbcType=VARCHAR}, #{createtime,jdbcType=TIMESTAMP}, ",
        "#{user,jdbcType=VARCHAR})"
    })
    int add(SysLog record);
//    int insert(SysLog record);

//    @InsertProvider(type=SysLogSqlProvider.class, method="insertSelective")
//    int insertSelective(SysLog record);

//    // 添加
//    void add(com.xxx.admin.domain.modle.SysLog sysLog);

    // 查找     @Select("select * from syslog")
    @Select({"select * from syslog"})
    List<com.xxx.admin.domain.modle.SysLog> list();

    // 清空
//    @Delete("DELETE FROM syslog")
    void clear();
}