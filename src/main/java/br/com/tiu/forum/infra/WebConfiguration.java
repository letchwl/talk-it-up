package br.com.tiu.forum.infra;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        FilterRegistrationBean<HiddenHttpMethodFilter> filter = new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        filter.addUrlPatterns("/*");
        return filter;
    }

}
