package com.pj.mall.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author Jack
 * @create 2019-04-02 10:34
 */
public class CodeUtil {

    /**
     * MD5盐值加密
     * @param data
     * @param salt
     * @return
     */
    public static String md5Hex(String data,String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.md5Hex(salt + DigestUtils.md5Hex(data));
    }

    /**
     * SHA盐值加密
     * @param data
     * @param salt
     * @return
     */
    public static String shaHex(String data, String salt) {
        if (StringUtils.isBlank(salt)) {
            salt = data.hashCode() + "";
        }
        return DigestUtils.sha512Hex(salt + DigestUtils.sha512Hex(data));
    }

    /**
     * 生成盐值
     * @return
     */
    public static String generateSalt(){
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
