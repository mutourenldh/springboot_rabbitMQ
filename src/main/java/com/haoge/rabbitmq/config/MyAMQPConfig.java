package com.haoge.rabbitmq.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//自定义MessageConverter,使之序列化的时候使用json进行序列化
//这个MessageConverter会自动生效
@Configuration
public class MyAMQPConfig {
	@Bean
	public MessageConverter	messageConverter(){
		return new Jackson2JsonMessageConverter();
	}
}
