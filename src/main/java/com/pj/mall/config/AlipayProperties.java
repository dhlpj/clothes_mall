package com.pj.mall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jack
 * @create 2019-04-18 18:38
 */
@Data
@ConfigurationProperties(prefix = "mall.alipay")
public class AlipayProperties {
    private String appId;
    private String merchantPrivateKey;
    private String alipayPublicKey;
    private String returnUrl;
    private String notifyUrl;
    private String signType;
    private String charset;
    private String format;
    private String gatewayUrl;
    private String productCode;
}
