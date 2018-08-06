package com.ccmp.sendmail.config.datasource;

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

import javax.sql.DataSource;

/**
 * 公共库数据源配置类
 *
 * @author wulong
 * @since 2018-2-23
 */

/**
 * common-stat数据源配置类
 */
@Configuration
@MapperScan(basePackages = {"com.ccmp.sendmail.mapper.common_stat"}, sqlSessionFactoryRef = "commonStatSqlSessionFactory")
public class CommStatDataSourceConfig {

    @Value("${spring.datasource.common-stat.url}")
    private String url;

    @Value("${spring.datasource.common-stat.username}")
    private String user;

    @Value("${spring.datasource.common-stat.password}")
    private String password;

    @Value("${spring.datasource.common-stat.driver-class-name}")
    private String driverClass;

//    @Value("${spring.datasource.common-stat.mapperLocations}")
//    private String mapperLocations;


    @Bean("common-stat")
    public DataSource commonStatDataSource() {
        return DataSourceBuilder.create().url(url).username(user).password(password).driverClassName(driverClass)
                .build();
    }

    @Bean("commonStatSqlSessionFactory")
    public SqlSessionFactory commonStatSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(commonStatDataSource());
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return factoryBean.getObject();
    }

    @Bean("commonStatSqlSessionTemplate")
    public SqlSessionTemplate commonStatSqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(commonStatSqlSessionFactory());
        return template;
    }
}