package com.example.socialnetworking.model;

import java.time.LocalTime;

import lombok.Data;

@Data
public class Message {
	private User user;
	private String message;
	private LocalTime posted; // Keep it simple (i.e., no Date arithmetic)
}
