package com.peng.main.provider.mapper.mapper;


import com.peng.main.api.mapper.model.BaseRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseRoleMapper extends Mapper<BaseRole> {
    List<BaseRole> getRoleByUserId(String userId);
}