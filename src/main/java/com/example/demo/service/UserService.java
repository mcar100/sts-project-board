package com.example.demo.service;

import com.example.demo.model.InputFormat;
import com.example.demo.model.User;

public interface UserService {
	
	User getUserByEmailAndPassword(String email, String password) throws Exception;
	
	Integer getUserIdByEmailAndPassword(String email, String password) throws Exception;
	
	User getUserById(Integer userId) throws Exception;
	
	boolean insertUserInfo(User user) throws Exception;
	
	boolean checkDuplicatedUserInfo(InputFormat inputFormat) throws Exception;
}
