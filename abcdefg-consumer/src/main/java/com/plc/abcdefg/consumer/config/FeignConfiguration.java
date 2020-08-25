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

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
		return new BasicAuthRequestInterceptor("panlicun","panlicun");
	}

	/**
	 * Feign客户端可以配置各种的Logger.Level对象，告诉Feign记录哪些日志。Logger.Level的值有以下选择。
	 * 　　　　NONE，无记录（DEFAULT）。
	 * 　　　　BASIC，只记录请求方法和URL以及响应状态代码和执行时间。
	 * 　　　　HEADERS，记录基本信息以及请求和响应标头。
	 * 　　　　FULL，记录请求和响应的头文件，正文和元数据。
	 * @return
	 */
	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.BASIC;
	}

}
