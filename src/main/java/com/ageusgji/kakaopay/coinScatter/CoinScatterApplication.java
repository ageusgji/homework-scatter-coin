package com.ageusgji.kakaopay.coinScatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class CoinScatterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinScatterApplication.class, args);
	}

}
