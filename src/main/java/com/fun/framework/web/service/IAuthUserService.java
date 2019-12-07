package com.fun.framework.web.service;

import java.util.List;

/**
 * Admin 和 App 端通用授权用户接口
 *
 * @author DJun
 * @date 2019/12/2
 */
public interface IAuthUserService<T> {

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<T> selectAllocatedList(T user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<T> selectUnallocatedList(T user);

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int insertAuthUsers(Long roleId, String userIds);

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要删除的用户数据ID
     * @return 结果
     */
    int deleteAuthUsers(Long roleId, String userIds);

    /**
     * 取消单个用户的授权
     *
     * @param roleId roleId
     * @param userId userId
     * @return success-1,fail-0
     */
    int deleteAuthUser(Long roleId, Long userId);
}
