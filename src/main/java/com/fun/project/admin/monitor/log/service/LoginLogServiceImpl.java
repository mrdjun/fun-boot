package com.fun.project.admin.monitor.log.service;

import com.fun.common.utils.text.Convert;
import com.fun.project.admin.monitor.log.entity.LoginLog;
import com.fun.project.admin.monitor.log.mapper.LoginLogMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by DJun on 2019/9/13 12:42
 * desc:
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper ;

    @Override
    public void insertLoginLog(LoginLog loginLog) {
        loginLogMapper.insertLoginLog(loginLog);
    }

    @Override
    public List<LoginLog> selectLoginLogList(LoginLog loginLog,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return loginLogMapper.selectLoginLogList(loginLog);
    }

    @Override
    public int deleteLoginLogByIds(String ids) {
        return loginLogMapper.deleteLoginLogByIds(Convert.toStrArray(ids));
    }

    @Override
    public int cleanLoginLog() {
        return loginLogMapper.cleanLoginLog();
    }
}
