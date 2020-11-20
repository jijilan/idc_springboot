package com.idc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableTransactionManagement
@MapperScan(value = "com.idc.modules.mapper")
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class},
        scanBasePackages = {"com.idc"})
public class IdcApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdcApplication.class, args);
    }


}
