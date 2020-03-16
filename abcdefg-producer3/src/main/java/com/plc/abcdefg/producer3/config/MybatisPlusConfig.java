package com.plc.abcdefg.producer3.config;

import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * MybatisPlus配置
 *
 * @author stylefeng
 * @Date 2017/5/20 21:58
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    /**
     * 乐观锁mybatis插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() { return new OptimisticLockerInterceptor(); }

}
