//package com.xxx.admin.domain.mapper;
//
//import com.xxx.admin.domain.modle.SysLog;
//import org.apache.ibatis.jdbc.SQL;
//
//public class SysLogSqlProvider {
//
//    public String insertSelective(SysLog record) {
//        SQL sql = new SQL();
//        sql.INSERT_INTO("syslog");
//
//        if (record.getUid() != null) {
//            sql.VALUES("uid", "#{uid,jdbcType=VARCHAR}");
//        }
//
//        if (record.getContent() != null) {
//            sql.VALUES("content", "#{content,jdbcType=VARCHAR}");
//        }
//
//        if (record.getOperation() != null) {
//            sql.VALUES("operation", "#{operation,jdbcType=VARCHAR}");
//        }
//
//        if (record.getCreatetime() != null) {
//            sql.VALUES("createTime", "#{createtime,jdbcType=TIMESTAMP}");
//        }
//
//        if (record.getUser() != null) {
//            sql.VALUES("user", "#{user,jdbcType=VARCHAR}");
//        }
//
//        return sql.toString();
//    }
//}