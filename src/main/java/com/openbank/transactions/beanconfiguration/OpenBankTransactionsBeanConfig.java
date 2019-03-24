package com.openbank.transactions.beanconfiguration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openbank.transactions.model.OpenBankTransactionBO;

@Configuration
@PropertySource("classpath:config.properties")
public class OpenBankTransactionsBeanConfig {

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	OpenBankTransactionBO getOpenBankTransactionBO()
	{
		return new OpenBankTransactionBO();
	}
	
	@Bean
	RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	@Bean
	ObjectMapper getObjectMapper()
	{
		return new ObjectMapper();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	      return new PropertySourcesPlaceholderConfigurer();
	}
	
}
