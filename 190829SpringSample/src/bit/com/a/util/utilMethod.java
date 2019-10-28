package bit.com.a.util;

public class utilMethod {

	// 제목이 너무 길면 제목 + 떙떙떙 으로 처리하는 함수 예) 김사장님과 저녁식사 -> 김사장님과 저....
	public static String dot4(String msg){
		String str = "";
		if(msg.length() >= 5){
			str = msg.substring(0, 4);
			str = str + "···";
		}else {
			str = msg.trim();
		}
		return str;
	}
	
	// NVL함수 (오류를 검사할 수 있는 함수 == 유틸리티 함수 클래스 만들어서 static으로 호출 하면 ㄱㅊ)
	public static boolean nvl(String msg){
		return msg == null || msg.trim().equals("")?true:false;
	}
}
