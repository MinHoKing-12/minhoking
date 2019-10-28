package pds;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// @WebServlet("/filedown")
public class FileDownLoader extends HttpServlet {

	ServletConfig mConfig = null;	// 업로드한 경로를 얻기 위한다.
	static final int BUFFER_SIZE = 8192;	// 8kb
	
	//오버라이드 - 제너릭서블릿 - init
	@Override			// config : 경로를 가지고 있는 부분
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		mConfig = config;
	}
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("FileDownLoader doGet");
		
		String filename = req.getParameter("filename");
		String Stringseq = req.getParameter("seq");
		int seq = Integer.parseInt(Stringseq);
		// download 회수 증가(DB 증가)

		BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream());
		
		String filePath = "";
		
		// tomcat 경로
		filePath = mConfig.getServletContext().getRealPath("/upload");
		// 폴더 경로
	//	filePath = "d:\\tmp";
		
		filePath = filePath + "\\" + filename;
		
		System.out.println("다운로드 경로 : " + filePath);
		
		File f = new File(filePath);
		
	// exists : 파일이 존재 하느냐 // 파일을 읽을 수 있느냐
		if(f.exists() && f.canRead()) {
			// window download 창									//기입 안하면 무슨 파일인지 안나옴
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
			resp.setHeader("Content-Transfer-Encoding", "binary;");
												//얼마나 다운로드 되었는지
			resp.setHeader("Content-Length", "" + f.length());
									//기록 안하겠다
			resp.setHeader("Pragma", "no-cache;");
						  // 유효기한      무한대
			resp.setHeader("Expires", "-1;");
			
			//파일 생성, 기입
			BufferedInputStream fileInput
				= new BufferedInputStream(new FileInputStream(f));
			byte buffer[] = new byte[BUFFER_SIZE];
			int read = 0;
			while(( read = fileInput.read(buffer)) != -1 ) {
				out.write(buffer, 0, read);			//실제로 다운로드 되는 부분
			}
			fileInput.close();
			out.flush();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	
}
