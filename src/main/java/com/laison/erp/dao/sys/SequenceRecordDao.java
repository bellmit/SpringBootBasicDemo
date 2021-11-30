package com.laison.erp.dao.sys;

import com.laison.erp.model.sys.SequenceRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author lihua  496583747@qq.com
 * @date  2021-03-23 18:23:58
 */
@org.apache.ibatis.annotations.Mapper
public interface SequenceRecordDao extends Mapper<SequenceRecord>{

	@Select("SELECT * from sequence_record where sequence_prefix = #{codePrefix} limit 0,1 " )
	SequenceRecord selectByCodePrefix(@Param("codePrefix") String codePrefix);
	
}