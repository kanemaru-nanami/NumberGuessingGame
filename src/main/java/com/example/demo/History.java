package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor  // 全フィールドへ値をセットするコンストラクタ
@Getter
public class History {
	private int seq;
	private int yourAnswer;
	private String result;
}
