package com.bdth.oa.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hj
 * @create 2019-04-25 14:20
 */
@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroProperties {

    private Integer sessionInvalidateTime = 30 * 60;  //session 失效时间（默认为30分钟 单位：秒）

    private Integer sessionValidationInterval = 15 * 60;  //session 验证失效时间（默认为15分钟 单位：秒）

}
