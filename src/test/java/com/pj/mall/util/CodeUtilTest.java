package com.pj.mall.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jack
 * @create 2019-04-02 10:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeUtilTest {
    @Test
    public void testGenerateSalt(){
        String salt = CodeUtil.generateSalt();
        String admin = CodeUtil.md5Hex("admin", salt);
        System.out.println(salt+"\n"+admin);
    }

    @Test
    public void testGenerateSalt2(){
        String salt = "8e88cfe9fe7c45c28c1be6a18ec758a5";
        String password = "f4c63365f0acbbd085fa0efb928640ef";
        System.out.println(StringUtils.equals(password,CodeUtil.md5Hex("admin",salt)));
    }
}