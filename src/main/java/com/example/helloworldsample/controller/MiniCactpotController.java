package com.example.helloworldsample.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.helloworldsample.repository.PayoutRepository;
import com.example.helloworldsample.service.MiniCactpotService;

import lombok.RequiredArgsConstructor;

import com.example.helloworldsample.entity.PayoutEntity;
import org.springframework.data.domain.Sort;

@Controller
@RequiredArgsConstructor
public class MiniCactpotController {
	private final MiniCactpotService miniCactpotService;

	private final PayoutRepository repository;

	// 1 ～ 9のリスト作成 todo:service化
	private static final List<Integer> NUMBER_LIST;
	static {
		var list = new ArrayList<Integer>();
		for (int i = 1; i <= 9; i++) {
			list.add(i);
		}
		NUMBER_LIST = Collections.unmodifiableList(list);
	}

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
