package com.haoge.rabbitmq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.haoge.rabbitmq.entitys.Book;

@Service
public class BookService {
	// RabbitMQ监听huoying.emps队列，如果有消息，就会自动接收
	@RabbitListener(queues = "huoying.emps")
	public void receive(Message message) {
		System.out.println("123" + message.getBody());
		System.out.println("123" + message.getMessageProperties());
	}

	// RabbitMQ监听haoge.news队列，如果有消息，就会自动接收
	// 然后将接收到的消息直接转换成Book对象
	@RabbitListener(queues = "haoge.news")
	public void receiveBook(Book book) {
		System.out.println("123" + book);
	}
}
