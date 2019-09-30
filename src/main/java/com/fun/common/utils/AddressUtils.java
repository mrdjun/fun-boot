package com.fun.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fun.framework.config.FunBootConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过 IP 查询地址
 *
 * @author DJun
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (FunBootConfig.isAddressEnabled()) {

            String rspStr;
            try {
                // 一般不会抛异常，只有本地调试 断网时，则会因为网络异常问题抛异常
                rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip);
            } catch (Exception e) {
                throw e;
            }

            if (StringUtils.isEmpty(rspStr)) {
                log.error("获取IP为{}地理位置异常 ", ip);
                return address;
            }
            JSONObject obj = JSONObject.parseObject(rspStr);
            JSONObject data = obj.getObject("data", JSONObject.class);
            String region = data.getString("region");
            String city = data.getString("city");
            address = region + " " + city;
        }
        return address;
    }
}
