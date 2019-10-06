package com.fun.common.utils.gen;

import com.fun.project.admin.tool.entity.GenTable;

import java.io.IOException;

/**
 * 将表处理为genTable
 * @author DJun
 */
public class CodeGeneratorTool {
    /**
     * process Table Into GenTable
     * @param tableSql table sql
     * @return GenTable
     * @throws IOException exception
     */
    public static GenTable processTableIntoClassInfo(String tableSql) throws IOException {
        return TableParseUtil.processTableIntoClassInfo(tableSql);
    }

}
