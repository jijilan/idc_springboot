package com.idc.mvc.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @auther: jijl
 * @Date: Create in 2020/10/16
 * @Description:
 **/
@Getter
@Setter
@ConfigurationProperties(prefix = "web")
@Component
public class WebResource {

    private String staticResourcePath;

    private String welcomePath;

    private String projectPath;

    private String qrCodePath;
}
