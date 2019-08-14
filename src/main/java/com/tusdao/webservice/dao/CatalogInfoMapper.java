package com.tusdao.webservice.dao;

import com.tusdao.webservice.model.CatalogInfo;
import com.tusdao.webservice.model.CatalogInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CatalogInfoMapper {
    long countByExample(CatalogInfoExample example);

    int deleteByExample(CatalogInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CatalogInfo record);

    int insertSelective(CatalogInfo record);

    List<CatalogInfo> selectByExample(CatalogInfoExample example);

    CatalogInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CatalogInfo record, @Param("example") CatalogInfoExample example);

    int updateByExample(@Param("record") CatalogInfo record, @Param("example") CatalogInfoExample example);

    int updateByPrimaryKeySelective(CatalogInfo record);

    int updateByPrimaryKey(CatalogInfo record);
}