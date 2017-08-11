package com.tiamaes.bike.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@MapperScan("com.tiamaes.bike.**.persistence")
public class MyBatisConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	@ConditionalOnClass(com.alibaba.druid.pool.DruidDataSource.class)
	protected DataSource dataSource() {
		return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
	}
	
	@Bean
	protected SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setFailFast(true);
		factory.setDataSource(dataSource);
		factory.setVfs(SpringBootVFS.class);
		factory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		return factory.getObject();
	}
}
