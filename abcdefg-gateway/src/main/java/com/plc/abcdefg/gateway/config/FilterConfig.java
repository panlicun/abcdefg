package com.plc.abcdefg.gateway.config;

import com.plc.abcdefg.gateway.filters.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    /**
     * token过滤器
     * @return
     */
    @Bean
    public TokenFilter preRequestLogFilter() {
        return new TokenFilter();
    }
}
