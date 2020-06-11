package me.hotsse.visionapi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class JavaRegExTests {

	@Test
	void contextLoads() {
		
		final String originText = "키스체크KIS정보통신가맹점덤/주소가실제와다른경우신고한내(포상급10만원지음금)여산금용협회(02-2011-0777)IBK비씨제크승인TID:11625247-0814-일시불[신용승인]거래일시20/05/3120:00:08[회원용]6210-03**-****-0255금액:15,909원부가세:1,591원합계:17,500원승인번호(비씨카드BCESC)가맹점/사업자김은진(TRL:0284899?)서울특별시영등포구대림로731층(대림동)779127510C]670/218-04-77660신수폭발50,000원이하는무서명개래입니다알림EDC매출전표VANKEY:0152012720082980G8044958-0814-SR67[00021";
		
		System.out.println("---");
		
		// 신용카드 관련 정보 획득
		List<String> cardInfoText = this.fetchStringWithRegEx(originText, "[0-9||*]{4}-?[0-9||*]{4}-?[0-9||*]{4}-?[0-9||*]{4}");
		
		System.out.println("신용카드 관련 수집정보");
		for(String text : cardInfoText) {
			System.out.println(text);
		}
		
		System.out.println("---");
		
		// 금액 관련 정보 획득
		List<String> cashInfoText = this.fetchStringWithRegEx(originText, "[0-9||,]{1,}원");
		
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
