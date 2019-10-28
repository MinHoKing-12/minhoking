package bit.com.a.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import bit.com.a.model.PdsDto;
import bit.com.a.service.BitPdsService;

public class DownloadView extends AbstractView {

	@Autowired
	BitPdsService pdsservice;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("DownloadView renderMergedOutputModel");
		
		File file = (File)model.get("downloadFile");	// == getAttribute와 동일
		int seq = (int)model.get("seq");
		response.setContentType(this.getContentType());
		response.setContentLength((int)file.length());
		// dto
		PdsDto dto = pdsservice.getOldfileName(seq);
		// IE / chrome
		String userAgent = request.getHeader("user-Agent");
		// true == IE // false == chrome
		boolean ie = userAgent.indexOf("MSIE") > -1;

		String oldfilename = dto.getOldfilename();
		System.out.println("oldfilename 후= " + oldfilename);
		// 파일이름 안깨지는 작업
		if(ie) {	
						//java.net
			oldfilename = URLEncoder.encode(oldfilename, "utf-8");
		}else {
			oldfilename = new String(oldfilename.getBytes("utf-8"), "iso-8859-1");
		}
		System.out.println("oldfilename 전= " + oldfilename);
		// download window (다운로드 창)
		response.setHeader("Content-Disposition", "attachment; filename=\"" + oldfilename + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Content-Length", "" + file.length());
		response.setHeader("Pragma", "no-cache;");	// 저장을 할 거냐 
		response.setHeader("Expires", "-1;");		// 기한 설정
		
		OutputStream out = response.getOutputStream();
		FileInputStream fi = null;
		
		fi = new FileInputStream(file);
		FileCopyUtils.copy(fi, out);
		
		// download count 증가
		boolean a = pdsservice.PdsDownCount(seq);
		if(a) {
			
		}
		if(fi != null) {
			fi.close();
		}
	}
}
