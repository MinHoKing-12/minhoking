package cyber.exam.a.login;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cyber.exam.a.model.CyberMemberDto;
import cyber.exam.a.service.cyberMemberService;

@Controller
public class CyberMemberController {

	private static final Logger logger = LoggerFactory.getLogger(CyberMemberController.class);
	
	@Autowired
	private cyberMemberService memberservice;
	
	@RequestMapping(value="login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String login() {
		logger.info("CyberMemberController login() " + new Date());
		
		return "login.tiles";
	}
	
	@RequestMapping(value="loginCheck.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String loginCheck(CyberMemberDto dto, HttpServletRequest req) {
		logger.info("CyberMemberController loginCheck() " + new Date());
		
		CyberMemberDto mem = memberservice.LoginCheck(dto);
		System.out.println("mem : " + mem.toString());
		
		if(mem != null) {
			HttpSession session = req.getSession();
			session.setMaxInactiveInterval(30 * 60 * 60);
			session.setAttribute("login", mem);
			return "redirect:/examlist.do";
		}else {
			return "redirect:/login.do";
		}
	}
	
	@RequestMapping(value="addMember.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String addMember() {
		logger.info("CyberMemberController addMember() " + new Date());
		return "addMember.tiles";
	}
	
	@RequestMapping(value="addMemberAf.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String addMemberAf(CyberMemberDto dto) {
		logger.info("CyberMemberController addMemberAf() " + new Date());
		
		boolean a = memberservice.addMember(dto);
		if(a) {
			logger.info("CyberMemberController addMemberAf suc() " + new Date());
			return "login.tiles";
		}else {
			logger.info("CyberMemberController addMemberAf fail() " + new Date());
		}
		return "login.tiles";
	}
	
	@ResponseBody
	@RequestMapping(value="getId.do", method=RequestMethod.POST)
	public String getId(String id) throws Exception{
		logger.info("CyberMemberController getId() " + new Date());
		
		System.out.println("id : " +id);
		boolean a = memberservice.checkId(id);
		
		String msg = "";
		
		if(a) {
			return msg = "no";
		}else {
			return msg = "yes";
		}
	}
	
	@RequestMapping(value = "logout.do", method= {RequestMethod.POST, RequestMethod.GET})
	public String logout(HttpServletRequest req) {
		logger.info("CyberMemberController logout() " + new Date());
		
		// 세션 파괴
		HttpSession session = req.getSession();
		session.invalidate();
		return "redirect:/login.do";
	}
	
}
