package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller  // リクエストを受け取るクラス
public class GameController {


	@Autowired  // クラスの呼び出しや結びつけ
	HttpSession session;

	@GetMapping("/")
	public String index() {
		session.invalidate();  // session初期化

		Random rnd = new Random();  // 正解(answer)を作成
		int answer = rnd.nextInt(100)+1;

		session.setAttribute("answer", answer);  // answerをsessionに格納

		System.out.println("answer=" + answer);

		// コンソールに正解を出力する
		return "game";
	}


	@PostMapping("/challenge")
	public ModelAndView

	// 回答(number)パラメーター
	challenge ( @RequestParam ("number") int number, ModelAndView mv) {

		// 戻り値がObject型なのでInteger型にキャスト
		int answer = (Integer) session.getAttribute("answer");  // sessionからanswerを取得

		// ユーザーの回答履歴を取得
		@SuppressWarnings("unchecked")
		List<History> histories = (List<History>) session.getAttribute("histories");

		if(histories == null) {
			histories = new ArrayList<>();  // Listはnullダメなので、Arrayに変換
			session.setAttribute("histories", histories);
		}

		// 判定→回答履歴追加
		if(answer < number) {
			histories.add(new History(histories.size() + 1,number,"もっと小さいです"));
		}else if(answer == number) {
			histories.add(new History(histories.size() + 1,number,"正解です！"));
		}else {
			histories.add(new History(histories.size() + 1,number,"もっと大きいです"));
		}

		mv.setViewName("game");

		mv.addObject("histories", histories);
		return mv;
	}

	// 「/fortune」ときたら動くメソッドにしたい
	@RequestMapping("/fortune")
	public String start() {
		// 0.0～1.0の値をランダムに返す
		double fn =Math.random();

		// fnが0.7以上の値ならば
		if(fn >= 0.7) {
			return "greatFortune.html";
		// fnが0.4以上の値ならば
		}else if(fn >= 0.4) {
			return "middleFortune.html";
		// fnが0.1以上の値ならば
		}else if(fn>=0.1) {
			return "smallFortune.html";
		// それ以下（それ以外）
		}else {
			return "misFortune.html";
		}
	}

}
