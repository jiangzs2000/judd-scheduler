package com.monefish.judd.scheduler.config.interceptors;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class InteceptorAdder implements WebMvcConfigurer {

    @Autowired
    private StringHttpMessageConverter stringHttpMessageConverter;
    @Autowired
    private FastJsonHttpMessageConverter4 fastConverter;
    @Autowired
    private ByteArrayHttpMessageConverter byteArrayHttpMessageConverter;

    @Autowired
    private GlobalAspectInteceptor globalAspectInteceptor;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{
            "classpath:/META-INF/resources/",
            "classpath:/WEB-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
         /**表示拦截/下的所有路径， /*表示只拦截/下的一级路径
		 */
        registry.addInterceptor(globalAspectInteceptor).addPathPatterns("/**");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.
                favorParameter(false).
                ignoreAcceptHeader(false).
                defaultContentType(MediaType.APPLICATION_JSON);
				//defaultContentType(MediaType.TEXT_HTML).
				//mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(this.byteArrayHttpMessageConverter);
        converters.add(this.stringHttpMessageConverter);

        /* 在judd-base已经设置过了
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
		this.fastConverter.setFastJsonConfig(fastJsonConfig);
		 */
        converters.add(this.fastConverter);
    }

    /**
     * httprequest请求，获取body内容拦截器
     *
     */
    @Bean
    public FilterRegistrationBean bodyReaderFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new BodyReaderFilter());
        //添加拦截器拦截路径
        registration.addUrlPatterns(new String[]{"/basic/setCenter/paySet/set", "/basic/setCenter/withdraw/set"});
        registration.setName("bodyReaderFilter");
        return registration;
    }
}