package com.example.socialnetworking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.socialnetworking.model.Message;
import com.example.socialnetworking.model.User;
import com.example.socialnetworking.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class SocialControllerTest {
	
	@InjectMocks
	SocialController target;
	
	@Mock
	MessageRepository mockMessageRepository;
	
	@Test
	void postMessage() {
		Message msg = new Message();
		msg.setMessage("msg 1");
		String response = target.postMessage(msg);
		
		verify(mockMessageRepository).add(msg);
		assertEquals("Message published: msg 1", response);
		assertNotNull(msg.getPosted());
	}
	
	@Test
	void getPublisherMessages() {
		Message msg = makeMessageForUser("I love the weather today.", 1);
		List<Message> publisherMessages = Collections.singletonList(msg);
		
		when(mockMessageRepository.getPublisherMessages(any(User.class)))
		  .thenReturn(publisherMessages);
		
		String msgString = target.getPublisherMessages("Alice");
		assertEquals("I love the weather today.", msgString);
	}

	@Test
	void getFollowedMessages() {
		List<Message> publisherMessages = new ArrayList<Message>();
		publisherMessages.add(makeMessageForUser("Darn! We lost!", 2));
		publisherMessages.add(makeMessageForUser("Good game though.", 1));
		
		when(mockMessageRepository.getPublisherMessages(any(User.class)))
		  .thenReturn(publisherMessages);
		
		String msgString = target.getFollowedMessages("Bob");
		assertEquals("Darn! We lost! (2 minutes ago)\nGood game though. (1 minutes ago)", msgString);
	}	
	
	private Message makeMessageForUser(String msg, int minutesAgo) {
		User publisher = new User();
		Message message = new Message();
		message.setMessage(msg);
		message.setUser(publisher);
		message.setPosted(LocalTime.now().minusMinutes(minutesAgo));
		
		return message;
	}
}
