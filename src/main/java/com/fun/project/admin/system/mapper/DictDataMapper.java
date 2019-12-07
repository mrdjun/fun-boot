package com.fun.project.admin.system.mapper;

import com.fun.project.admin.system.entity.dict.DictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据
 *
 * @author DJun
 * @date 2019/10/30
 */
public interface DictDataMapper {

    /**
     * 查询DictData列表
     * @param dictData 查询对象
     * @return 查询列表
     */
    List<DictData> selectDictDataList(DictData dictData);

    /**
     * 通过Id查询 DictData
     * @param dictCode 查询Id
     * @return 查询对象
     */
    DictData selectDictDataById(long dictCode);

    /**
     * 新增DictData
     * @param dictData 新增对象
     * @return 插入行数
     */
    int insertDictData(DictData dictData);

    /**
     * 修改DictData信息
     * @param  dictData 用户对象
     * @return 更新行数
     */
    int updateDictData(DictData dictData);

    /**
     * 通过id删除DictData
     * @param dictCode 删除id
     * @return 删除行数
     */
    int deleteDictDataById(long dictCode);

    /**
     * 通过id批量删除DictData
     * @param dictCodes 删除ids
     * @return 删除行数
     */
    int deleteDictDataByIds(String[] dictCodes);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<DictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);
}
