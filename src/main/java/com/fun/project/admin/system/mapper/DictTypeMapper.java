package com.fun.project.admin.system.mapper;

import com.fun.project.admin.system.entity.dict.DictType;

import java.util.List;

/**
 * 字典类型
 *
 * @author DJun
 * @date 2019/10/30
 */
public interface DictTypeMapper {

    /**
     * 查询DictType列表
     *
     * @param dictType 查询对象
     * @return 查询列表
     */
    List<DictType> selectDictTypeList(DictType dictType);

    /**
     * 通过Id查询 DictType
     *
     * @param dictId 查询Id
     * @return 查询对象
     */
    DictType selectDictTypeById(long dictId);

    /**
     * 新增DictType
     *
     * @param dictType 新增对象
     * @return 插入行数
     */
    int insertDictType(DictType dictType);

    /**
     * 修改DictType信息
     *
     * @param dictType 用户对象
     * @return 更新行数
     */
    int updateDictType(DictType dictType);

    /**
     * 通过id删除DictType
     *
     * @param dictId 删除id
     * @return 删除行数
     */
    int deleteDictTypeById(long dictId);

    /**
     * 通过id批量删除DictType
     *
     * @param dictIds 删除ids
     * @return 删除行数
     */
    int deleteDictTypeByIds(String[] dictIds);

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    List<DictType> selectDictTypeAll();

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    DictType selectDictTypeByType(String dictType);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    DictType checkDictTypeUnique(String dictType);
}
