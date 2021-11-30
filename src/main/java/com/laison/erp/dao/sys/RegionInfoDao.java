package com.laison.erp.dao.sys;
import com.laison.erp.common.utils.MybatisExtendedLanguageDriver;
import com.laison.erp.model.sys.RegionInfo;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author lihua  496583747@qq.com
 * @date  2021-03-09 14:21:04
 */
@org.apache.ibatis.annotations.Mapper
public interface RegionInfoDao extends Mapper<RegionInfo>{

    @Select("select COUNT(*)  FROM region_info WHERE parent_id =#{parentId}")
    int selectReleaseParentCount(@Param("parentId") String parentId);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT region_name FROM region_info WHERE region_code IN (#{regions})")
    List<String> findRegions(@Param("regions") List<String> regions);
}