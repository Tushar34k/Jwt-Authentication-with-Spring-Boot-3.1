package com.basic.auth.impl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basic.auth.impl.model.User;
import com.basic.auth.impl.userservice.UserService;

@RestController
@RequestMapping("/user")
public class SecurityController {

	@Autowired
	private UserService userService;

	@GetMapping("/details")
	public List<User> userDetails() {
		return this.userService.getUsers();
	}
}
