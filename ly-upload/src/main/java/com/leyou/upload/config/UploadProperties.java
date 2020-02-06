package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @ClassName UploadProperties
 * @Description TODO
 * @Author Administrator
 * @Date 2020/2/6 14:50
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "leyou.upload")
public class UploadProperties {

    private String baseUrl;

    private List<String> allowFileType;
}
