package com.peng.main.provider.mapper.mapper;


import com.peng.main.api.mapper.model.BaseModuleResources;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseModuleResourcesMapper extends Mapper<BaseModuleResources> {
    List<BaseModuleResources> getMenusByUserId(@Param("userId") String userId);

    List<BaseModuleResources> selectModuleTree(@Param("id") String id, @Param("systemId")String systemId);
}