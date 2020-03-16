# kernel-datasource
**kernel-datasource是数据源模块，需要连接数据库的模型可以通过引入该包实现连接数据库的功能。**
**kernel-datasource使用的druid数据库连接工具。**
**注意事项：**
**1. 首先需要让项目扫描到我们的包，包路径为**

`com.plc.abcdefg.kernel`

可以使用

`@SpringBootApplication(scanBasePackages = {"com.plc.abcdefg.kernel.datasource"})`

也可以使用

`@ComponentScan(basePackages = {"com.plc.abcdefg.kernel.datasource"})`

**2. yml文件必须为application.yml否则无法读取yml中的数据库相关配置**

**3. 数据源的默认路径为**
```yaml
spring:
	datasource:  
		 url: jdbc:mysql://127.0.0.1:3306/xxxx?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false
		 username: xxx
		 password: xxx
		 filters: log4j,wall,mergeStat
```
如果想使用多数据源,格式如下：
```yaml
spring
	datasource-core:
		url:jdbc:mysql://127.0.0.1:3306/xxx?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false
		username: xxx
		password: xxx
		filters: log4j,wall,mergeStat
	datasource-log:
		url: jdbc:mysql://127.0.0.1:3306/xxx?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false
		username: xxx
		password: xxx
		filters: log4j,wall,mergeStat
```
###### 注意：
如果有datasource就用则使用datasource数据源。如果没有写datasource这个节点，则使用下面的其他数据源，如果没有datasource则默认使用下面的第一个数据源（如上文中的配置，则使用core数据源）。如果没有任何数据源则使用默认的数据源做为数据连接，详见DefaultProperties类。 

在使用多数据源时，需要在service的实现类方法上使用@DataSource注解选择要使用的数据源，在注释内使用数据源时，则直接写datasource-core横线的后半段即可，如：
```java
    @Override
    @DataSource(name = "core")
    public User getUserByAccount(String account) {
        return userMapper.getByAccount(account);
    }

    @Override
    @DataSource(name = "log")
    public User getUserByAccount2(String account) {
        return userMapper.getByAccount(account);
    }
```
如果yml中没有写多数据源的配置，@DataSource不起作用，使用默认的数据源连接数据库。


