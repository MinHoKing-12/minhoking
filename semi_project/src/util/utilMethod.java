package util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;

public class utilMethod {
	
	// 날짜용 함수
	public static String dateEdit(String strDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String str = "";
		long min = 0;
		long hour = 0;
		Date w_date = new Date();
		
		try{
			w_date = sdf.parse(strDate);
			Date today = new Date();
			long diff = today.getTime() - w_date.getTime();
			min = diff / (1000 * 60);
			hour = diff / (1000 * 60 * 60);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if(min < 60) {
			str = min + "분 전";
		}else if (min >= 60 && hour < 24) {
			str = hour + "시간 전";
		}else if (hour >= 24) {
			str = new SimpleDateFormat("yyyy-MM-dd").format(w_date);
		}
		
		return str;
	}
	
	// 날짜용 함수 : 숫자 2글자로 만들기
		public static String two(String str) {
			String two = "";
			
			if(str.length() == 1) {
				two = "0" + str;
			}else {
				two = str;
			}
			return two;
		}
	
	// 파일업로드 함수
	public static String processUploadFile(FileItem fileItem, String dir) throws IOException {
		String filename = fileItem.getName();	// 경로 + 파일명
		long sizeInBytes = fileItem.getSize();	// 파일의 크기
		
		//파일이 정상적인 파일인지 검사
		if(sizeInBytes > 0){	// d:\\tmp\\abc.txt		d:/tmp/abc.txt
			
			int idx = filename.lastIndexOf("\\");
			if(idx == -1){
				idx = filename.lastIndexOf("/");
			}
			
			filename = filename.substring( (idx + 1) );
			// 파일 포인터 설정
			File uploadFile = new File(dir, filename);
			try{
			fileItem.write(uploadFile);	// 실제 파일을 업로드 하는 부분
			}catch(Exception e){}
		}
		return filename;	//파일 이름 확인용
	}
	
	// 쪽지함 리스트 함수
	public static String msgContent(String text) {
		if(text.length() > 20) {
			text = text.substring(0, 20) + "...";
		}
		
		return text;
	}
	
	   // 돈 쉼표찍고 원까지 붙여주는 유틸함수
	   public static String PriceConvert(int price) {
		   DecimalFormat dc = new DecimalFormat("###,###,###,###");
		   String str = dc.format(price);
		   str = str + "원";
		   return str;
	   }
	   
	   // 댓글용 유틸함수
	   public static String arrow(int depth){
			String rs = "<img src='./sellbbsimage/47.jpg' width='20px' height='20px'/>";
			String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;";	// 여백
			
			String ts = "";
			for(int i = 0; i < depth; i++){
				ts = ts + nbsp;
			}
			return depth==0?"":ts + rs; // 여백 + 이미지 생성
		}
	   
	// 제목이 너무 길면 제목 + 떙떙떙 으로 처리하는 함수 예) 김사장님과 저녁식사 -> 김사장님과 저....
	   public static String dot5(String msg){
		   	String str = "";
		   	if(msg.length() >= 5){
		   		str = msg.substring(0, 4);
		   		str = str + "···";
		   	}else {
		   		str = msg.trim();
		   	}
		   	return str;
		   } 
	   public static String dot7(String msg){
		   	String str = "";
		   	if(msg.length() >= 7){
		   		str = msg.substring(0, 6);
		   		str = str + "···";
		   	}else {
		   		str = msg.trim();
		   	}
		   	return str;
	   }	
	   public static String dot13(String msg){
		   	String str = "";
		   	if(msg.length() >= 13){
		   		str = msg.substring(0, 12);
		   		str = str + "···";
		   	}else {
		   		str = msg.trim();
		   	}
		   	return str;
	   }	
	
}
