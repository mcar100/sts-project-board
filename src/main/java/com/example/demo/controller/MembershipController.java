package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.InputFormat;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/membership")
public class MembershipController{
	
	@Autowired
	private UserService userService;
	
    @GetMapping
    public String goRegister() {
        return "/page/membership"; 
    }
	
	@PostMapping("/signup")
	@ResponseBody
    public boolean signUp(@RequestBody User user) throws Exception {
		try {
			if(user==null) {
				throw new Exception("요청된 정보가 없습니다.");
			}
			
    		boolean result = userService.insertUserInfo(user);
    		if(!result) {
    			throw new Exception("User Service 에러");
    		}
    		return true;
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
    }

	@GetMapping("/user")
	@ResponseBody
    public boolean checkNameDuplicate(@RequestParam Map<String, Object> map) throws Exception {
		try {
			if(map==null) {
				throw new Exception("요청된 정보가 없습니다.");
			}
			
			InputFormat inputFormat = new InputFormat();
			inputFormat.setType((String) map.get("type"));
			inputFormat.setValue((String) map.get("value"));
    		boolean check = userService.checkDuplicatedUserInfo(inputFormat);
    		if(check) {
    			throw new Exception("중복된 정보가 존재합니다.");
    		}
    		return false;
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    		return true;
    	}
    }
}
