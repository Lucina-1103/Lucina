package com.example.helloworldsample.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.helloworldsample.repository.PayoutRepository;
import com.example.helloworldsample.entity.PayoutEntity;
import org.springframework.data.domain.Sort;

@Controller
public class MiniCactpotController {
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

	@Autowired
	public MiniCactpotController(PayoutRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/")
	public String home(@RequestParam(name="name", required=false,defaultValue="World") String name, Model model) {
		// ===== 報酬一覧の表示 ===== todo:service化
		final Integer DIVISION_LIMIT = 10;

		// idでソートされたpayoutListを取得
		var payoutList = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));

		// payoutListを２つに分割
		var payoutList1 = new ArrayList<PayoutEntity>();
		var payoutList2 = new ArrayList<PayoutEntity>();

		for (var payout : payoutList) {
			if (payoutList1.size() < DIVISION_LIMIT) {
				payoutList1.add(payout);
			} else {
				payoutList2.add(payout);
			}
			// System.out.println(String.format("%d : %d : %d", payout.getId(), payout.getSum(), payout.getMgp()));
		}

		// モデルへセット
		model.addAttribute("payoutList1", payoutList1);
		model.addAttribute("payoutList2", payoutList2);

		// ===== 標準の期待値計算 ===== todo:service化 リファクタ
		// 組み合わせリストの作成
		var selectedNumberList = new ArrayList<ArrayList<Integer>>();
		for (var number1 : NUMBER_LIST) {
			for (var number2 : NUMBER_LIST) {
				for (var number3 : NUMBER_LIST) {
					// 無効なリストの除去
					// 1,1,x 2,2,x など同じ数字の組み合わせを除去
					if (number1 == number2 || number1 == number3 || number2 == number3) break;

					// 1,2,3 と 3,2,1 などソートしたときに同じになる組み合わせを除去
					var list = new ArrayList<Integer>();
					list.add(number1);
					list.add(number2);
					list.add(number3);
					Collections.sort(list);
					if(selectedNumberList.contains(list)) {
						break;
					}

					selectedNumberList.add(list);
				}
			}
		}

		// 払い出し額合計を算出
		var payoutSum = 0;
		for (var list : selectedNumberList) {
			var sum = list.get(0) + list.get(1) + list.get(2);
			var mgp = 0;
			
			for (var payoutEntity : payoutList) {
				if (payoutEntity.getSum() == sum) {
					System.out.print("" + list.get(0) + ":" + list.get(1) + ":" + list.get(2) + ":" + sum  + ":" + payoutEntity.getSum());

					mgp = payoutEntity.getMgp();
					System.out.println(":" + mgp);
					break;
				}
			}
			payoutSum = payoutSum + mgp;
			System.out.println("合計値:" + payoutSum);
		}

		// 払出合計額の平均値（期待値）を算出
		var result = 0;
		result = payoutSum / selectedNumberList.size();

		System.out.println(selectedNumberList);
		System.out.print("期待値？：");
		System.out.println(result);

		return "home";
	}
}
