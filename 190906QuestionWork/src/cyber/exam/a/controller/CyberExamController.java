package cyber.exam.a.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cyber.exam.a.model.CyberMemberDto;
import cyber.exam.a.model.ExamBean;
import cyber.exam.a.model.ExamDto;
import cyber.exam.a.model.ExamQuestionDto;
import cyber.exam.a.model.ExamReplyDto;
import cyber.exam.a.model.ReplyBean;
import cyber.exam.a.service.cyberExamService;
import cyber.exam.a.util.DateUtil;
import cyber.exam.a.util.FUpUtil;


@Controller
public class CyberExamController {

	//private static final Logger logger = LoggerFactory.getLogger(CyberExamController.class);
	
	@Autowired
	cyberExamService exam;
	
	@RequestMapping(value="examlist.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String examlist(Model model, HttpServletRequest req) {
	//	logger.info("BitMemberController examlist() " + new Date());
		
		String id = ((CyberMemberDto)req.getSession().getAttribute("login")).getId();
		List<ExamDto> list = exam.getExamlist(id);
		model.addAttribute("list", list);
		
		return "examlist.tiles";
	}
	
	@RequestMapping(value="examwrite.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String examwrite() {
		
		return "examwrite.tiles";
	}
	
	@RequestMapping(value="examwriteAf.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String examwriteAf(ExamDto dto, Model model, ExamBean bean) {
		
	//	System.out.println("bean : " + bean.toString());
		Date startDate = DateUtil.toDate(bean.getSyear(), bean.getSmonth(), bean.getSday());
		Date finishDate = DateUtil.toDate(bean.getEyear(), bean.getEmonth(), bean.getEday());
		
		dto.setStartdate(startDate);
		dto.setFinishdate(finishDate);
		
		exam.makeExam(dto);
		model.addAttribute("dto", dto);
		return "examwriteAf.tiles";
	}
	
	
	@RequestMapping(value="examSubmit.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String examwriteAfAf(ExamReplyDto dto, @ModelAttribute(value="ReplyBean") ReplyBean bean) {
	
	List<ExamReplyDto> list = bean.getReplylist();
	List<ExamQuestionDto> qlist = exam.getExamList(list.get(0).getExam_seq());
	
	for(int i = 0; i<list.size(); i++) {
		if(!list.get(i).getReply().equals(qlist.get(i).getCorrect_answer())) {
			list.get(i).setScore(0);
		}
	}
	
	for (ExamReplyDto e : list) {
		exam.ExamSubmit(e);
	}
	exam.ExamCount(dto);
	
	return "redirect:/examlist.do";
	}
	
	@ResponseBody
	@RequestMapping(value="examwriteAfAf.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String examwriteAfAf(ExamQuestionDto questionDto, 
        @RequestParam(value="fileload", required = false)MultipartFile fileload,
        HttpServletRequest req, Model model) {

		if(!fileload.isEmpty()) {
		String filename = fileload.getOriginalFilename();
		questionDto.setOriginal_filename(filename);
		
		String fupload = req.getServletContext().getRealPath("/upload");
		System.out.println("fupload : " + fupload);
		
		String newFilename = FUpUtil.convertFileName(filename);
		questionDto.setFilename(newFilename);
		File file = new File(fupload + "/" + newFilename);
		
		try {
		// 실제 파일 업로드 되는 부분
		FileUtils.writeByteArrayToFile(file, fileload.getBytes());
		   
		}catch (Exception e) {
		e.printStackTrace();
		}   
		
		}else {
		questionDto.setOriginal_filename("");
		questionDto.setFilename("");
		}
		
		System.out.println("문제 정보 : "+ questionDto.toString());
		exam.makeExamSub(questionDto);
		return "ok";
	}
	
	@RequestMapping(value="examdetail.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String examdetail(@RequestParam(value="seq")int seq , Model model) {
		
		ExamDto dto = exam.getExam(seq);
		List<ExamQuestionDto> list = exam.getExamList(seq);
		
	//	System.out.println("질문 : " + dto.getTitle());
		
		for (ExamQuestionDto e : list) {
			System.out.println(e.toString());
		}
		
		model.addAttribute("exam", dto);
		model.addAttribute("examsublist", list);
		
		return "examdetail.tiles";
		
	}
	
	@RequestMapping(value="examresult.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String examresult(@RequestParam(value="seq")int seq , Model model, ExamReplyDto rdto, 
			HttpServletRequest req) {
		
		String id = ((CyberMemberDto)req.getSession().getAttribute("login")).getId();
		
		int score = exam.ExamScore(new ExamReplyDto(seq, id));
		List<ExamQuestionDto> qlist = exam.getExamList(seq);
		List<ExamReplyDto> rlist = exam.getExamReply(new ExamReplyDto(seq, id));
		
		model.addAttribute("id", id);
		model.addAttribute("qlist", qlist);
		model.addAttribute("score", score);
		model.addAttribute("rlist", rlist);
	
		return "examresult.tiles";
	}
	@RequestMapping(value="adminResult.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String adminResult(@RequestParam(value="seq")int seq , Model model) {
		List<ExamReplyDto> list = exam.getExamTotalScore(seq);
		
		String jsonData = "[";
		
		for (ExamReplyDto p : list) {
			jsonData = jsonData + "{name:'" + p.getId() + "', y:" + p.getScore() + "},";
		}
		
		jsonData = jsonData.substring(0, jsonData.lastIndexOf(","));
		jsonData = jsonData + "]";
		model.addAttribute("list", list);
		model.addAttribute("jsonData", jsonData);
		
		return"adminresult.tiles";
	}
}
