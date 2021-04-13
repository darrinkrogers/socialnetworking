package com.example.socialnetworking.controller;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialnetworking.model.Message;
import com.example.socialnetworking.model.User;
import com.example.socialnetworking.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SocialController {
	
	private final MessageRepository messageRepository;
	
	@PostMapping(path = "/api/message")
	public String postMessage(@RequestBody Message message) {
		message.setPosted(LocalTime.now());
		messageRepository.add(message);
		
		return "Message published: " + message.getMessage();
	}
	
	@GetMapping(path = "/api/publishermessages/{userName}")
	public String getPublisherMessages(@PathVariable String userName) {
		final StringBuilder msgBuilder = new StringBuilder();
		
		List<Message> messages = getMessages(userName);
		messages.stream().forEach(m -> {
			msgBuilder.append(msgBuilder.toString().equals("")?m.getMessage():"\n" + m.getMessage());
		});
		
		return msgBuilder.toString();
	}
	
	@GetMapping(path = "/api/followedmessages/{followedUserName}")
	public String getFollowedMessages(@PathVariable  String followedUserName) {
		final StringBuilder msgBuilder = new StringBuilder();
		
		List<Message> messages = getMessages(followedUserName);
		messages.stream().forEach(m -> {
			Long minutesOld = ChronoUnit.MINUTES.between(m.getPosted(), LocalTime.now());
			String msg = m.getMessage() + " (" + minutesOld + " minutes ago)";
			msgBuilder.append(msgBuilder.toString().equals("")?msg:"\n" + msg);
		});
		
		return msgBuilder.toString();
	}
	
	private List<Message> getMessages(String userName){
		User publisher = new User();
		publisher.setUserName(userName);
		
		return messageRepository.getPublisherMessages(publisher);		
	}
}
