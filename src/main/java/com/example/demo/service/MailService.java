package com.example.demo.service;

public interface MailService {
	public boolean checkEmailVerification(String authNum, String userNum);
	public String sendEmail(String email) throws Exception;	
}
