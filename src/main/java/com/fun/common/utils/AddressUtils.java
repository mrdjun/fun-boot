package com.fun.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fun.framework.config.FunBootConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 获取地址类
 *
 * @author fun
 */
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (FunBootConfig.isAddressEnabled()) {
            DbSearcher searcher = null;
            try {
                String dbPath = AddressUtils.class.getResource("/ip2region/ip2region.db").getPath();
                File file = new File(dbPath);
                if (!file.exists()) {
                    String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
                    dbPath = tmpDir + File.separator + "ip.db";
                    file = new File(dbPath);
                    InputStream resourceAsStream = AddressUtils.class.getClassLoader().getResourceAsStream("classpath:ip2region/ip2region.db");
                    if (resourceAsStream != null) {
                        FileUtils.copyInputStreamToFile(resourceAsStream, file);
                    }
                }
                DbConfig config = new DbConfig();
                searcher = new DbSearcher(config, file.getPath());
                Method method = searcher.getClass().getMethod("btreeSearch", String.class);
                if (!Util.isIpAddress(ip)) {
                    log.error("Error: Invalid ip address");
                }
                DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
                return dataBlock.getRegion();
            } catch (Exception e) {
                log.error("获取地址信息异常，{}", e.getMessage());
                return UNKNOWN;
            } finally {
                if (searcher != null) {
                    try {
                        searcher.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return UNKNOWN;
    }

    public static void main(String[] args) {
        System.out.println(getRealAddressByIP("116.62.152.80"));
    }
}
