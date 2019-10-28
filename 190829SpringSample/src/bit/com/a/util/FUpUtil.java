package bit.com.a.util;

import java.util.Date;

public class FUpUtil {

	// f = myfile.txt => f.indexOf('.') -> 6
	// f.substring( 6 ) -> .txt 시작위치임(6번째부터)
	
	// 원래 파일명 불러오기 f.substring( 0, 6 ) -> myfile	(0번째부터 6번 전까지)
	
	// myfile.txt ---> 123412521.txt
	public static String convertFileName(String f) {
		String filename = "";
		String fpost = "";
		
		if(f.indexOf('.') >= 0) {
			fpost = f.substring(f.indexOf('.'));	//.txt
			filename = new Date().getTime() + fpost;	// 453453.txt
			
		}else { // 확장자 명이 없을 때
			filename = new Date().getTime() + ".back";
		}
		return filename;
	}
}
