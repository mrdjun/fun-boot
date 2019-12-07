package com.fun.common.pagehelper;

import com.fun.common.constant.Constants;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页数据封装类
 *
 * @author DJun
 */
@Getter
@Setter
public class CommonPage<T> {

    /** 页数 */
    private Integer pageNum;

    /** 每一页显示数据数 */
    private Integer pageSize;

    /** 总页数 */
    private Integer totalPage;
    private Long total;
    /** 数据列表 */
    private List<T> list;

    /**  排序列 */
    private String orderByColumn;

    /** 排序的方向 "desc" 或者 "asc". */
    private String isAsc;

    public String getOrderBy() {
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        // 返回 res.data.list
        return result;
    }

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> CommonPage<T> restPage(Page<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }

    /**
     * 封装分页对象
     */
    public static CommonPage getCommonPage() {
        CommonPage commonPage = new CommonPage();
        commonPage.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        commonPage.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        commonPage.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        commonPage.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return commonPage;
    }

    public static CommonPage buildPageRequest() {
        return getCommonPage();
    }

}
