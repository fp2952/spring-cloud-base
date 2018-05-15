package com.peng.main.provider.service;

import com.peng.common.utils.UUID;
import com.peng.db.spring.boot.autoconfigure.service.BaseService;
import com.peng.main.api.mapper.model.BaseRole;
import com.peng.main.api.mapper.model.BaseRoleModule;
import com.peng.main.provider.mapper.mapper.BaseRoleMapper;
import com.peng.main.provider.mapper.mapper.BaseRoleModuleMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseRoleModuleService extends BaseService<BaseRoleModule>{

    @Transactional
    public void saveRoleModule(List<BaseRoleModule> roleModule) {
        if (roleModule.size() > 0 && !StringUtils.isEmpty(roleModule.get(0).getRoleId())) {
            BaseRoleModule module = new BaseRoleModule();
            module.setRoleId(roleModule.get(0).getRoleId());
            mapper.delete(module);
            roleModule.forEach(it -> {
                it.setId(UUID.uuid32());
                mapper.insertSelective(it);
            });
        }
    }

    // 查询关联角色的叶子模块
    public List<BaseRoleModule> selectLeafRoleModule(String roleId) {
        return ((BaseRoleModuleMapper)mapper).selectLeafRoleModule(roleId);
    }
}
