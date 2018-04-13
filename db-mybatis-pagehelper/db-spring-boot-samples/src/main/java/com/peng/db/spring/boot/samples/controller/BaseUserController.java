package com.peng.db.spring.boot.samples.controller;

import com.github.pagehelper.PageInfo;
import com.peng.db.spring.boot.autoconfigure.controller.CrudController;
import com.peng.db.spring.boot.samples.mapper.model.BaseUser;
import com.peng.db.spring.boot.samples.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by fp295 on 2018/4/7.
 */
@Controller
public class BaseUserController{
    @Autowired
    private BaseUserService baseUserService;

    @RequestMapping("/users")
    @ResponseBody
    public List<BaseUser> select(){
        return baseUserService.selectAll();
    }

}
