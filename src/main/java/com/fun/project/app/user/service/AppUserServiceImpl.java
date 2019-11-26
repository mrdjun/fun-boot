package com.fun.project.app.user.service;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.constant.Constants;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.TimestampUtil;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.app.user.entity.AppUser;
import com.fun.project.app.user.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static com.fun.common.utils.app.AppRandomUtils.getStr18;

/**
 * @author DJun
 * @date 2019/9/12 18:17
 */
@Service
public class AppUserServiceImpl implements IAppUserService {

    @Autowired
    private AppUserMapper appUserMapper;

    @Override
    public AppUser selectUserById(Long userId) {
        return appUserMapper.selectUserById(userId);
    }

    @Override
    public List<AppUser> selectUserList(AppUser appUser) {
        return appUserMapper.selectUserList(appUser);
    }

    @Override
    public int insertUser(AppUser appUser) {
        appUser.setOpenId(getStr18());
        appUser.setCreateTime(TimestampUtil.getCurrentTimestamp13());
        return appUserMapper.insertUser(appUser);
    }

    @Override
    public int updateAppUser(AppUser appUser) {
        AppUser oldInfo = appUserMapper.selectUserById(appUser.getUserId());
        // u号只允许修改一次
        if (Constants.NOT_UNIQUE.equals(oldInfo.getIsLock())) {
            appUser.setUAccount(oldInfo.getUAccount());
        }
        if (StringUtils.isEmpty(ShiroUtils.getLoginName())) {
            appUser.setUpdateBy(appUser.getLoginName());
        }
        appUser.setUpdateTime(TimestampUtil.getCurrentTimestamp13());
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
    public JSONObject login(String loginName, String password) {
        JSONObject jsonObject = new JSONObject();
        AppUser appUserInfo = appUserMapper.login(loginName, password);
        if (StringUtils.isNotNull(appUserInfo)) {
            appUserInfo.setPassword("it's a secret");
            appUserInfo.setSalt("it's a secret");
            jsonObject.put("user", appUserInfo);
            List<String> permsList = appUserMapper.selectUserPermsByUserId(appUserInfo.getUserId());
            String[] perms = stringListToArray(permsList);
            jsonObject.put("perms", perms);
        } else {
            jsonObject.put(Constants.EMPTY, Constants.EMPTY);
        }
        return jsonObject;
    }

    @Override
    public String checkLoginNameUnique(String loginName) {
        return String.valueOf(appUserMapper.checkLoginNameUnique(loginName));
    }

    @Override
    public String checkUAccountUnique(String uAccount) {
        return String.valueOf(appUserMapper.checkUAccountUnique(uAccount));
    }

    @Override
    public String checkPhoneUnique(String telephone) {
        return String.valueOf(appUserMapper.checkPhoneUnique(telephone));
    }

    @Override
    public String checkEmailUnique(String email) {
        return String.valueOf(appUserMapper.checkEmailUnique(email));
    }

    private String[] stringListToArray(List<String> stringList) {
        Iterator iterator = stringList.iterator();
        String[] str = new String[stringList.size()];
        int i = 0;
        while (iterator.hasNext()) {
            str[i] = (String) iterator.next();
            i++;
        }
        return str;
    }

}
