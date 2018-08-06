package com.ccmp.sendmail.config.datasource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 公共库数据源配置类
 *
 * @author wulong
 * @since 2018-1-15
 */
@Configuration
@MapperScan(basePackages = {
        "com.ccmp.sendmail.mapper.common" }, sqlSessionFactoryRef = "commonSqlSessionFactory")
public class CommDataSourceConfig {

    @Value("${spring.datasource.common.url}")
    private String url;

    @Value("${spring.datasource.common.username}")
    private String user;

    @Value("${spring.datasource.common.password}")
    private String password;

    @Value("${spring.datasource.common.driver-class-name}")
    private String driverClass;

//    @Value("${spring.datasource.common.mapperLocations}")
//    private String mapperLocations;


    @Bean("common")
    public DataSource commonDataSource() {
        return DataSourceBuilder.create().url(url).username(user).password(password).driverClassName(driverClass)
                .build();
    }

    @Bean("commonSqlSessionFactory")
    public SqlSessionFactory commonSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(commonDataSource());
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return factoryBean.getObject();
    }

    @Bean("commonSqlSessionTemplate")
    public SqlSessionTemplate commonSqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(commonSqlSessionFactory());
        return template;
    }

    @Bean(name = "commonTransactionManager")
    public PlatformTransactionManager commonTransactionManager() {
        return new DataSourceTransactionManager(commonDataSource());
    }
}
