package com.fun.common.constant;

/**
 * Admin代码生成通用常量
 *
 * @author DJun
 */
public interface GenConstants {
    /**
     * 单表（增删改查）
     */
     String TPL_CRUD = "crud";

    /**
     * 树表（增删改查）
     */
     String TPL_TREE = "tree";

    /**
     * 树编码字段
     */
     String TREE_CODE = "treeCode";

    /**
     * 树父编码字段
     */
     String TREE_PARENT_CODE = "treeParentCode";

    /**
     * 树名称字段
     */
     String TREE_NAME = "treeName";

    /**
     * 数据库字符串类型
     */
     String[] COLUMNTYPE_STR = {"char", "varchar", "narchar", "varchar2", "tinytext", "text",
            "mediumtext", "longtext"};

    /**
     * 数据库时间类型
     */
     String[] COLUMNTYPE_TIME = {"datetime", "time", "date", "timestamp"};

    /**
     * 数据库数字类型
     */
     String[] COLUMNTYPE_NUMBER = {"tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bigint", "float", "float", "double", "decimal"};

    /**
     * 页面不需要编辑字段
     */
     String[] COLUMNNAME_NOT_EDIT = {"id", "create_by", "create_time", "del_flag"};

    /**
     * 页面不需要显示的列表字段
     */
     String[] COLUMNNAME_NOT_LIST = {"id", "create_by", "create_time", "del_flag", "update_by",
            "update_time"};

    /**
     * 页面不需要查询字段
     */
     String[] COLUMNNAME_NOT_QUERY = {"id", "create_by", "create_time", "del_flag", "update_by",
            "update_time", "remark"};

    /**
     * 文本框
     */
     String HTML_INPUT = "input";

    /**
     * 文本域
     */
     String HTML_TEXTAREA = "textarea";

    /**
     * 下拉框
     */
     String HTML_SELECT = "select";

    /**
     * 单选框
     */
     String HTML_RADIO = "radio";

    /**
     * 复选框
     */
     String HTML_CHECKBOX = "checkbox";

    /**
     * 日期控件
     */
     String HTML_DATETIME = "datetime";

    /**
     * 字符串类型
     */
     String TYPE_STRING = "String";

    /**
     * 整型
     */
     String TYPE_INTEGER = "Integer";

    /**
     * 长整型
     */
     String TYPE_LONG = "Long";

    /**
     * 浮点型
     */
     String TYPE_DOUBLE = "Double";

    /**
     * 高精度计算类型
     */
     String TYPE_BIGDECIMAL = "BigDecimal";

    /**
     * 时间类型
     */
     String TYPE_DATE = "Date";

    /**
     * 模糊查询
     */
     String QUERY_LIKE = "LIKE";

    /**
     * 需要
     */
     String REQUIRE = "1";
}
