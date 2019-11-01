package com.pj.mall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jack
 * @create 2019-03-22 11:24
 */
@Data
@ConfigurationProperties(prefix = "mall.upload")
public class UploadProperties {
    private String baseUrl;
    private List<String> allowTypes;
}
