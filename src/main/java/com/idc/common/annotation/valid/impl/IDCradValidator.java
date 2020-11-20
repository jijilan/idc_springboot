package com.idc.common.annotation.valid.impl;

import com.idc.common.annotation.valid.Phone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @auther: jijl
 * @Date: Create in 2020/11/26
 * @Description: 自定身份证校验实现
 **/

public class IDCradValidator implements ConstraintValidator<Phone, String> {

    private Pattern pattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

    @Override
    public void initialize(Phone phone) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(s).matches();
    }
}
