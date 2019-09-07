package com.fun.project.system.service;

import com.fun.project.system.entity.User;
import com.fun.project.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by DJun on 2019/9/7 16:10
 * desc:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }
}
