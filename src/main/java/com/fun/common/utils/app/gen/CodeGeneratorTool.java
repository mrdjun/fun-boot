package com.fun.common.utils.app.gen;


import com.fun.project.app.tool.entity.AppGenTable;

import java.io.IOException;

/**
 * 将表处理为 AppGenTable
 *
 * @author DJun
 */
public class CodeGeneratorTool {

    /**
     * process Table Into GenTable
     *
     * @param tableSql table sql
     * @return GenTable
     * @throws IOException exception
     */
    public static AppGenTable processTableIntoClassInfo(String tableSql) throws IOException {
        return TableParseUtil.processTableIntoClassInfo(tableSql);
    }

}
