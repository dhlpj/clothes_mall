package com.pj.mall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jack
 * @create 2019-04-18 9:21
 */
@Data
@ConfigurationProperties(prefix = "mall.id-worker")
public class IdWorkerProperties {

    private long workerId;// 当前机器id

    private long dataCenterId;// 序列号
}