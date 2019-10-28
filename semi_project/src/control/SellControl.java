package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.Part;

import member.MemberDao;
import member.MemberDto;
import member.iMember;
import sellbbs.isell_Dao;
import sellbbs.sell_Dao;
import sellbbs.sell_Dto;
import sellcomment.isell_comment_Dao;
import sellcomment.sell_comment_Dao;
import sellcomment.sell_comment_Dto;
import sellgood.isell_good_Dao;
import sellgood.sell_good_Dao;
import sellgood.sellgoodDto;
import util.utilMethod;


public class SellControl extends HttpServlet{

	isell_Dao dao = sell_Dao.getInstance();
	isell_comment_Dao comment_dao = sell_comment_Dao.getInstance(); 
	isell_good_Dao good_dao = sell_good_Dao.getInstance();
	iMember member_dao = MemberDao.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SellControl doGet");
		req.setCharacterEncoding("utf-8");

		String command = req.getParameter("command");
		System.out.println("명령어 : " + command);

		// 세션 로그인 가져오는 부분
		HttpSession session = req.getSession();
		Object ologin =  session.getAttribute("login");

		MemberDto mem = (MemberDto)ologin;

		if(command.equals("list")) {
			int page1 = Integer.parseInt(req.getParameter("page"));
			String categoryValue = req.getParameter("categoryValue");
			String searchValue = req.getParameter("searchValue");
			String text = req.getParameter("text");
			int Count = dao.pageCountMain();
			
			List<sell_Dto> list = dao.getSellList(page1);
			req.setAttribute("searchValue", searchValue);
			req.setAttribute("text", text);
			req.setAttribute("categoryValue", categoryValue);
			req.setAttribute("count", Count);
			req.setAttribute("selllist", list);
			req.getRequestDispatcher("sell/sell_bbsList.jsp").forward(req, resp);
		}
		
		if(command.equals("complete")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			boolean a = dao.completeSell(seq);
			if(a) {
				resp.sendRedirect("sellcontrol?command=list&page=1");
			}
		}

		if(command.equals("detail")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			/*
			  로그인이 안되었을 시 컨트롤러에서 제어하는 부분 if else문으로 처리한다.
			if(mem == null) {
				resp.sendRedirect("sellcontrol?command=list&page=1");
				return;
			}
			 */
			/*
			로그인이 안되었을 시 컨트롤러에서 제어하는 부분 다음 페이지에 들어가는 스크립트 사용 가능
			로케이션, alert메세지!!
			 */
			if(mem == null) {
				resp.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('로그인이 필요한 페이지 입니다..');"
						+ " location.href = 'memcontrol?command=login';</script>");
				out.flush();
				return;
			}else {
				sell_Dto dto = dao.detailSell(seq);
				System.out.println("들어오기 전 dto : " + dto.toString());
				int count = comment_dao.commentCount(seq);
				// 댓글 단 후 page null포인트 익셉션 막아주는 코드
				String spage = req.getParameter("page");
				int page = 1;
				if(spage != null) {
					page = Integer.parseInt(spage);
				}
				////////////////////////////////////
				req.setAttribute("page", page);
				req.setAttribute("count", count);
				req.setAttribute("selldetail", dto);
				req.getRequestDispatcher("sell/sell_bbsDetail.jsp").forward(req, resp);
			}
		}

		if(command.equals("add")) {
			resp.sendRedirect("sell/sell_bbsWrite.jsp");
		}

		if(command.equals("updateSell")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			sell_Dto dto = dao.detailSell(seq);
			req.setAttribute("sellupdate", dto);
			req.getRequestDispatcher("sell/sell_bbsUpdate.jsp").forward(req, resp);

		}

		if(command.equals("deletesell")) {
			int seq = Integer.parseInt(req.getParameter("seq"));
			boolean a = dao.deleteSell(seq);

			if(a) {
				resp.sendRedirect("sellcontrol?command=list&page=1");
				System.out.println("삭제완료");
			}else {
				System.out.println("삭제 실패");
			}
		}
		if(command.equals("likes")) {
				int seq = Integer.parseInt(req.getParameter("seq"));
				boolean a = good_dao.pickId(seq, mem.getId());
				
				resp.setContentType("text/html; charset=UTF-8"); 
				PrintWriter out = resp.getWriter();
				
				if(!a) {
					good_dao.like(new sellgoodDto(0, mem.getId(), seq));
					dao.likeCount(seq);
					int count = dao.getLikeCount(seq);
					out.println("물품에 등록 하였습니다");
					out.println(count);
				}else {
					good_dao.pickDeleteId(seq, mem.getId());
					dao.likeDisCount(seq);
					int count = dao.getLikeCount(seq);
					out.println("물품을 취소 하였습니다");
					out.println(count);
				}
				
				/*
				if(a) {
					resp.setContentType("text/html; charset=UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.println("<script>alert('이미 좋아요를 했습니다...');"
							+ " location.href = 'sellcontrol?command=list&page=1';</script>");
					out.flush();
				}else {
					good_dao.like(new sellgoodDto(0, mem.getId(), seq));
					dao.likeCount(seq);
					resp.sendRedirect("sellcontrol?command=list&page=1");
				}
				*/
				/*
			  boolean a = dao.likeCount(seq);

			  if(a) { resp.sendRedirect("sellcontrol?command=list&page=1"); }
				 */
			}
		
		if(command.equals("search")) {
			String text = req.getParameter("text");
			String searchValue = req.getParameter("searchValue");
			int count = dao.pageCount(searchValue, text);
			
			List<sell_Dto> list = dao.getFindList(1, text, searchValue);
			System.out.println("검색한 리스트 수 : " +list.size());
			
			req.setAttribute("selllist", list);
			req.setAttribute("count", count);
			req.setAttribute("text", text);
			req.setAttribute("searchValue", searchValue);
			req.getRequestDispatcher("sell/sell_bbsList.jsp").forward(req, resp);
		}

		if(command.equals("searchCategory")) {
			String categoryValue = req.getParameter("categoryValue");
			int page1 = Integer.parseInt(req.getParameter("page"));
			int count = dao.pageCategory(categoryValue);

			List<sell_Dto> list = dao.getCategoryList(page1, categoryValue);
			System.out.println("서치 카테고리 검색 리스트 수 : " + list.size());
			System.out.println("카테고리 분류 count 수 : " + count);
			
			req.setAttribute("categoryValue", categoryValue);
			req.setAttribute("selllist", list);
			req.setAttribute("count", count);
			req.getRequestDispatcher("sell/sell_bbsList.jsp").forward(req, resp);
		}

		if(command.equals("commentadd")) {
			
			int parent = Integer.parseInt(req.getParameter("seq"));
			String content = req.getParameter("content");
			String id = req.getParameter("id");
			System.out.println("dto 에 넣기전 아이디" + mem.getId());
			sell_comment_Dto dto = new sell_comment_Dto(mem.getId(), content, parent);
			dto.setDate("0");
			System.out.println("댓글 쓰기 전 dto : " +dto.toString());
			boolean a = comment_dao.sell_Comment_insert(dto);
			if(a) {
				System.out.println("추가 성공");

				resp.sendRedirect("sellcontrol?command=detail&seq="+parent);
			}
		}

		if(command.equals("commentDelete")) {
			int commentseq = Integer.parseInt(req.getParameter("commentseq"));
			int bbsseq = Integer.parseInt(req.getParameter("bbsseq"));
			boolean a = comment_dao.sell_Comment_delete(commentseq);
			if(a) {
				resp.sendRedirect("sellcontrol?command=detail&seq="+bbsseq);
			}
		}

		if(command.equals("comment2add")) {

			int commandSeq = Integer.parseInt(req.getParameter("commentseq"));
			int bbsseq = Integer.parseInt(req.getParameter("bbsseq"));
			String content = req.getParameter("content");

			System.out.println("커맨드 시퀀스 :" +commandSeq + " 답글 내용 : " + content);

			sell_comment_Dto dto = new sell_comment_Dto(commandSeq, mem.getId(), content);
			boolean a = comment_dao.comment_answer(bbsseq ,commandSeq, dto);
			if(a) {
				resp.sendRedirect("sellcontrol?command=detail&seq="+bbsseq);
			}

		}
		if(command.equals("commentUpdate")) {
			int bbsseq = Integer.parseInt(req.getParameter("bbsseq"));
			int commentSeq = Integer.parseInt(req.getParameter("commentseq"));
			String content = req.getParameter("content");

			boolean a = comment_dao.comment_update(commentSeq, content);

			if(a) {
				resp.sendRedirect("sellcontrol?command=detail&seq="+bbsseq);
			}
		}
		if(command.equals("memberDetail")) {
			String nickname = req.getParameter("nickname");
			
			MemberDto dto = member_dao.getSellMemberDto(nickname);
			String grade = member_dao.getMemberGrade(dto.getId());
			String imgname = member_dao.getGradeImg(grade);
			String id = dto.getId();
			String _nickname = dto.getNickname();
			String address = dto.getAddress() + " " + dto.getAddress_detail();
			String email = dto.getEmail();
			
			String json = "{ \"id\":\"" + id + "\"," 
					 +  "\"nickname\":\"" + _nickname + "\","
					 +  "\"address\":\"" + address + "\","
					 +  "\"email\":\"" + email + "\","
					 +  "\"grade\":\"" + grade + "\","
				     +  "\"imgname\":\"" + imgname + "\" }";
			
			resp.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = resp.getWriter();
			
			System.out.println("json : " +json);
			out.println(json);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SellControl doPost");
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		//     PrintWriter out = resp.getWriter();

		String command = req.getParameter("command");
		System.out.println("명령어 : " + command);

		if(command.equals("addProduct")) {

			String id = "";
			String category = "";
			String product = "";
			String title = "";
			String content = "";
			String filename = "";
			String pcategory ="";
			int price = 0;

			String fileupload = req.getServletContext().getRealPath("/upload");
			System.out.println("파일 업로드 폴더 : " + fileupload);

			String yourTempDir = fileupload;
			int yourMaxRequestSize = 100 * 1024 * 1024;	 // 1MByte
			int yourMaxMemorySize = 100 * 1024;			 // 1KByte

			boolean isMultipart = ServletFileUpload.isMultipartContent(req);
			System.out.println("들어옴1");
			if(isMultipart){
				System.out.println("들어옴2");
				// FileItem 생성(org.apache.commons.fileupload.disk.DiskFileItemFactory)
				DiskFileItemFactory factory = new DiskFileItemFactory();

				factory.setSizeThreshold(yourMaxMemorySize);
				factory.setRepository(new File(yourTempDir));

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(yourMaxRequestSize);

				// list에 저장
				List<FileItem> items = null;
				try {
					items = upload.parseRequest(req);
					System.out.println("items : "+items);
					System.out.println("들어옴3");
				} catch (FileUploadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// .parseRequest(req);
				Iterator<FileItem> it = items.iterator();
				//업로드
				while(it.hasNext()) {
					System.out.println("들어옴4");
					FileItem item = it.next();
					if(item.isFormField()) {
						if(item.getFieldName().equals("id")){
							id = item.getString("utf-8");
						} else if(item.getFieldName().equals("sel")){
							category = item.getString("utf-8");
						} else if(item.getFieldName().equals("title")){
							title = item.getString("utf-8");
						} else if(item.getFieldName().equals("contentText")){
							content = item.getString("utf-8");
						} else if(item.getFieldName().equals("price")){
							price = Integer.parseInt(item.getString("utf-8"));
						}else if(item.getFieldName().equals("product")){
							pcategory = item.getString("utf-8");
						}
					}else {
						if(item.getFieldName().equals("filename")) {
							filename = utilMethod.processUploadFile(item, fileupload);
							System.out.println("fileupload: " + fileupload);
							System.out.println("filename: " + filename);
						}
					}
				}
			}else{
				System.out.println("multipart 아님");
			}
			System.out.println("filename : " +filename);
			boolean a = dao.Sell_Insert(id, category, pcategory, filename, title, content, price);
			if(a) {
				resp.sendRedirect("sellcontrol?command=list&page=1");
			}
		}

		if(command.equals("updateProduct")) {

			String title = "";
			String content = "";
			String filename = "";
			String category = "";
			String pcategory ="";
			String seq = "";
			int price = 0;

			String fileupload = req.getServletContext().getRealPath("/upload");
			System.out.println("파일 업로드 폴더 : " + fileupload);

			String yourTempDir = fileupload;
			int yourMaxRequestSize = 100 * 1024 * 1024;	 // 1MByte
			int yourMaxMemorySize = 100 * 1024;			 // 1KByte

			boolean isMultipart = ServletFileUpload.isMultipartContent(req);
			System.out.println("들어옴1");
			if(isMultipart){
				System.out.println("들어옴2");
				// FileItem 생성(org.apache.commons.fileupload.disk.DiskFileItemFactory)
				DiskFileItemFactory factory = new DiskFileItemFactory();

				factory.setSizeThreshold(yourMaxMemorySize);
				factory.setRepository(new File(yourTempDir));

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(yourMaxRequestSize);

				// list에 저장
				List<FileItem> items = null;
				try {
					items = upload.parseRequest(req);
					System.out.println("items : "+items);
					System.out.println("들어옴3");
				} catch (FileUploadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// .parseRequest(req);
				Iterator<FileItem> it = items.iterator();
				//업로드
				while(it.hasNext()) {
					System.out.println("들어옴4");
					FileItem item = it.next();
					if(item.isFormField()) {
						if(item.getFieldName().equals("sel")){
							category = item.getString("utf-8");
						} else if(item.getFieldName().equals("title")){
							title = item.getString("utf-8");
						} else if(item.getFieldName().equals("contentText")){
							content = item.getString("utf-8");
						} else if(item.getFieldName().equals("price")){
							price = Integer.parseInt(item.getString("utf-8"));
						}else if(item.getFieldName().equals("product")){
							pcategory = item.getString("utf-8");
						}else if(item.getFieldName().equals("seq")) {
							seq = item.getString("utf-8");
						}
					}else {
						if(item.getFieldName().equals("filename")) {
							filename = utilMethod.processUploadFile(item, fileupload);
							System.out.println("fileupload: " + fileupload);
							System.out.println("filename: " + filename);
						}
					}
				}
			}else{
				System.out.println("multipart 아님");
			}

			int Iseq = Integer.parseInt(seq);

			sell_Dto dto = dao.detailSell(Iseq);
			dto.setTitle(title);
			dto.setContent(content);
			dto.setCategory(category);
			dto.setPcategory(pcategory);
			dto.setPrice(price);
			dto.setFilename(filename);

			boolean a = dao.updateSell(dto);
			if(a) {
				resp.sendRedirect("sellcontrol?command=list&page=1");
			}
		}
	}
}
