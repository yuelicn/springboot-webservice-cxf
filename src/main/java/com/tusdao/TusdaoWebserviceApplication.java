package com.tusdao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yueli
 * @date 2019-08-05 19:17
 */
@MapperScan(value = {"com.tusdao.*.dao"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TusdaoWebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TusdaoWebserviceApplication.class, args);
    }
}