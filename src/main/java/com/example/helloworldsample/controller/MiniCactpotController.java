package com.example.helloworldsample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.helloworldsample.repository.PayoutRepository;

@Controller
public class MiniCactpotController {
	private final PayoutRepository repository;

	@Autowired
	public MiniCactpotController(PayoutRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/")
	public String home(@RequestParam(name="name", required=false,defaultValue="World") String name, Model model) {
		var payoutList = repository.findAll();

		for (var payout : payoutList) {
			System.out.println(String.format("%d : %d : %d", payout.getId(), payout.getSum(), payout.getMgp()));
		}

		model.addAttribute("payoutList", payoutList);

		System.out.println(model.toString());

		return "home";
	}
}
