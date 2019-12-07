package com.fun.project.admin.system.service.impl;

import com.fun.common.constant.Constants;
import com.fun.common.utils.StringUtils;
import com.fun.framework.web.entity.Ztree;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.common.utils.text.Convert;
import com.fun.project.admin.system.mapper.DictTypeMapper;
import com.fun.project.admin.system.service.IDictTypeService;
import com.fun.project.admin.system.entity.dict.DictType;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典类型
 *
 * @author DJun
 * @date 2019/10/30
 */
@Service
public class DictTypeServiceImpl implements IDictTypeService {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    /**
     * 分页查询DictType列表
     */
    @Override
    public List<DictType> selectDictTypeList(DictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);

    }

    /**
     * 通过Id查询 DictType
     */
    @Override
    public DictType selectDictTypeById(long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<DictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 新增DictType
     */
    @Override
    public int insertDictType(DictType dictType) {
        return dictTypeMapper.insertDictType(dictType);
    }

    /**
     * 通过id删除DictType
     */
    @Override
    public int deleteDictTypeById(long dictId) {
        return dictTypeMapper.deleteDictTypeById(dictId);
    }

    /**
     * 通过id批量删除DictType
     */
    @Override
    public int deleteDictTypeByIds(String dictIds) {
        return dictTypeMapper.deleteDictTypeByIds(Convert.toStrArray(dictIds));
    }

    /**
     * 修改DictType信息
     */
    @Override
    public int updateDictType(DictType dictType) {
        return dictTypeMapper.updateDictType(dictType);
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public DictType selectDictTypeByType(String dictType) {
        return dictTypeMapper.selectDictTypeByType(dictType);
    }


    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(DictType dict) {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        DictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
            return Constants.NOT_UNIQUE;
        }
        return Constants.UNIQUE;
    }

    /**
     * 查询字典类型树
     *
     * @param dictType 字典类型
     * @return 所有字典类型
     */
    @Override
    public List<Ztree> selectDictTree(DictType dictType) {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<DictType> dictList = dictTypeMapper.selectDictTypeList(dictType);
        for (DictType dict : dictList) {
            if (Constants.DICT_NORMAL.equals(dict.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dict.getDictId());
                ztree.setName(transDictName(dict));
                ztree.setTitle(dict.getDictType());
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    public String transDictName(DictType dictType) {
        StringBuffer sb = new StringBuffer();
        sb.append("(" + dictType.getDictName() + ")");
        sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
        return sb.toString();
    }
}
