package com.idc.common.annotation.log;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @auther: jijl
 * @Date: Create in 2020/11/22
 * @Description: 业务日志注解
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface SysLog {

    /**
     * 行为描述，数据类型为String类型
     * @return
     */
    String value() default "";
}
