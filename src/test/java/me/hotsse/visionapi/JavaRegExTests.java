package me.hotsse.visionapi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class JavaRegExTests {

	@Test
	void contextLoads() {
		
		final String originText = "가까운행복을만나다GSZS0283596741023062895GS25대림밸리소홈점김승욱서울영등포구대림동1101-7번지2020/06/11박*욱NO:14003※정부방침에의해교환/환불은반드시영수증을지참하셔야하며,카드결제는30일(07월11일)이내카드와영수종지참시가능합니다미닛오렌지350김치찌개(컵밥)톡톡김지알밥합계수량/금액판촉/팝할인1,950증정품7,0008,950-450과세매출부가세계7,7287728,5008,500합신용카드*-신용카드전표(고객용)카드번호5365-10**--3707사용금액8,500원할부0(매입사:국민카드)송인번호22045572--20/06/1120:13:19----**2020년GS25_30주년진심으로고맙습니다.11244";
		
		System.out.println("Google Vision API");
		System.out.println("---");
		
		System.out.println("OCR 분석 텍스트 원본");
		System.out.println(originText);
		
		System.out.println("---");
		
		// 신용카드 관련 정보 획득
		List<String> cardInfoText = this.fetchStringWithRegEx(originText, "[0-9||*]{4}-?[0-9||*]{4}-?[0-9||*]{4}-?[0-9||*]{4}");
		
		System.out.println("신용카드 관련 수집정보");
		for(String text : cardInfoText) {
			System.out.println(text);
		}
		
		System.out.println("---");
		
		// 금액 관련 정보 획득
		List<String> cashInfoText = this.fetchStringWithRegEx(originText, "[0-9||,]{3,}원");
		
		System.out.println("금액 관련 수집정보");
		for(String text : cashInfoText) {
			System.out.println(text);
		}
		
	}
	
	private List<String> fetchStringWithRegEx(String originText, String patternText) {
		
		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile(patternText);
		Matcher matcher = pattern.matcher(originText);
		
		while(matcher.find()) {
			result.add(matcher.group(0));
			if(matcher.group(0) == null) break;
		}
		
		return result;
	}

}
