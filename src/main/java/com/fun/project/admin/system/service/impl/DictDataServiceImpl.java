package com.fun.project.admin.system.service.impl;

import com.fun.common.utils.TimestampUtil;
import com.fun.framework.shiro.helper.ShiroUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.common.utils.text.Convert;
import com.fun.project.admin.system.mapper.DictDataMapper;
import com.fun.project.admin.system.service.IDictDataService;
import com.fun.project.admin.system.entity.dict.DictData;

import java.util.List;

/**
 * 字典数据
 *
 * @author DJun
 * @date 2019/10/30
 */
@Service
public class DictDataServiceImpl implements IDictDataService {

    @Autowired
    private DictDataMapper dictDataMapper;

    /**
     * 分页查询DictData列表
     */
    @Override
    public List<DictData> selectDictDataList(DictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 通过Id查询 DictData
     */
    @Override
    public DictData selectDictDataById(long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 新增DictData
     */
    @Override
    public int insertDictData(DictData dictData) {
        dictData.setCreateTime(TimestampUtil.getCurrentTimestamp13());
        dictData.setCreateBy(ShiroUtils.getLoginName());
        return dictDataMapper.insertDictData(dictData);
    }

    /**
     * 通过id删除DictData
     */
    @Override
    public int deleteDictDataById(long dictCode) {
        return dictDataMapper.deleteDictDataById(dictCode);
    }

    /**
     * 通过id批量删除DictData
     */
    @Override
    public int deleteDictDataByIds(String dictCodes) {
        return dictDataMapper.deleteDictDataByIds(Convert.toStrArray(dictCodes));
    }

    /**
     * 修改DictData信息
     */
    @Override
    public int updateDictData(DictData dictData) {
        dictData.setUpdateTime(TimestampUtil.getCurrentTimestamp13());
        dictData.setUpdateBy(ShiroUtils.getLoginName());
        return dictDataMapper.updateDictData(dictData);
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }
}
