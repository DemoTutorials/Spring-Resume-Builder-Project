package com.avisys.email;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:emailTemplateVariables.properties")
public class EmailTemplateVariables {

	public static PropertySourcesPlaceholderConfigurer placeHolderCongfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
