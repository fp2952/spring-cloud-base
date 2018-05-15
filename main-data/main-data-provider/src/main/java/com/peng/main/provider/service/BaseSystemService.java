package com.peng.main.provider.service;

import com.peng.db.spring.boot.autoconfigure.service.BaseService;
import com.peng.main.api.mapper.model.BaseRole;
import com.peng.main.api.mapper.model.BaseSystem;
import com.peng.main.api.pojo.response.ModuleAndSystemResponse;
import com.peng.main.provider.mapper.mapper.BaseRoleMapper;
import com.peng.main.provider.mapper.mapper.BaseSystemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseSystemService extends BaseService<BaseSystem>{
    public List<ModuleAndSystemResponse> selectModuleAndSystem() {
        return ((BaseSystemMapper)mapper).selectModuleAndSystem();
    }
}
