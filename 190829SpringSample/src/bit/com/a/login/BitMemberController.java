package bit.com.a.login;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bit.com.a.model.MemberDto;
import bit.com.a.service.BitMemberService;

@Controller
public class BitMemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(BitMemberController.class);
	
	// private 안붙여도 됨
	@Autowired private BitMemberService bitMemberService;

	@RequestMapping(value="login.do")
	public String login() {
		
		logger.info("BitMemberController login() " + new Date());
		// 타일스로 가게 되는 것임 --> layouts을 보고 들어옴
		return "login.tiles";
	}
	
	@RequestMapping(value="account.do")
	public String account() {
		logger.info("BitMemberController account() " + new Date());
		return "memberadd.tiles";
	}
	
	@ResponseBody
	@RequestMapping(value="getId.do", method=RequestMethod.POST)
	public String getId(String id) throws Exception{
		logger.info("BitMemberController getId() " + new Date());
		
		boolean a = bitMemberService.idCheck(id);
		
		String msg = "";
		if(a) {
			return msg = "yes";
		}else {
			return msg = "no";
		}
	}
	
	@RequestMapping(value = "regiAf.do", method=RequestMethod.POST)
	public String regiAf(MemberDto mem) throws Exception{
		logger.info("BitMemberController regiAf() " + new Date());
		
		logger.info("mem : " +mem.toString());
		
		boolean b = bitMemberService.addmember(mem);
		if(b) {
			return "redirect:/login.do";
		}else {
			return "redirect:/account.do";
		}
	}
	
	@RequestMapping(value = "loginAf.do", method=RequestMethod.POST)
	public String loginAf(MemberDto dto, HttpServletRequest req) throws Exception {
		logger.info("BitMemberController loginAf() " + new Date());
		
		MemberDto mem = bitMemberService.logincheck(dto);
	//	logger.info("a:"+a);
		if(mem != null) {
			logger.info("BitMemberController loginAf() " + new Date());
			logger.info("로그인 성공~");
			System.out.println("dto tosring : " + dto.toString());
			// 세션 만들기
			HttpSession session = req.getSession();
			//초 단위
			session.setMaxInactiveInterval(30 * 60 * 60);
			session.setAttribute("login", mem);
		//	System.out.println("dto auth : " + mem.getAuth());
			return "redirect:/bbslist.do";
		}else {
			logger.info("BitMemberController loginAf() " + new Date());
			return "redirect:/login.do";
		}
	}
	
	@RequestMapping(value = "logout.do", method= {RequestMethod.POST, RequestMethod.GET})
	public String logout(HttpServletRequest req) {
		logger.info("BitMemberController loginAf() " + new Date());
		
		// 세션 파괴
		HttpSession session = req.getSession();
		session.invalidate();
		return "redirect:/login.do";
	}
	
	@RequestMapping(value = "chatting.do", method= {RequestMethod.POST, RequestMethod.GET})
	public String chatting() {
		return "chatting.tiles";
	}
	
}