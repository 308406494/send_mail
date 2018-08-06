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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * CRM库数据源配置类
 *
 * @author wulong
 * @since 2018-1-15
 */
@Configuration
@MapperScan(basePackages = {"com.ccmp.sendmail.mapper.crm"}, sqlSessionFactoryRef = "crmSqlSessionFactory")
public class CrmDataSourceConfig {

    @Value("${spring.datasource.crm.url}")
    private String url;

    @Value("${spring.datasource.crm.username}")
    private String user;

    @Value("${spring.datasource.crm.password}")
    private String password;

    @Value("${spring.datasource.crm.driver-class-name}")
    private String driverClass;

//    @Value("${spring.datasource.crm.mapperLocations}")
//    private String mapperLocations;

    @Bean("crm")
    @Primary
    public DataSource crmDataSource() {
        return DataSourceBuilder.create().url(url).username(user).password(password).driverClassName(driverClass)
                .build();
    }

    @Bean("crmSqlSessionFactory")
    @Primary
    public SqlSessionFactory crmSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(crmDataSource());
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return factoryBean.getObject();
    }

    @Bean("crmSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate crmSqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(crmSqlSessionFactory());
        return template;
    }
}
