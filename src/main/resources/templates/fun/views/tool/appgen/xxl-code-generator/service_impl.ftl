package com.fun.project.app.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.fun.common.utils.text.Convert;
import com.fun.common.utils.TimestampUtil;
import com.fun.project.app.business.mapper.${classInfo.className}Mapper;
import com.fun.project.app.business.service.I${classInfo.className}Service;
import com.fun.project.app.business.entity.${classInfo.className};

import java.util.List;

/**
 * ${classInfo.classComment}
 *
 * @author DJun
 * @date ${.now?string('yyyy/MM/dd')}
 */
@Service
public class ${classInfo.className}ServiceImpl implements I${classInfo.className}Service {

	@Autowired
	private ${classInfo.className}Mapper ${classInfo.className?uncap_first}Mapper;

	/**
	 * 分页查询${classInfo.classComment}列表
	 */
	@Override
	public List<${classInfo.className}> select${classInfo.className}List(${classInfo.className} ${classInfo.className?uncap_first}){
		return  ${classInfo.className?uncap_first}Mapper.select${classInfo.className}List(${classInfo.className?uncap_first});
	}

	/**
	 * 通过Id查询 ${classInfo.classComment}
	 */
	@Override
	public ${classInfo.className} select${classInfo.className}ById(long ${classInfo.conversionPrimaryKey}) {
		return ${classInfo.className?uncap_first}Mapper.select${classInfo.className}ById(${classInfo.conversionPrimaryKey});
	}

	/**
     * 新增${classInfo.classComment}
     */
	@Override
	public int insert${classInfo.className}(${classInfo.className} ${classInfo.className?uncap_first}) {
		${classInfo.className?uncap_first}.setCreateTime(TimestampUtil.getCurrentTimestamp13());
		return ${classInfo.className?uncap_first}Mapper.insert${classInfo.className}(${classInfo.className?uncap_first});
	}

	/**
	 * 通过id删除${classInfo.classComment}
	 */
	@Override
	public int delete${classInfo.className}ById(long ${classInfo.conversionPrimaryKey}) {
		return ${classInfo.className?uncap_first}Mapper.delete${classInfo.className}ById(${classInfo.conversionPrimaryKey});
	}

	/**
	 * 通过id批量删除${classInfo.classComment}
	 */
	@Override
	public int delete${classInfo.className}ByIds(String ${classInfo.conversionPrimaryKey}s){
		return ${classInfo.className?uncap_first}Mapper.delete${classInfo.className}ByIds(Convert.toStrArray(${classInfo.conversionPrimaryKey}s));
	 }

	/**
	 * 修改${classInfo.classComment}信息
	 */
	@Override
	public int update${classInfo.className}(${classInfo.className} ${classInfo.className?uncap_first}) {
		${classInfo.className?uncap_first}.setUpdateTime(TimestampUtil.getCurrentTimestamp13());
		return ${classInfo.className?uncap_first}Mapper.update${classInfo.className}(${classInfo.className?uncap_first});
	}

}
