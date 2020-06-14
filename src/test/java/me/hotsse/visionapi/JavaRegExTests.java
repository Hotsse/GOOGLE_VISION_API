package me.hotsse.visionapi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class JavaRegExTests {

	@Test
	void contextLoads() {
		
		final String originText = "가맵접멸,가맹접주소가싫제와다를결우신고안내(포살금10만원지급)매전표사분을협부하여무편접수자세한만내:www.crefia.or.kr/02-2011-0777КСРNHN한국사미버결제(주)IC신용승인TID:10033670732020/06/1320:57:04536510******0311전표No:200613703164카카오체크.전자서명전표일시불KB국민35.000원세계승인번호:21045933금35.000원맹점명:어사출또구로점가맹점N.:00096745758205-17-70185호:02-866-44521물구로구디지털로34길55B139호(구로동,코오뽕싸이언스밸리2차)*감사합니다*(고객용)";
		
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
