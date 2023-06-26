package com.example.minicactpot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.minicactpot.service.MiniCactpotService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MiniCactpotController {
	private final MiniCactpotService miniCactpotService;

	@GetMapping("/")
	public String home(@RequestParam(name="name", required=false,defaultValue="World") String name, Model model) {
		// ===== 報酬一覧の表示 =====
		var divisionPayoutList = miniCactpotService.getDivisionPayoutList();
		model.addAttribute("payoutList1", divisionPayoutList.get(0));
		model.addAttribute("payoutList2", divisionPayoutList.get(1));

		// ===== 標準期待値の計算 =====
		var standardExpectedValue = miniCactpotService.getStandardExpectedValue();

		// TODO : 標準期待値の画面への出力
		System.out.println("標準期待値：" + standardExpectedValue);

		return "home";
	}
}
