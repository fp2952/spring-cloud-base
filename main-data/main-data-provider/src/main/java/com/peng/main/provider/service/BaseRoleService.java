package com.peng.main.provider.service;

import com.peng.db.spring.boot.autoconfigure.service.BaseService;
import com.peng.main.api.mapper.model.BaseRole;
import com.peng.main.api.mapper.model.BaseUser;
import com.peng.main.provider.mapper.mapper.BaseRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseRoleService extends BaseService<BaseRole>{

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    public List<BaseRole> getRoleByUserId(String userId) {
        return ((BaseRoleMapper)mapper).getRoleByUserId(userId);
    }
}
