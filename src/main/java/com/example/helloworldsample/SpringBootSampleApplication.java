package com.example.helloworldsample;

import com.example.helloworldsample.entity.HelloworldEntity;
import com.example.helloworldsample.repository.HelloworldRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class SpringBootSampleApplication {
	private final HelloworldRepository repository;

	@Autowired
	public SpringBootSampleApplication(HelloworldRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSampleApplication.class, args);
	}

	@GetMapping("/")
	public String home(@RequestParam(name="name", required=false,defaultValue="World") String name, Model model) {
		var helloworldName = repository.findAll().get(0).getName();
		model.addAttribute("name", helloworldName);
		return "home";
	}
}
