package com.haoge.rabbitmq;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.haoge.rabbitmq.entitys.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitMqApplicationTests {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	@Autowired
	AmqpAdmin amqpAdmin;
	@Test
	public void useAmqpAdmin() {
		//使用AmqpAdmin创建一个Queue
		amqpAdmin.declareQueue(new Queue("amqp.queue", true));
		//Exchange是一个接口，我们创建他的一个实现类
		DirectExchange directExchange = new DirectExchange("amqp.exchange");
		//创建Exchange
		amqpAdmin.declareExchange(directExchange);
		//创建绑定规则
		Binding binding = new Binding("amqp.queue", Binding.DestinationType.QUEUE, "amqp.exchange", "admin.haoge", null);
		amqpAdmin.declareBinding(binding);
	}

	@Test
	public void contextLoads() {
//		这个方法需要我们自己定义message，定义消息体内容和消息头
//		rabbitTemplate.send(exchange, routingKey, message);
//		object默认作为消息体，只需要传入发送的对象，自动序列化发送给rabbitMQ
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("msg", "这是第一个消息");
		map.put("data",Arrays.asList("123",true,"hello World"));
//		rabbitTemplate.convertAndSend("exchange.direct", "haoge.news", map);
		rabbitTemplate.convertAndSend("exchange.direct", "haoge.news", new Book("qqq","mmm"));
	}
	@Test
	public void testTeceive() {
		//接受消息
		Object object = rabbitTemplate.receiveAndConvert("haoge.news");
		System.out.println(object.getClass());
		System.out.println(object);
	}
	//广播模式
	@Test
	public void sendMsg() {
		//接受消息
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("msg", "这是第一个消息");
		map.put("data",Arrays.asList("123",true,"hello World"));
		rabbitTemplate.convertAndSend("exchange.fanout", "", map);
	}
}
