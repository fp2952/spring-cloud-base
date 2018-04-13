package com.peng.db.spring.boot.samples.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peng.db.spring.boot.autoconfigure.service.BaseService;
import com.peng.db.spring.boot.samples.mapper.mapper.BaseUserMapper;
import com.peng.db.spring.boot.samples.mapper.model.BaseUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fp295 on 2018/4/7.
 */
@Service
public class BaseUserService extends BaseService<BaseUser> {}
