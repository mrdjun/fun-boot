package com.fun.project.admin.monitor.service.impl;

import com.fun.common.utils.text.Convert;
import com.fun.project.admin.monitor.entity.LoginLog;
import com.fun.project.admin.monitor.mapper.LoginLogMapper;
import com.fun.project.admin.monitor.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 *
 * @author DJun
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {
    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insertLoginLog(LoginLog loginLog) {
        loginLogMapper.insertLoginLog(loginLog);
    }

    @Override
    public List<LoginLog> selectLoginLogList(LoginLog loginLog) {
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
