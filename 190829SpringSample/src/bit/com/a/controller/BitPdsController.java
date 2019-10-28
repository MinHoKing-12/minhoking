package bit.com.a.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bit.com.a.model.BbsParam;
import bit.com.a.model.PdsDto;
import bit.com.a.service.BitPdsService;
import bit.com.a.util.FUpUtil;

@Controller
public class BitPdsController {

	@Autowired
	BitPdsService pdsservice;
	
	@RequestMapping(value = "pdslist.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String pdslist(Model model, BbsParam param) throws Exception{
		model.addAttribute("doc_title", "자료실 목록");
		
		// paging 처리
		int sn = param.getPageNumber();
		int start = sn * param.getRecordCountPerPage();
		int end = (sn + 1) * param.getRecordCountPerPage();
		
		param.setStart(start);
		param.setEnd(end);
		System.out.println("검색 카테고리 : " + param.getS_category());
		System.out.println("검색 키워드 : " + param.getS_keyword());
		
		List<PdsDto> list = pdsservice.getPdsList(param);
		
		int totalRecordCount = pdsservice.getPdsCount(param);
		
		model.addAttribute("pdslist", list);
		model.addAttribute("pageNumber", sn);
		model.addAttribute("pageCountPerScreen", 10);
		model.addAttribute("recordCountPerPage", param.getRecordCountPerPage());
		model.addAttribute("totalRecordCount", totalRecordCount);
		model.addAttribute("s_keyword", param.getS_keyword());
		model.addAttribute("s_category", param.getS_category());
		return "pdslist.tiles";	
	}
	
	@RequestMapping(value = "pdswrite.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String pdswrite(Model model) {
		model.addAttribute("doc_title", "자료 올리기");
		
		return "pdswrite.tiles";
	}
	
	// required : 캐시에 저장 할 것이냐?
	@RequestMapping(value = "pdsupload.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String pdsupload(PdsDto pdsdto,
				@RequestParam(value = "fileload", required = false)MultipartFile fileload,
				HttpServletRequest req) {
		
		// DB <- 파일명을 변경 abc.txt -> 123456.txt(time) -> abc, 123456(저장)
		// filename을 취득
		String filename = fileload.getOriginalFilename();	// 실제 파일 이름 짚어넣음
		System.out.println("original filename : " + filename);
		pdsdto.setFilename(filename);
		pdsdto.setOldfilename(filename);
		// upload 경로 설정
		// tomcat 경로
		String fupload = req.getServletContext().getRealPath("upload");
		System.out.println("fupload : " + fupload);		// 업로드 위치 경로 확인 꼭하기
		
		// folder 경로
		// String fupload = "e:\\tmp";
		
		// file명 변경
		String f = pdsdto.getFilename();
		String newfilename = FUpUtil.convertFileName(f);
		
		// 업로드
		pdsdto.setFilename(newfilename);
		
		File file = new File(fupload + "/" + newfilename);
		
		try {
			// 실제 파일 업로드 되는 부분
			FileUtils.writeByteArrayToFile(file, fileload.getBytes());
			
			// db 저장
			pdsservice.uploadPds(pdsdto);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/pdslist.do";
	}
	@RequestMapping(value = "fileDownload.do", method = {RequestMethod.GET,RequestMethod.POST})
	// 자동적으로 넘어옴
	public String fileDownload(String filename, int seq, HttpServletRequest req, Model model) {
		
		// download 경로
		
		// tomcat 
		String fupload = req.getServletContext().getRealPath("/upload");
		// 폴더
		// String fupload = "e:\\tmp";
		
		File downloadFile = new File(fupload + "/" + filename);
		
		
		model.addAttribute("downloadFile", downloadFile);
		model.addAttribute("seq", seq);
		
		
		// util에 있는 downloadView로 간다.
		return "downloadView";
	}
	
	@RequestMapping(value = "pdsdelete.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String pdsdelete(@RequestParam(value="seq")int seq) {
		
		boolean a = pdsservice.PdsDelete(seq);
		if(a) {
		return "redirect:/pdslist.do";
			}
		
		return "redirect:/pdslist.do";
	}
	@RequestMapping(value = "pdsdetail.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String pdsdetail(@RequestParam(value="seq")int seq, Model model) throws Exception{
		
		model.addAttribute("doc_title", "상세");
		
		PdsDto pdsdto = pdsservice.PdsDetail(seq);
		
		model.addAttribute("pdsdto", pdsdto);
		
		boolean a = pdsservice.PdsReadCount(seq);
		if(a){
			return "pdsdetail.tiles";
		}else {
			return "pdsdetail.tiles";
		}
	}
	@RequestMapping(value = "pdsupdate.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String pdsupdate(@RequestParam(value="_seq")int seq, Model model) throws Exception {
		PdsDto pdsdto = pdsservice.PdsDetail(seq);
		
		model.addAttribute("pdsdto", pdsdto);
		
		return "pdsupdate.tiles";
	}
	
	@RequestMapping(value="pdsuploadAf.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String pdsfileupload(PdsDto pdsdto,
			@RequestParam(value = "fileload", required = false)MultipartFile fileload,
			HttpServletRequest req) {
	System.out.println("들어가기전 파일 수정 dto : " +pdsdto.toString());
	// DB <- 파일명을 변경 abc.txt -> 123456.txt(time) -> abc, 123456(저장)
	// filename을 취득
	String filename = fileload.getOriginalFilename();	// 실제 파일 이름 짚어넣음
	pdsdto.setFilename(filename);
	
	// upload 경로 설정
	// tomcat 경로
	String fupload = req.getServletContext().getRealPath("upload");
	System.out.println("fupload : " + fupload);		// 업로드 위치 경로 확인 꼭하기
	
	// folder 경로
	// String fupload = "e:\\tmp";
	
	// file명 변경
	String f = pdsdto.getFilename();
	pdsdto.setOldfilename(filename);
	String newfilename = FUpUtil.convertFileName(f);
	
	// 업로드
	pdsdto.setFilename(newfilename);
	
	File file = new File(fupload + "/" + newfilename);
	
	try {
		// 실제 파일 업로드 되는 부분
		FileUtils.writeByteArrayToFile(file, fileload.getBytes());
		
		// db 저장
		pdsservice.PdsUpdate(pdsdto);
		} catch (IOException e) {
		e.printStackTrace();
	}
	
	return "redirect:/pdslist.do";
}
}
