package com.example.socialnetworking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.socialnetworking.model.Message;
import com.example.socialnetworking.model.User;

class MessageRepositoryTest {

	private MessageRepository target;
	
	@BeforeEach
	void setup(){
		target = new MessageRepository();
	}
	
	@Test
	void add() {
		Message msg = new Message();
		
		target.add(msg);

		assertEquals(1, target.getMessageList().size());
		assertSame(msg, target.getMessageList().get(0));
	}
	
	@Test
	void getPublisherMessages() {
		User user = new User();
		user.setUserName("Alice");

		User wrongUser = new User();
		wrongUser.setUserName("WrongUser");		
		
		Message msg = new Message();
		msg.setUser(user);
	
		Message wrongUserMsg = new Message();
		wrongUserMsg.setUser(wrongUser);		
		
		target.add(msg);
		target.add(wrongUserMsg);
		
		List<Message> messages = target.getPublisherMessages(user);
		assertEquals(1, messages.size());
		assertEquals("Alice", messages.get(0).getUser().getUserName());
	}
}
