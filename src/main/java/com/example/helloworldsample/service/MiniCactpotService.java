package com.example.helloworldsample.service;

import java.util.ArrayList;
import java.util.List;

import com.example.helloworldsample.entity.PayoutEntity;

public interface MiniCactpotService {
    public List<ArrayList<PayoutEntity>> getDivisionPayoutList();

    // 標準期待値の取得
    public Integer getStandardExpectedValue();
}