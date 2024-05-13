package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginFormat {
	private String email;
	private String password;
	private String emailCheck;
	private String recaptcha;
}
