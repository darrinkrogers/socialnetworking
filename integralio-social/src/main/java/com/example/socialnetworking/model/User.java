package com.example.socialnetworking.model;

import java.util.List;

import lombok.Data;

@Data
public class User {
	private String userName;
	private List<User> followedUsers;
}
