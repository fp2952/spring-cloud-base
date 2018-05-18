package com.peng.main.provider.controller;

import com.github.pagehelper.PageInfo;
import com.peng.common.pojo.ResponseData;
import com.peng.common.pojo.TableData;
import com.peng.db.spring.boot.autoconfigure.controller.CrudController;
import com.peng.main.api.mapper.model.OauthClientDetails;
import com.peng.main.api.pojo.ResponseCode;
import com.peng.main.api.pojo.request.OauthClientDetailsRequest;
import com.peng.main.api.service.BaseClientRemoteService;
import com.peng.main.provider.service.OauthClientDetailsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * Created by fp295 on 2018/4/17.
 */
@RestController
public class OauthClientDetailsController extends CrudController<OauthClientDetails, OauthClientDetailsRequest> implements BaseClientRemoteService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @PostMapping("/client/table")
    @Override
    protected ResponseData<TableData<OauthClientDetails>> queryRecord(@RequestBody OauthClientDetailsRequest query) {
        logger.debug("查询客户端表格");
        Example example = new Example(OauthClientDetails.class);
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(query.getName())) {
            criteria.andLike("name", "%" + query.getName() + "%");
        }
        PageInfo<OauthClientDetails> pageInfo = oauthClientDetailsService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/client")
    @Override
    protected ResponseData<OauthClientDetails> addRecord(@RequestBody OauthClientDetails record) {
        logger.debug("添加客户端应用");
        try {
            oauthClientDetailsService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加客户端应用失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/client")
    @Override
    protected ResponseData<OauthClientDetails> deleteRecord(@RequestBody List<OauthClientDetails> record) {
        logger.debug("删除客户端应用");
        try {
            oauthClientDetailsService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除客户端应用失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/client")
    @Override
    protected ResponseData<OauthClientDetails> updateRecord(@RequestBody OauthClientDetails record) {
        logger.debug("更新客户端应用");
        try {
            oauthClientDetailsService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新客户端应用失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/client/validate/{clientId}")
    protected ResponseData<OauthClientDetails> validateClientId(@PathVariable("clientId") String clientId) {
        logger.debug("校验应用id是否存在");
        OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setClientId(clientId);
        oauthClientDetails = oauthClientDetailsService.selectOne(oauthClientDetails);
        if(oauthClientDetails == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @Override
    public ResponseData<List<OauthClientDetails>> getAllClient() {
        logger.debug("获取所有客户端应用");
        List<OauthClientDetails> list;
        try {
            list = oauthClientDetailsService.selectAll();
        } catch (Exception e) {
            logger.error("获取所有客户端应用失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }
}
