package com.fun.project.app.user.service;

import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.mapper.AppUserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by DJun on 2019/9/12 18:17
 * desc:
 */
@Service
public class AppUserServiceImpl implements IAppUserService {

    @Autowired
    private AppUserMapper appUserMapper ;

    @Override
    public AppUser selectUserById(Long userId) {
        return appUserMapper.selectUserById(userId);
    }

    @Override
    public List<AppUser> selectUserList(AppUser appUser,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return appUserMapper.selectUserList(appUser);
    }

    @Override
    public int insertUser(AppUser appUser) {
        return appUserMapper.insertUser(appUser);
    }

    @Override
    public int updateAppUser(AppUser appUser) {
        return appUserMapper.updateAppUser(appUser);
    }

    @Override
    public int updateAppUserByLoginName(AppUser appUser) {
        return appUserMapper.updateAppUserByLoginName(appUser);
    }

    @Override
    public int deleteUserByIds(String ids) {
        return appUserMapper.deleteUserByIds(ids);
    }

    @Override
    public int deleteUserById(Long userId) {
        return appUserMapper.deleteUserById(userId);
    }

    @Override
    public AppUser login(String loginName, String password) {
        return appUserMapper.login(loginName,password);
    }
}
