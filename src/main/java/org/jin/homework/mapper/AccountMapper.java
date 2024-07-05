package org.jin.homework.mapper;

import org.apache.ibatis.annotations.*;
import org.jin.homework.entity.Account;

import java.util.List;

@Mapper
public interface AccountMapper {
    @Select("SELECT * FROM users WHERE userId = #{userId}")
    Account getUserById(Long userId);

    @Select("SELECT * FROM users")
    List<Account> getAllUsers();

    @Insert("INSERT INTO users(userId, accountName, role) VALUES(#{userId}, #{accountName}, #{role})")
    void insertUser(Account user);

    @Update("UPDATE users SET accountName = #{accountName}, role = #{role} WHERE userId = #{userId}")
    void updateUser(Account user);

    @Delete("DELETE FROM users WHERE userId = #{userId}")
    void deleteUser(Long userId);
}
