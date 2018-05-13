package com.peng.main.provider.service;

import com.peng.db.spring.boot.autoconfigure.service.BaseService;
import com.peng.main.api.mapper.model.BaseModuleResources;
import com.peng.main.api.mapper.model.BaseRole;
import com.peng.main.provider.mapper.mapper.BaseModuleResourcesMapper;
import com.peng.main.provider.mapper.mapper.BaseRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseModuleResourceService extends BaseService<BaseModuleResources>{


    /**
     * 根据用户查询菜单
     * @param userId
     * @return
     */
    public List<BaseModuleResources> getMenusByUserId(String userId) {
        return ((BaseModuleResourcesMapper)mapper).getMenusByUserId(userId);
    }

    public List<BaseModuleResources> getModuleTree(String id, String systemId) {
        return ((BaseModuleResourcesMapper)mapper).selectModuleTree(id, systemId);
    }
}
