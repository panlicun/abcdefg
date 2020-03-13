package com.plc.abcdefg.kernel.datasource;


import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.plc.abcdefg.kernel.datasource.constant.DataSourceKey;
import com.plc.abcdefg.kernel.datasource.util.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.yaml.snakeyaml.Yaml;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


@Configuration
@EnableTransactionManagement(order = 2)//由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
public class DataSourceAutoConfig {

	@Autowired
	DefaultProperties defaultProperties;

	@Autowired
	private Environment env;

	//解析yml文件
	@Bean
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

    // TODO: 2020/3/13 测试mybatis的分页插件 
    // TODO: 2020/3/13 测试乐观锁插件是否可用 

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



//	@Autowired
//	MutiDataSourceProperties mutiDataSourceProperties;
//
//
//	/**
//	 * 另一个数据源
//	 */
//	private DruidDataSource bizDataSource() {
//		DruidDataSource dataSource = new DruidDataSource();
//		druidProperties.config(dataSource);
//		mutiDataSourceProperties.config(dataSource);
//		return dataSource;
//	}
//
//	/**
//	 * guns的数据源
//	 */
//	private DruidDataSource dataSourceApiService(){
//		DruidDataSource dataSource = new DruidDataSource();
//		druidProperties.config(dataSource);
//		return dataSource;
//	}
//
//	/**
//	 * 单数据源连接池配置
//	 */
//	@Bean
//	@ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "false")
//	public DruidDataSource singleDatasource() {
//		return dataSourceApiService();
//	}
//
//	/**
//	 * 多数据源连接池配置
//	 */
//	@Bean
//	@ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "true")
//	public DynamicDataSource mutiDataSource() {
//
//		DruidDataSource dataSourceGuns = dataSourceApiService();
//		DruidDataSource bizDataSource = bizDataSource();
//
//		try {
//			dataSourceGuns.init();
//			bizDataSource.init();
//		}catch (SQLException sql){
//			sql.printStackTrace();
//		}
//
//		DynamicDataSource dynamicDataSource = new DynamicDataSource();
//		HashMap<Object, Object> hashMap = new HashMap();
//		hashMap.put(DatasourceEnum.DATA_SOURCE_API_SERVICE, dataSourceGuns);
//		hashMap.put(DatasourceEnum.DATA_SOURCE_BIZ, bizDataSource);
//		dynamicDataSource.setTargetDataSources(hashMap);
//		dynamicDataSource.setDefaultTargetDataSource(dataSourceGuns);
//		return dynamicDataSource;
//	}
//
//	/**
//	 * mybatis-plus分页插件
//	 */
//	@Bean
//	public PaginationInterceptor paginationInterceptor() {
//		return new PaginationInterceptor();
//	}
//
//	/**
//	 * 数据范围mybatis插件
//	 */
//	@Bean
//	public DataScopeInterceptor dataScopeInterceptor() {
//		return new DataScopeInterceptor();
//	}
//
//	/**
//	 * 乐观锁mybatis插件
//	 */
//	@Bean
//	public OptimisticLockerInterceptor optimisticLockerInterceptor() { return new OptimisticLockerInterceptor(); }




	///-------------------------------------------------------------

//	@Autowired
//	DruidProperties druidProperties;
//
//
//	private DruidDataSource dataSourceApiService(){
//		DruidDataSource dataSource = new DruidDataSource();
//		druidProperties.config(dataSource);
//		return dataSource;
//	}
//
//
//	@Primary
//    @Bean // 只需要纳入动态数据源到spring容器
//    public DataSource dataSource() {
//        DynamicDataSource dataSource = new DynamicDataSource();
//        DataSource coreDataSource =  dataSourceCore() ;
//        DataSource logDataSource =  dataSourceLog();
//        dataSource.addDataSource(DataSourceKey.core, coreDataSource);
//        dataSource.addDataSource(DataSourceKey.log, logDataSource);
//        dataSource.setDefaultTargetDataSource(coreDataSource);
//        return dataSource;
//    }
//
//
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
//            throws Exception {
//    	MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//		sqlSessionFactory.setDataSource(dataSource);
////		//默认扫描com.open.*****.dao.*.xml
////		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/plc/abcdefg/**/modular/**/mapper/sqlMapperXml/*.xml"));
////		sqlSessionFactory.setTypeAliasesPackage("com.plc.abcdefg.kernel.model");
////
////		MybatisConfiguration configuration = new MybatisConfiguration();
////		// configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
////		configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
////		configuration.setMapUnderscoreToCamelCase(true);
////		configuration.setCacheEnabled(false);
////		sqlSessionFactory.setConfiguration(configuration);
//		// sqlSessionFactory.setPlugins(new Interceptor[]{
//		// //PerformanceInterceptor(),OptimisticLockerInterceptor()
//		// paginationInterceptor() //添加分页功能
//		// });
//		// sqlSessionFactory.setGlobalConfig(globalConfiguration());
//		return sqlSessionFactory.getObject();
//    }
//
//
//    @Bean // 将数据源纳入spring事物管理
//    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource")  DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//	/**
//	 * mybatis-plus分页插件
//	 */
//	@Bean
//	public PaginationInterceptor paginationInterceptor() {
//		return new PaginationInterceptor();
//	}
//
//
//	/**
//	 * 乐观锁mybatis插件
//	 */
//	@Bean
//	public OptimisticLockerInterceptor optimisticLockerInterceptor() { return new OptimisticLockerInterceptor(); }
   
}
