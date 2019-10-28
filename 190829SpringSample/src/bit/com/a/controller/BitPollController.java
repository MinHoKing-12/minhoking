package bit.com.a.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bit.com.a.model.MemberDto;
import bit.com.a.model.PollBean;
import bit.com.a.model.PollDto;
import bit.com.a.model.PollSubDto;
import bit.com.a.model.Voter;
import bit.com.a.service.BitPollService;

@Controller
public class BitPollController {

	
	// 투표에는 서비스가 중요한 일을 함
	@Autowired
	BitPollService pollservice;
	
	@RequestMapping(value ="polllist.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String polllist(Model model, HttpServletRequest req) {
		
		model.addAttribute("doc_title", "투표 목록");
		
		String id = ((MemberDto)req.getSession().getAttribute("login")).getId();
		
		// list 투표를 했는지 안했는지 체크!!
		List<PollDto> list = pollservice.getPolllist(id);
		model.addAttribute("plists", list);
		
		return "polllist.tiles";
	}
	
	@RequestMapping(value ="pollmake.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String pollmake(Model model) throws Exception {
		model.addAttribute("doc_title", "투표 만들기");
		
		return "pollmake.tiles";
	}
	
	@RequestMapping(value ="pollmakeAf.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String pollmakeAf(PollBean pbean) throws Exception {
		
	//	System.out.println(pbean.toString());
		
		pollservice.makePoll(pbean);
		
		return "redirect:/polllist.do";
	}
	
	@RequestMapping(value ="polldetail.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String polldetail(PollDto poll, Model model) {
		model.addAttribute("doc_title", "투표 내용");
		
		PollDto dto = pollservice.getPoll(poll);	// 질문 포장
		List<PollSubDto> list = pollservice.getPollSubList(poll);	// 보기 포장
		
		System.out.println("질문 : " + dto.getQuestion());
		for (PollSubDto p : list) {
			System.out.println(p);
		}
		
		model.addAttribute("poll", dto);
		model.addAttribute("pollsublist", list);
		
		return "polldetail.tiles";
	}
	
	@RequestMapping(value ="polling.do", method = {RequestMethod.POST})
	public String polling(Voter voter) {
		
		pollservice.polling(voter);
		
		return "redirect:/polllist.do";
	}
	
	@RequestMapping(value ="pollresult.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String pollresult(PollDto poll, Model model) {
		model.addAttribute("doc_title", "투표 결과");
		
		PollDto dto = pollservice.getPoll(poll);
		List<PollSubDto> list = pollservice.getPollSubList(poll);
		
		model.addAttribute("poll", dto);
		model.addAttribute("pollsublist", list);
		
		return "pollresult.tiles";
		
	}
	
}	
