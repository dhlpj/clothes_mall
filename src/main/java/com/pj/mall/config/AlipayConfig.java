package com.pj.mall.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jack
 * @create 2019-04-18 18:42
 */
@Configuration
@EnableConfigurationProperties(AlipayProperties.class)//需要注入到方法的参数，必须使用这个
public class AlipayConfig {

    @Bean
    public AlipayClient alipayClient(AlipayProperties properties){
        return new DefaultAlipayClient(properties.getGatewayUrl(), properties.getAppId(), properties.getMerchantPrivateKey(),
                        properties.getFormat(), properties.getCharset(), properties.getAlipayPublicKey(), properties.getSignType());
    }
}
