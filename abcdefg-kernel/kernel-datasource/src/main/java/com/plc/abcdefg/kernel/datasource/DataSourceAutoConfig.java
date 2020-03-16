package com.plc.abcdefg.kernel.datasource;


import com.alibaba.druid.pool.DruidDataSource;
import com.plc.abcdefg.kernel.datasource.util.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


@Configuration
public class DataSourceAutoConfig {

	@Autowired
	DefaultProperties defaultProperties;

	@Autowired
	private Environment env;

	//解析yml文件
	public Map<String,Object> analysisYmal() throws Exception{
		Yaml yaml = new Yaml();
		Map ymalMap = new HashMap();
		Resource app = new ClassPathResource("application.yml");
		if(env.getActiveProfiles().length != 0){
			Resource appEnv = new ClassPathResource("application-" + env.getActiveProfiles()[0] + ".yml");
			URL appEnvUrl = appEnv.getURL();
			Map<String,Object> appEnvMap =(Map)yaml.load(new FileInputStream(appEnvUrl.getFile()));
			ymalMap.put("application-" + env.getActiveProfiles()[0] + ".yml",appEnvMap);
		}
		URL appUrl = app.getURL();
		Map<String,Object> appMap =(Map)yaml.load(new FileInputStream(appUrl.getFile()));
		ymalMap.put("application.yml",appMap);
		return ymalMap;
	}

	// TODO: 2020/3/13 mybatis的基本配置也写在这里 

	/**
	 * 默认的数据源
	 * @return
	 */
	private DruidDataSource defaultDataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		defaultProperties.config(dataSource);
		return dataSource;
	}

	@Bean
	public DataSource dataSource() throws Exception{
		Map<String,Object> ymlMap = analysisYmal();
		Map<String,Object> dynamicDataSourceMap = new TreeMap<>();
		for (String key : ymlMap.keySet()) {
			Map<String,Object> temp = (Map)((Map)ymlMap.get(key)).get("spring");
			if(temp.get("datasource") != null){
				return defaultDataSource();
			}
			for (String tempKey : temp.keySet()) {
				if(tempKey.startsWith("datasource-")){
					dynamicDataSourceMap.put(tempKey,temp.get(tempKey));
				}
			}
		}
		if(dynamicDataSourceMap.isEmpty()){
            return defaultDataSource();
        }
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		HashMap<Object, Object> datasourceMap = new HashMap();
		for (String key : dynamicDataSourceMap.keySet()) {
			Map<String,Object> datasourceFieldMap = (Map)dynamicDataSourceMap.get(key);
			for (String datasourceField:datasourceFieldMap.keySet()) {
				Field field = defaultProperties.getClass().getDeclaredField(datasourceField);
				Method method = defaultProperties.getClass().getDeclaredMethod("set"+initCap(datasourceField),field.getType());
				method.invoke(defaultProperties,datasourceFieldMap.get(datasourceField));
			}
			DruidDataSource dataSourceTemp = new DruidDataSource();
			defaultProperties.config(dataSourceTemp);
			datasourceMap.put(key.split("-")[1], dataSourceTemp);
		}
		dynamicDataSource.setTargetDataSources(datasourceMap);
		dynamicDataSource.setDefaultTargetDataSource(datasourceMap.entrySet().iterator().next().getValue());
        return dynamicDataSource;
    }

	private String initCap(String str){
		if(str == null|| "".equals(str)){
			return str;
		}
		if(str.length() == 1){
			return str.toUpperCase();
		}else{
			return str.substring(0,1).toUpperCase()+str.substring(1);
		}

	}


   
}
