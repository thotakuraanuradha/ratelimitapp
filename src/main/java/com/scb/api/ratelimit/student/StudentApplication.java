package com.scb.api.ratelimit.student;

import com.scb.api.ratelimit.student.config.RequestThrottleFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentApplication {

	/**
	 * This method is used to register FilterRegistrationBean to filter the requests
	 * with the application based on URI.
	 *
	 * @return FilterRegistrationBean
	 **/
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		//registrationBean.setFilter(new JwtFilter());
		registrationBean.setFilter(new RequestThrottleFilter());
		//  registrationBean.addUrlPatterns("/api/v1/*");
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

}
