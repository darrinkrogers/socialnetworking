package com.example.socialnetworking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.socialnetworking.model.Message;
import com.example.socialnetworking.model.User;

import lombok.Data;

@Repository
@Data
public class MessageRepository {
	
	private List<Message> messageList = new ArrayList<Message>();
	
	public void add(Message msg) {
		messageList.add(msg);
	}
	
	public List<Message> getPublisherMessages(User publisher){
		return messageList.stream()
						  .filter(m -> m.getUser().getUserName().equals(publisher.getUserName()))
						  .collect(Collectors.toList());
	}
}
