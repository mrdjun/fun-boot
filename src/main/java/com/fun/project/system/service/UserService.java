package com.fun.project.system.service;

import com.fun.project.system.entity.User;

import java.util.List;

/**
 * created by DJun on 2019/9/7 15:19
 * desc:
 */
public interface UserService {

    List<User> selectUserList(User user);

}
