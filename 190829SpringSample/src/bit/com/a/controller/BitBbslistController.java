package bit.com.a.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bit.com.a.model.BbsParam;
import bit.com.a.model.BbslistDto;

@Controller
public class BitBbslistController {

	private static final Logger logger = LoggerFactory.getLogger(BitBbslistController.class);
	
	@Autowired
	private bit.com.a.service.BitBbslistServiece BitBbslistServiece;
	
	@RequestMapping(value="bbslist.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String allbbslist(Model model, BbsParam param) throws Exception{
		logger.info("HelloController bbslist()" + new Date());
		
		model.addAttribute("doc_title", "글목록");
		
		// paging 처리
		int sn = param.getPageNumber();		// 현재 페이지 번호 0 1 2
		int start = sn * param.getRecordCountPerPage() + 1;	// 1  11
		int end = (sn + 1) * param.getRecordCountPerPage(); // 10 20
		
		param.setStart(start);
		param.setEnd(end);
		System.out.println("검색 카테고리 : " + param.getS_category());
		System.out.println("검색 키워드 : " + param.getS_keyword());
		
		List<BbslistDto> list = BitBbslistServiece.getBbsList(param);
		// 글의 총 수
		int totalRecordCount = BitBbslistServiece.getBbsCount(param);
		
		model.addAttribute("list", list);
		
		model.addAttribute("pageNumber", sn);
		model.addAttribute("pageCountPerScreen", 10);
		model.addAttribute("recordCountPerPage", param.getRecordCountPerPage());
		model.addAttribute("totalRecordCount", totalRecordCount);
		model.addAttribute("s_keyword", param.getS_keyword());
		model.addAttribute("s_category", param.getS_category());
		return "bbslist.tiles";
	}
	
	@RequestMapping(value="bbswrite.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String bbswrite() {
		logger.info("HelloController bbswrite()" + new Date());
		return "bbswrite.tiles";
	}
	
	@RequestMapping(value="bbswriteAf.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String bbswriteAf(BbslistDto dto) throws Exception{
		logger.info("HelloController bbswriteAf()" + new Date());
		boolean a = BitBbslistServiece.BbsWrite(dto);
		if(a) {
			return "redirect:/bbslist.do";
		}else {
			return "redirect:/bbslist.do";
		}
	}
	@RequestMapping(value="bbsdetail.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String bbsdetail(@RequestParam("seq")int seq, Model model, BbslistDto dto) throws Exception {
		logger.info("HelloController bbsdetail()" + new Date());
		
		boolean a = BitBbslistServiece.bbsReadCount(seq);
		if(a) {
		dto = BitBbslistServiece.bbsDetail(seq);
		
		model.addAttribute("dto", dto);
		}
		return "bbsdetail.tiles";
	}
	
	@RequestMapping(value="bbsupdate.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String bbsupdate(@RequestParam("seq")int seq, Model model, BbslistDto dto) throws Exception{
		logger.info("HelloController bbsupdate()" + new Date());
		dto = BitBbslistServiece.bbsDetail(seq);
		
		model.addAttribute("dto", dto);
		return "bbsupdate.tiles";
	}
	
	@RequestMapping(value="bbsupdateAf.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String bbsupdateAf(BbslistDto dto, Model model) throws Exception {
		logger.info("HelloController bbsupdate()" + new Date());
		
		boolean a = BitBbslistServiece.bbsUpdate(dto);
		if(a) {
			return "redirect:/bbslist.do";
		}else {
			return "redirect:/bbslist.do";
		}
	}
	@RequestMapping(value="bbsdelete.do", method= {RequestMethod.GET, RequestMethod.POST})
	public String bbsdelete(@RequestParam("seq")int seq) throws Exception {
		logger.info("HelloController bbsdelete()" + new Date());
		
		boolean a = BitBbslistServiece.bbsDelete(seq);
		if(a) {
			return "redirect:/bbslist.do";
		}else {
			return "redirect:/bbslist.do";
		}
	}
	@RequestMapping(value="replayAf.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String replayAf(BbslistDto dto) throws Exception {
	
		logger.info("HelloController replayAf()" + new Date());
		boolean a = BitBbslistServiece.answerWrite(dto);
		if(a) {
			return "redirect:/bbslist.do";
		}else {
			return "redirect:/bbslist.do";
		}
	}
}
