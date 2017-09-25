package com.xxx.admin.domain.mapper;

import com.xxx.admin.domain.modle.UserMb;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface UserMbMapper {
    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int remove(String id);

    @Insert({
        "insert into user (id, username, ",
        "password, email, ",
        "salt, disabled, ",
        "createTime, lastTime)",
        "values (#{id,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{salt,jdbcType=VARCHAR}, #{disabled,jdbcType=SMALLINT}, ",
        "#{createtime,jdbcType=TIMESTAMP}, #{lasttime,jdbcType=TIMESTAMP})"
    })
    int add(UserMb record);

//    @InsertProvider(type=UserMbSqlProvider.class, method="insertSelective")
//    int insertSelective(UserMb record);

    @Select({
        "select",
        "id, username, password, email, salt, disabled, createTime, lastTime",
        "from user",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="salt", property="salt", jdbcType=JdbcType.VARCHAR),
        @Result(column="disabled", property="disabled", jdbcType=JdbcType.SMALLINT),
        @Result(column="createTime", property="createtime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="lastTime", property="lasttime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserMb get(String id);
//    UserMb selectByPrimaryKey(String id);

//    @UpdateProvider(type=UserMbSqlProvider.class, method="updateByPrimaryKeySelective")
//    int updateByPrimaryKeySelective(UserMb record);

    @Update({
        "update user",
        "set username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "salt = #{salt,jdbcType=VARCHAR},",
          "disabled = #{disabled,jdbcType=SMALLINT},",
          "createTime = #{createtime,jdbcType=TIMESTAMP},",
          "lastTime = #{lasttime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int update(UserMb record);
//    int updateByPrimaryKey(UserMb record);

//    void add(UserMb user);

//    void update(UserMb user);

    // Dao层的函数方法 这个有循环
    public void updateRoles(@Param("uid") String uid, @Param("rids")List<String> rids);

//    void updateRoles(String uid, List<String> rids);     // 这个有循环

    //    UserMb get(String id);
    @Select("select count(username) from user where username=#{name,jdbcType=VARCHAR}")
    boolean contains(String name);

    @Select("select * from user where username <> 'root'")
    List<UserMb> list();

    @Select({"select count(*) from user_role ur join role_resource rr on ur.role_id=rr.role_id where ur.uid=#{userId,jdbcType=VARCHAR}",
            " and rr.resource_id="})
    boolean hasResourcePermission(String userId, String resourceCode);

    @Update({
            "update user",
            "set disabled = #{disabled,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=VARCHAR}"
    })
    void switchStatus(String id, boolean disabled);

    // 这个在 UserMbMapper中实现
    UserMb findByUserName(String username);

}