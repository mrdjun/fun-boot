package com.fun.project.system.mapper;

import com.fun.project.system.entity.User;

import java.util.List;

/**
 * created by DJun on 2019/9/7 15:19
 * desc:
 */
public interface UserMapper {

    List<User> selectUserList(User user);

}
