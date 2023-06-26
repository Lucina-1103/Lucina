package com.example.helloworldsample.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.helloworldsample.entity.PayoutEntity;
import com.example.helloworldsample.repository.PayoutRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MiniCactpotServiceImpl implements MiniCactpotService {
    private final PayoutRepository payoutRepository;

    private final Integer DIVISION_LIMIT = 10; // 一覧を分割する際の境界値
    
	private static final List<Integer> NUMBER_LIST; // 1 ～ 9のリスト作成
	static {
		var list = new ArrayList<Integer>();
		for (int i = 1; i <= 9; i++) {
			list.add(i);
		}
		NUMBER_LIST = Collections.unmodifiableList(list);
	}

    /**
     * 分割されたPayoutListの取得
     */
    @Override
    public List<ArrayList<PayoutEntity>> getDivisionPayoutList() {
        // idでソートされたpayoutListを取得
        var payoutList = payoutRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        // payoutListを２つに分割
   		var payoutList1 = new ArrayList<PayoutEntity>();
		var payoutList2 = new ArrayList<PayoutEntity>();
		for (var payout : payoutList) {
			if (payoutList1.size() < DIVISION_LIMIT) {
				payoutList1.add(payout);
			} else {
				payoutList2.add(payout);
			}
		}

        var divisionPayoutList = new ArrayList<ArrayList<PayoutEntity>>();
        divisionPayoutList.add(payoutList1);
        divisionPayoutList.add(payoutList2);

        return divisionPayoutList;
    }

    /**
     * 標準期待値の取得
     */
    @Override
    public Integer getStandardExpectedValue() {
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
        var payoutList = payoutRepository.findAll();
        var payoutSum = 0;
		for (var list : selectedNumberList) {
			var sum = list.get(0) + list.get(1) + list.get(2);
			var mgp = 0;
			
			for (var payoutEntity : payoutList) {
				if (payoutEntity.getSum() == sum) {
					mgp = payoutEntity.getMgp();
					break;
				}
			}
			payoutSum = payoutSum + mgp;
		}

		// 払出合計額の平均値（期待値）を算出
		var getStandardExpectedValue = 0;
		getStandardExpectedValue = payoutSum / selectedNumberList.size();

        return getStandardExpectedValue;
    }
}