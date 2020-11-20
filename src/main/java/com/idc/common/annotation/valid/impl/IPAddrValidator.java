package com.idc.common.annotation.valid.impl;

import com.idc.common.annotation.valid.IPAddr;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;


/**
 * @auther: jijl
 * @Date: Create in 2020/11/26
 * @Description: 自定义IP地址校验实现
 **/
public class IPAddrValidator implements ConstraintValidator<IPAddr, String> {

    private Pattern pattern = Pattern.compile("(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})");

    @Override
    public void initialize(IPAddr ipAddr) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(s).matches();
    }
}
