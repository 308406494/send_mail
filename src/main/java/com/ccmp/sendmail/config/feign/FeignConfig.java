package com.ccmp.sendmail.config.feign;

import com.ccmp.sendmail.config.feign.pltform.PlatformClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import feign.Logger;
import feign.Request;
import feign.Request.Options;
import feign.Retryer;
import feign.Target;
import feign.httpclient.ApacheHttpClient;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * feigh配置类
 * 
 * @author wulong
 * @since 2018-2-1
 *
 */
@Configuration
public class FeignConfig {


    @Value("${feign.platformBaseUrl}")
    private String platformBaseUrl;

    @Value("${feign.hystrix.timeout}")
    private int timeout;

    @Value("${feign.http.connectTimeoutMillis}")
    private int connectTimeoutMillis;

    @Value("${feign.http.readTimeoutMillis}")
    private int readTimeoutMillis;


    @Bean
    PlatformClient platformClient() throws InterruptedException {

        return HystrixFeign.builder().client(new ApacheHttpClient()).retryer(Retryer.NEVER_RETRY)// TODO
                .encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).logger(new Slf4jLogger())
                .logLevel(Logger.Level.HEADERS)
                .setterFactory(
                        (target, method) -> new SetterFactory.Default().create(target, method)
                                .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter()
                                        .withExecutionTimeoutInMilliseconds(timeout)))
                .target(PlatformClient.class, this.platformBaseUrl);
    }



}
