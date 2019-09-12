package com.fun.project.app.user.service;

import com.fun.project.app.user.entity.User;
import com.fun.project.app.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by DJun on 2019/9/12 18:17
 * desc:
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper ;

    @Override
    public User selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUserByIds(String ids) {
        return userMapper.deleteUserByIds(ids);
    }

    @Override
    public int deleteUserById(Long userId) {
        return userMapper.deleteUserById(userId);
    }

    @Override
    public User login(String loginName, String password) {
        return userMapper.login(loginName,password);
    }
}
