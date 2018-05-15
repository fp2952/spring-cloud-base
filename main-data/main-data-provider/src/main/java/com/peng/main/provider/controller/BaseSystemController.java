package com.peng.main.provider.controller;

import com.github.pagehelper.PageInfo;
import com.peng.common.pojo.ResponseData;
import com.peng.common.pojo.TableData;
import com.peng.common.utils.UUID;
import com.peng.db.spring.boot.autoconfigure.controller.CrudController;
import com.peng.main.api.mapper.model.BaseRole;
import com.peng.main.api.mapper.model.BaseSystem;
import com.peng.main.api.pojo.ResponseCode;
import com.peng.main.api.pojo.request.BaseSystemRequest;
import com.peng.main.api.pojo.response.ModuleAndSystemResponse;
import com.peng.main.provider.service.BaseSystemService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by fp295 on 2018/5/13.
 */
@RestController
public class BaseSystemController extends CrudController<BaseSystem, BaseSystemRequest> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseSystemService baseSystemService;

    @PostMapping("/system/table")
    @Override
    protected ResponseData<TableData<BaseSystem>> queryRecord(@RequestBody BaseSystemRequest query) {
        logger.debug("查询系统表格");
        Example example = new Example(BaseSystem.class);
        Example.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(query.getProjectName())) {
            criteria.andLike("projectName", "%" + query.getProjectName() + "%");
        }
        if(!StringUtils.isEmpty(query.getSystemName())) {
            criteria.andLike("systemName", "%" + query.getSystemName() + "%");
        }

        PageInfo<BaseSystem> pageInfo = baseSystemService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/system")
    @Override
    protected ResponseData<BaseSystem> addRecord(@RequestBody BaseSystem record) {
        logger.debug("添加系统");
        try {
            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            baseSystemService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/system")
    @Override
    protected ResponseData<BaseSystem> deleteRecord(@RequestBody List<BaseSystem> record) {
        logger.debug("删除系统");
        try {
            baseSystemService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/system")
    @Override
    protected ResponseData<BaseSystem> updateRecord(@RequestBody BaseSystem record) {
        logger.debug("更新系统");
        try {
            record.setUpdateDate(new Date());
            baseSystemService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/system/validate/{projectName}")
    public ResponseData<BaseSystem> validateRoleCode(@PathVariable("projectName") String projectName) {
        logger.debug("校验系统项目名是否存在");
        BaseSystem baseSystem = new BaseSystem();
        baseSystem.setProjectName(projectName);
        baseSystem = baseSystemService.selectOne(baseSystem);
        if(baseSystem == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @GetMapping("/system")
    public ResponseData<List<BaseSystem>> getSystem() {
        logger.debug("查询所有系统");
        List<BaseSystem> list;
        try {
            list = baseSystemService.selectAll();
        } catch (Exception e) {
            logger.error("查询所有系统失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/system/module")
    public ResponseData<List<ModuleAndSystemResponse>> getModuleAndSystem() {
        logger.debug("查询系统及模块树");
        List<ModuleAndSystemResponse> list;
        try {
            list = baseSystemService.selectModuleAndSystem();
        } catch (Exception e) {
            logger.error("查询系统及模块树失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

}
