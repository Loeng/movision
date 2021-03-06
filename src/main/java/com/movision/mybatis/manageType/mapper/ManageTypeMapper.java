package com.movision.mybatis.manageType.mapper;

import com.movision.mybatis.manageType.entity.ManageType;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface ManageTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManageType record);

    int insertSelective(ManageType record);

    ManageType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManageType record);

    int updateByPrimaryKey(ManageType record);

    List<ManageType> findAllqueryAdvertisementTypeList();

    int addAdvertisementType(Map map);

    Integer queryAdvertisementType();

    ManageType queryAdvertisementTypeById(String id);

    List<ManageType> findAllQueryAdvertisementTypeLikeName(ManageType map, RowBounds rowBounds);

    Integer queryAdvertisementLocation(String type);
}