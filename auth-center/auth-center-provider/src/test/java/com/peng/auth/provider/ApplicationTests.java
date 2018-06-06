package com.peng.auth.provider;

import com.peng.main.api.mapper.model.BaseRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by fp295 on 2018/4/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private RedisTemplate<String, BaseRole> baseRoleTemplate;

    @Test
    public void test() throws Exception {

/*        BaseRole baseRole = new BaseRole();
        baseRole.setId("id");
        baseRole.setRoleCode("code");
        baseRoleTemplate.opsForValue().set(baseRole.getId(), baseRole);
        Assert.assertEquals(baseRole.getRoleCode(), baseRoleTemplate.opsForValue().get(baseRole.getId()).getRoleCode());*/

    }

}
