package com.idc.common.annotation.valid;


import com.idc.common.annotation.valid.impl.LicensePlateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @auther: jijl
 * @Description: 自定义车牌
 * @Date: Create in 2020/11/26
 **/
@Constraint(validatedBy = LicensePlateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LicensePlate {

    String message() default "车牌号码不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
