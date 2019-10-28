package bit.com.a.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bit.com.a.model.MemberDto;
import bit.com.a.model.YesMember;
import bit.com.a.model.Youtube;
import bit.com.a.model.YoutubeSave;
import bit.com.a.service.YoutubeService;
import bit.com.a.util.YoutubeParser;

@Controller
public class BitYoutubeController {

	@Autowired
	private YoutubeParser youtubeParser;
	
	@Autowired
	private YoutubeService youtubeService;
	
	@RequestMapping(value ="yutube.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String yutube(String s_keyword, Model model) throws Exception {
			model.addAttribute("doc_title", "유튜브");
		
		if(s_keyword != null && !s_keyword.equals("")) {
			
			ArrayList<Youtube> getTiles = youtubeParser.getTitles(s_keyword);
			model.addAttribute("yulist", getTiles);
			model.addAttribute("s_keyword", s_keyword);
		}
		return "yutube.tiles";
	}
	
	@ResponseBody	// ajax 어노테이션
	@RequestMapping(value ="youtubesave.do", method = {RequestMethod.GET, RequestMethod.POST})
	// 예스맨을 활용
	public YoutubeSave youtubesave(YoutubeSave you) {
		
		// DB 추가
		youtubeService.writeYoutube(you);
		
		YoutubeSave me = youtubeService.getYoutube(you);
		// SUC/FAIL
		
		return me;
	}
	
	@RequestMapping(value ="yutubelist.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String youtubelist(HttpServletRequest req, Model model) {
		
		model.addAttribute("doc_title", "Youtube 저장 동영상 목록");
		String id = ((MemberDto)req.getSession().getAttribute("login")).getId();
		
		YoutubeSave you = new YoutubeSave();
		you.setId(id);
		
		List<YoutubeSave> getTitles = youtubeService.getYoutubeList(you);
		
		model.addAttribute("youlist", getTitles);
		
		return "youtubelist.tiles";
	}
}
