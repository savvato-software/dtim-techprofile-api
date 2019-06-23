package org.haxwell.dtim.techprofile;

import org.haxwell.dtim.techprofile._servletFilters.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
public class TechprofileApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechprofileApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean corsFilter_() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new CorsFilter());
		frb.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return frb;	
	}
}
