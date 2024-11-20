package com.basic.auth.impl.userservice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.basic.auth.impl.model.User;

@Service
public class UserService {

	List<User> user = new ArrayList<>();

	UserService() {
		user.add(new User(UUID.randomUUID().toString(), "akash", "akash@gmail.com"));
		user.add(new User(UUID.randomUUID().toString(), "tushar", "tusahr@gmail.com"));
		user.add(new User(UUID.randomUUID().toString(), "ajay", "ajay@gmail.com"));
	}

	public List<User> getUsers() {
		return this.user;
	}

}
