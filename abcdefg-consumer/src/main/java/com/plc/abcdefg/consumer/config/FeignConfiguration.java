package com.plc.abcdefg.consumer.config;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

	/**
	 * 将契约改为feign原生的默认契约，使用feign自带的注解
	 * @return
	 */
	/*@Bean
	public Contract feignContract() {
		return new Contract.Default();
	}*/

//	@Bean
//	public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
//		return new BasicAuthRequestInterceptor("panlicun","panlicun");
//	}

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.BASIC;
	}

}
