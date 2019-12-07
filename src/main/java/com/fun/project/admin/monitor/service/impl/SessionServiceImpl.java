package com.fun.project.admin.monitor.service.impl;

import com.fun.common.utils.AddressUtils;
import com.fun.common.utils.DateUtils;
import com.fun.common.utils.text.Convert;
import com.fun.framework.shiro.helper.ShiroUtils;
import com.fun.project.admin.monitor.entity.OnlineUser;
import com.fun.project.admin.monitor.service.ISessionService;
import com.fun.project.admin.system.entity.user.AdminUser;
import com.fun.project.admin.system.mapper.DeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author DJun
 * @date 2019/11/4
 */
@Service
@Slf4j
public class SessionServiceImpl implements ISessionService {
    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private DeptMapper deptMapper;
    @Value("${server.port}")
    private String serverPort;

    @Override
    public List<OnlineUser> list(String loginName) {
        String currentSessionId = ShiroUtils.getSessionId();
        List<OnlineUser> list = new ArrayList<>();
        InetAddress inetAddress;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            OnlineUser onlineUser = new OnlineUser();
            AdminUser user;
            SimplePrincipalCollection principalCollection;
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                user = (AdminUser) principalCollection.getPrimaryPrincipal();
                onlineUser.setUsername(user.getUsername());
                onlineUser.setUserId(user.getUserId().toString());
            }
            
            try {
                inetAddress = InetAddress.getLocalHost();
                String systemHost = String.format("%s:%s", inetAddress.getHostAddress(), serverPort);
                onlineUser.setSystemHost(systemHost);
            } catch (UnknownHostException e) {
                log.error(e.getMessage());
            }

            String deptName = deptMapper.selectDeptNameByAdminUserId(user.getUserId());
            if (StringUtils.isNotEmpty(deptName)) {
                onlineUser.setDeptName(deptName);
            }
            onlineUser.setLoginName(ShiroUtils.getLoginName());
            onlineUser.setId((String) session.getId());
            onlineUser.setHost(session.getHost());
            onlineUser.setStartTimestamp(DateUtils.getDateFormat(session.getStartTimestamp(),
                    DateUtils.YYYY_MM_DD_HH_MM_SS));
            onlineUser.setLastAccessTime(DateUtils.getDateFormat(session.getLastAccessTime(),
                    DateUtils.YYYY_MM_DD_HH_MM_SS));
            long timeout = session.getTimeout();
            onlineUser.setStatus(timeout == 0L ? "0" : "1");
            String address = AddressUtils.getRealAddressByIP(onlineUser.getHost());
            onlineUser.setLocation(address);
            onlineUser.setTimeout(timeout);
            if (StringUtils.equals(currentSessionId, onlineUser.getId())) {
                onlineUser.setCurrent(true);
            }
            if (StringUtils.isBlank(loginName)
                    || StringUtils.equalsIgnoreCase(onlineUser.getUsername(), loginName)) {
                list.add(onlineUser);
            }
        }
        return list;
    }

    @Override
    public void forceLogout(String sessionIds) {
        String[] ids = Convert.toStrArray(sessionIds);
        for (String id : ids) {
            Session session = sessionDAO.readSession(id);
            session.setTimeout(0);
            session.stop();
            sessionDAO.delete(session);
        }
    }
}
