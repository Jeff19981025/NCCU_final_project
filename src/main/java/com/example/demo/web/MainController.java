package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/hospital")
	public String hospital() {
		return "hospital";
	}
	@GetMapping("/**")
	public String home() {
		return "index";
	}
	
	@GetMapping("/start")
	public String start() {
		
		return "start";
	}
	
	@GetMapping("/sendauthcode")
	public String sendauthcode() {
		
		return "sendauthcode";
	}
}
