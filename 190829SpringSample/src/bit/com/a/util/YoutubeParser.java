package bit.com.a.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import bit.com.a.model.Youtube;

// 파싱을 위한 프로그램 : 파서
@Component		// == static
public class YoutubeParser {
	// youtube 검색 시에 나오는 url 복사
	String urls = "https://www.youtube.com/results?search_query=";
	ArrayList<String> htmls = new ArrayList<String>();
	
	public ArrayList<String> search(String s){
		
		htmls.clear();
		
		BufferedReader br = null;
		
			// net 소속임
			String ss;
			try {
				ss = URLEncoder.encode(s, "utf-8");

			URL url = new URL(urls + ss);
			
			br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));

			String msg = "";
			// 데이터가 있는 부분에 끝까지
			while((msg = br.readLine()) != null) {
				
				if(msg.trim().contains("class=\"yt-uix-tile-link yt-ui-ellipsis yt-ui-ellipsis-2 yt-uix-sessionlink")) {
					htmls.add(msg.trim());
				}
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return htmls;
	}
	
	// &를 제거	/watch?v=백종원&asdfaiodsfaiodsjfoiwfej
	public String toUrl(String msg) {
		String tt = "";
		if(msg.indexOf("&") == -1) {		// 없으면
			tt = msg;
		}else {	// 있으면
			tt = msg.substring(0, msg.indexOf("&"));
		}
		return tt;
	}
	
	public ArrayList<Youtube> getTitles(String key){
		
		ArrayList<Youtube> af = new ArrayList<Youtube>();
		int i = 0;
		ArrayList<String> asd = search(key);
		
		for(int j = 0; j< asd.size(); j++) {
			
			String fu[] = asd.get(j).split("\"");
			String url;
			try {
				url = URLDecoder.decode(fu[5], "EUC-KR");
				String title = URLDecoder.decode(fu[11], "EUC-KR");
				Youtube f = new Youtube(title, toUrl(url), "");
				af.add(f);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		return af;
	}
}
