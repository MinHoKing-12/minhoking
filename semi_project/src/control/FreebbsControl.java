package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import free_bbs.free_bbsDao;
import free_bbs.free_bbsDto;
import free_bbs.free_bbs_commentDao;
import free_bbs.free_bbs_commentDto;
import free_bbs.free_bbs_comment_likeDao;
import free_bbs.free_bbs_likeDao;
import net.sf.json.JSONArray; 
import net.sf.json.JSONObject;

public class FreebbsControl extends HttpServlet {

	
	 public static String dateEdit(String strDate) {
	      
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	      String str = "";
	      long min = 0;
	      long hour = 0;
	      Date w_date = new Date();
	      
	      try{
	         w_date = sdf.parse(strDate);
	         Date today = new Date();
	         long diff = today.getTime() - w_date.getTime();
	         min = diff / (1000 * 60);
	         hour = diff / (1000 * 60 * 60);
	      }catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	      if(min < 60) {
	         str = min + "분 전";
	      }else if (min >= 60 && hour < 24) {
	         str = hour + "시간 전";
	      }else if (hour >= 24) {
	         str = new SimpleDateFormat("yyyy-MM-dd").format(w_date);
	      }
	      
	      return str;
	   }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String command = req.getParameter("command");
		free_bbsDao dao = free_bbsDao.getInstance();
		free_bbs_commentDao commentDao = free_bbs_commentDao.getInstance();
		free_bbs_likeDao likedao = free_bbs_likeDao.getInstance();
		free_bbs_comment_likeDao commentlikeDao = free_bbs_comment_likeDao.getInstance();
		
		req.setCharacterEncoding("utf-8"); // 한글 안깨짐
		resp.setContentType("text/html; charset=utf-8");

		HttpSession session= req.getSession();
		
		System.out.println("command:" + command);
		
	// going to bbsList
		if (command.equals("list")) {
			System.out.println("doGet에서 command를 'list'로 받았습니다 ");
			
			String choice ="";
			String searchText ="";
			
			if( req.getParameter("searchChoice")!=null && req.getParameter("searchText")!= null) {
				 choice = req.getParameter("searchChoice");
				 searchText = req.getParameter("searchText");
			}
			System.out.println("-------------------choice : " + choice);
			
			int allPosting = dao.allPosting(choice, searchText);
			System.out.println("-------------------allposting : " + allPosting);
			int pageSize = (int) Math.floor(allPosting / 20);  
			//int pageGroup = (allPosting / pageSize);
						
			int pageNumber = 0;			
			
			if(req.getParameter("pageNumber")!=null) {
				pageNumber = Integer.parseInt(req.getParameter("pageNumber"));	
				System.out.println("before/next pageNumber" + pageNumber);
			}
			
			/*
			 * if(req.getParameter("pageGroup")!=null) { pageGroup = pageGroup + 1; }
			 */	
			
			
			dao = free_bbsDao.getInstance();
			List<free_bbsDto> list = dao.getBbsPagingList(pageNumber, choice, searchText);
			
			req.setAttribute("choice", choice);
			System.out.println("-------------------choice : " + choice);
			req.setAttribute("searchText", searchText);
			req.setAttribute("allPosting", allPosting);
			//req.setAttribute("pageGroup", pageGroup);
			req.setAttribute("pageSize", pageSize);
			req.setAttribute("pageNumber", pageNumber);			
			req.setAttribute("free_bbsList", list);
			req.getRequestDispatcher("free_bbs/free_bbsList.jsp").forward(req, resp);
		}
	// going to paging bbsList
		else if (command.equals("pagelist")) {
				System.out.println("doGet에서 command를 'list'로 받았습니다 ");
				String moveTo = req.getParameter("moveTo");
				
				int pageNumber = 1;
				
				if(moveTo.equals("before")) {
					pageNumber = pageNumber + 1;
				}else if(moveTo.equals("before")){
					pageNumber = pageNumber - 1;
				}
							
				dao = free_bbsDao.getInstance();
				List<free_bbsDto> list = dao.getBbsList(pageNumber);
				
				req.setAttribute("pageNumber", pageNumber);
				req.setAttribute("free_bbsList", list);
				req.getRequestDispatcher("free_bbs/free_bbsList.jsp").forward(req, resp);
			}	
	
	// going to bbsDetail
		else if ("forDetail".equals(command)) {

			System.out.println("forDetail진입");
			
			String _seqVal = req.getParameter("seqVal");
			System.out.println("클릭한 게시물의 String seq값은 : " + _seqVal);
			int seqVal = Integer.parseInt(_seqVal);
			System.out.println("seqVal:" + seqVal);
			
			dao.readCount(seqVal);
			
			int likecount = dao.showLikecount(seqVal);
			
			String _rPageNumber = "1";
			
			int rPageNumber = Integer.parseInt(_rPageNumber);
			
			String order ="";
			int orderVal = 1;
			order = req.getParameter("order");
			
			if(order != null && !order.equals("")) {
				orderVal = Integer.parseInt(order);
			}
			
			List<free_bbs_commentDto> replylistByseq = commentDao.getBbsCommentList(seqVal, rPageNumber, orderVal);
			
			String beforeWdate = "";
			String afterWdate = "";
			int seq = 0;
			int cnt = 0;
			
			for(int i=0; i<replylistByseq.size();i++) {
				beforeWdate = replylistByseq.get(i).getWdate();
				afterWdate = dateEdit(beforeWdate);
				replylistByseq.get(i).setWdate(afterWdate);
				
				System.out.println("afterWdate : " + afterWdate);
				seq = replylistByseq.get(i).getSeq();
				
				if(seq == 0) {
					continue;
				}
				
				cnt = commentDao.getReCommentListCnt(seq);				
				replylistByseq.get(i).setReplyCnt(cnt);
				
			}
			
			int replyCount = commentDao.getCommentListCnt(seqVal); // 페이지번호 댓글페이지번호
			System.out.println("@컨트롤러 command='list' 페이지 댓글 COUNT " + replyCount);
			
			List<free_bbs_commentDto> replylist = commentDao.getBbsCommentLikeList(seqVal);
			
			req.setAttribute("replyCount", replyCount);
			req.setAttribute("rPageNumber", rPageNumber);
			req.setAttribute("seq", seqVal);
			req.setAttribute("replylistByseq", replylistByseq);
			req.setAttribute("replyList", replylist);
			req.setAttribute("likecount", likecount);
			req.getRequestDispatcher("free_bbs/free_bbsDetail.jsp").forward(req, resp);
		}	
			
	// ajax additinal reply paging
		else if ("addReplylist".equals(command)) {	
		
			String _rPageNumber = "";
			int rPageNumber = 0;
			int pageSeq = 0;
			String loginId="";
			
			String order = "";
			int orderVal = 1;
			
			if(req.getParameter("_rPageNumber")!=null && req.getParameter("pageSeq")!=null) {
				rPageNumber = Integer.parseInt(req.getParameter("_rPageNumber"));
				pageSeq = Integer.parseInt(req.getParameter("pageSeq"));
				loginId = req.getParameter("loginId");
				order = req.getParameter("order");
				System.out.println("@컨트롤러 addReply 댓글페이지 넘버는 " + rPageNumber);
				System.out.println("@컨트롤러 addReply 페이지 넘버는 " + rPageNumber);
				System.out.println("@컨트롤러 addReply 정렬 항목은 " + order);
			}
			
			if(req.getParameter("loginId")!=null) {
				loginId = req.getParameter("loginId");
			}
			
			
			if(order != null && !order.equals("")) {
				orderVal = Integer.parseInt(order);
			}
			
			
			free_bbs_comment_likeDao cLikedao = free_bbs_comment_likeDao.getInstance();
			boolean rLikeCheck = false;
					
		//	if(rPageNumber!=1) {
				System.out.println("◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈◈");
				
				resp.setContentType("text/html; charset=UTF-8");
				
				List<free_bbs_commentDto> clist = commentDao.getBbsCommentList(pageSeq, rPageNumber, orderVal);
				
				int reReplyCount = 0;
				
				
				JSONArray jsonArray = new JSONArray();
				
				String beforeDate = "";
				String afterDate = "";
				
				
				JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
				
				for (int i = 0; i < clist.size(); i++) {
					
					beforeDate = clist.get(i).getWdate();
					afterDate = dateEdit(beforeDate);
					clist.get(i).setWdate(afterDate);					
					
					rLikeCheck = cLikedao.checkReplyLike(clist.get(i).getSeq(), loginId);
					reReplyCount = commentDao.getReCommentListCnt(clist.get(i).getSeq());
					
					sObject.put("id", clist.get(i).getId());
			        sObject.put("nickname", clist.get(i).getNickname());
			        sObject.put("content", clist.get(i).getContent());
			        sObject.put("blike", clist.get(i).getBlike());
			        sObject.put("wdate", clist.get(i).getWdate());
			        sObject.put("seq", clist.get(i).getSeq());
			        sObject.put("likecheck", rLikeCheck);
			        sObject.put("reReplyCount", reReplyCount);
			        jsonArray.add(sObject);
				}
								
				//sObject.put("reReplyCount", reReplyCount);
				
				PrintWriter writer = resp.getWriter();
				writer.println(jsonArray);
				
		//	}
		}
		
	// going to bbsWrite
		else if ("forWrite".equals(command)) {
			resp.sendRedirect("free_bbs/free_bbsWrite.jsp");		
		}

	// ♥ like add(plus) : page
		else if ("likeAdd".equals(command)) {	
			String loginId = req.getParameter("loginId");
			String _seq = req.getParameter("seq");
			int seq = Integer.parseInt(_seq);
			
			dao.likeCountPlus(seq);	// seq게시글 blike컬럼 ++
			
			boolean bool = false;
			bool = likedao.addLike(seq, loginId);
			
			int likeCount = dao.showLikecount(seq);
			
			System.out.println("like success? : " + bool);
			resp.setContentType("text/html; charset=UTF-8");
			
			PrintWriter writer = resp.getWriter();
			
			resp.setContentType("text/html; charset=UTF-8");
			
			JSONObject jsonOb = new JSONObject();
			jsonOb.put("result", bool);
			jsonOb.put("likeCount", likeCount);
			writer.println(jsonOb);
			
			// writer.println("{\"result\":\"" + bool + "\", \"likes\":" + likes + "}");
					
		}
	// ♥ unlike page
		else if ("likeCancel".equals(command)) {	
			
			String _loginId = req.getParameter("loginId");
			String _seq = req.getParameter("seq");
			int seq = Integer.parseInt(_seq);
			
			System.out.println("♣♣♣♣♣컨트롤러 loginId");
			
			//free_bbs_likeDto dto = new free_bbs_likeDto(seq, loginId);
			
			dao.likeCountMinus(seq);	// seq게시글 blike컬럼 --
			int likeCount = dao.showLikecount(seq);
			
			boolean bool = false;

			try {
				bool = likedao.cancelLike(seq, _loginId);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("like cancel success? : " + bool);
			resp.setContentType("text/html; charset=UTF-8");
			
			PrintWriter writer = resp.getWriter();
			
			JSONObject jsonOb = new JSONObject();
			jsonOb.put("result", bool);
			jsonOb.put("likeCount", likeCount);
			writer.println(jsonOb);
		}
	//♥ like reply
		else if ("replylikeAdd".equals(command)) {
			String loginId = req.getParameter("loginId");
			String _replySeq = req.getParameter("replySeq");
			int replySeq = Integer.parseInt(_replySeq);
			
			try {
				commentDao.cBlikePlus(replySeq);
				
				int likeCount = commentDao.showrReplyLikecount(replySeq);
				System.out.println("replylikecount from AJAX to DAO likecount" + likeCount);
				
				boolean bool = false;
				bool = commentlikeDao.addReplyLike(replySeq, loginId);
				
				System.out.println("REPLY like success? : " + bool);
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = resp.getWriter();
				
				resp.setContentType("tet/html; charset=UTF-8");
				
				JSONObject jsonOb = new JSONObject();
				jsonOb.put("result", bool);
				jsonOb.put("likeCount", likeCount);
				writer.println(jsonOb);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//♥ unlike reply
		else if ("replylikeCancel".equals(command)) {
			String loginId = req.getParameter("loginId");
			String _replySeq = req.getParameter("replySeq");
			int replySeq = Integer.parseInt(_replySeq);
			
			System.out.println("@replylikeCancel loginId : " + loginId);
			System.out.println("@replylikeCancel replySeq : " + replySeq);
			
			commentDao.cBlikeMinus(replySeq);
			
			boolean bool = false;
			bool = commentlikeDao.cancelReplyLike(replySeq, loginId);
			
			int likeCount = commentDao.showrReplyLikecount(replySeq);
			
			System.out.println("REPLY like success? : " + bool);
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			
			JSONObject jsonOb = new JSONObject();
			jsonOb.put("result", bool);
			jsonOb.put("likeCount", likeCount);
			writer.println(jsonOb);
		
		} 
		// ♥ reReply
		else if ("reReply".equals(command)) {
			resp.setContentType("text/html; charset=UTF-8");
			
			try {
				String _seq = req.getParameter("seq");
				int seq = Integer.parseInt(_seq);
				resp.setContentType("text/html; charset=UTF-8");
				
				List<free_bbs_commentDto> list = commentDao.getReCommentList(seq);
				
				JSONArray jsonArray = new JSONArray();
				
				String beforeWdate = "";
				String afterWdate = "";
				
				for (int i = 0; i < list.size(); i++) {
			        JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
			        
			        beforeWdate = list.get(i).getWdate();
			        afterWdate = dateEdit(beforeWdate);
			        list.get(i).setWdate(afterWdate);
			        
			        sObject.put("seq", list.get(i).getSeq());
			        sObject.put("nickname", list.get(i).getNickname());
			        sObject.put("content", list.get(i).getContent());
			        sObject.put("wdate", list.get(i).getWdate());
			        jsonArray.add(sObject);
			    }
			
				PrintWriter writer = resp.getWriter();
				writer.println(jsonArray);
						
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String command = req.getParameter("command");
		
		req.setCharacterEncoding("utf-8"); // 한글 안깨짐
		resp.setContentType("text/html; charset=utf-8");

		HttpSession session = req.getSession();
		
		System.out.println("command:" + command);
		
		// going to bbsList
		//if (command.equals("list")) {
		/*
		 * if (command.equals("list")) {
		 * System.out.println("doPost에서 command를 list로 받았습니다 "); free_bbsDao dao =
		 * free_bbsDao.getInstance(); int allPosting = dao.allPosting();
		 * System.out.println("-------------------allposting : " + allPosting); int
		 * pageSize = (allPosting / 20) + 1;
		 * System.out.println("-------------------pageSize : " + pageSize); int
		 * pageNumber = 1; int pageGroup = (pageSize / 5);
		 * 
		 * if(req.getParameter("pageNumber")!=null) { pageNumber =
		 * Integer.parseInt(req.getParameter("pageNumber"));
		 * System.out.println("before/next pageNumber" + pageNumber); }else
		 * 
		 * dao = free_bbsDao.getInstance(); List<free_bbsDto> list =
		 * dao.getBbsList(pageNumber);
		 * 
		 * req.setAttribute("pageGroup", pageGroup); req.setAttribute("allPosting",
		 * allPosting); req.setAttribute("pageSize", pageSize);
		 * req.setAttribute("pageNumber", pageNumber); req.setAttribute("free_bbsList",
		 * list); req.getRequestDispatcher("free_bbs/free_bbsList.jsp").forward(req,
		 * resp); }
		 */
		
		// add bbs
		if (command.equals("bbsWrite")) {
			System.out.println("doPost dao 진입");
			
			String id = req.getParameter("id");
			String nickname = req.getParameter("nickname");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			
			id = new String(id.getBytes("8859_1"), "utf-8");
			nickname = new String(nickname.getBytes("8859_1"), "utf-8");
			title = new String(title.getBytes("8859_1"), "utf-8");
			content = new String(content.getBytes("8859_1"), "utf-8");
			
			System.out.println("dao addBbs id : " + id);
			System.out.println("dao addBbs nickname : " + nickname);
			
			boolean bool = false;
			
			try {
				
				free_bbsDao dao = free_bbsDao.getInstance();
				free_bbsDto dto = new free_bbsDto(id, nickname,title, content);
				bool = dao.addBbs(dto);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("write success? : " + bool);
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			
			if(bool == true){
				writer.println("<script type='text/javascript'>");
				writer.println("alert('WRITE SUCCESS');");
				writer.println("history.back();");
				writer.println("</script>");
				
				resp.sendRedirect("freeControl?command=list");
				
			}else {
				writer.println("<script type='text/javascript'>");
				writer.println("alert('WRITE FAIL');");
				writer.println("history.back();");
				writer.println("</script>");
			}
		}
		
		// deleting bbsDetail 	
			else if ("deleteDetail".equals(command)) {
				
				// seq 확인
				String _seq = req.getParameter("seq");
				int seq = Integer.parseInt(_seq);
				System.out.println("Controller delete seq : " + seq);
				
				free_bbsDao dao = free_bbsDao.getInstance();
				boolean bool = false;
				bool = dao.deleteBbs(seq);
				
				System.out.println("delete success? : " + bool);
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter writer = resp.getWriter();
				
				if(bool == true){
					writer.println("<script type='text/javascript'>");
					writer.println("alert('DELETE SUCCESS');");
					writer.println("history.back();");
					writer.println("</script>");
					
					resp.sendRedirect("freeControl?command=list");
					
				}else {
					writer.println("<script type='text/javascript'>");
					writer.println("alert('DELETE FAIL');");
					writer.println("history.back();");
					writer.println("</script>");
				}
			}	
		
	// going to bbsUpdate	
		else if ("forUpdate".equals(command)) {
			// seq 확인
			String _seq = req.getParameter("seq");
			int seq = Integer.parseInt(_seq);
			System.out.println("Controller update seq : " + seq);
			
			req.setAttribute("updateSeq", seq);
			req.getRequestDispatcher("free_bbs/free_bbsUpdate.jsp").forward(req, resp);			
		}
		
	// updating bbsDetail 	
		else if ("bbsUpdate".equals(command)) {
			System.out.println("command bbsUpdate");
		// seq 
			String _seq = req.getParameter("seq");
			int seq = Integer.parseInt(_seq);
			System.out.println("★Controller update seq : " + seq);
			
		// nickname, title, content
			String _nickname = req.getParameter("nickname");
			String _title = req.getParameter("title");
			String _content = req.getParameter("hdnContent");
			
			_nickname = new String(_nickname.getBytes("8859_1"), "utf-8");
			_title = new String(_title.getBytes("8859_1"), "utf-8");
			_content = new String(_content.getBytes("8859_1"), "utf-8");	
						
			System.out.println("update nickname : " + _nickname);
			System.out.println("update title : " + _title);
			System.out.println("update content : " + _content);
			
			free_bbsDao dao = free_bbsDao.getInstance();
			free_bbsDto dto = new free_bbsDto(seq, _title, _content);
			
			boolean bool = false;
			
			try {
				bool = dao.updateBbs(dto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("update success? : " + bool);
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			
			if(bool == true){
				writer.println("<script type='text/javascript'>");
				writer.println("alert('UPDATE SUCCESS');");
				writer.println("history.back();");
				writer.println("</script>");
				
				resp.sendRedirect("freeControl?command=list");
				
			}else {
				writer.println("<script type='text/javascript'>");
				writer.println("alert('UPDATE FAIL');");
				writer.println("history.back();");
				writer.println("</script>");
			}
		}
	// REPLY add(INSERT) 
		else if ("forReply".equals(command)) {
			
			System.out.println("doPost reply 진입ㅊㅋ");
			
			String _page = req.getParameter("parentSeq");
			String _id = req.getParameter("replyId");
			String _nickname = req.getParameter("replyNickname");
			String _content = req.getParameter("replyContent");
			
			_id = new String(_id.getBytes("8859_1"), "utf-8");
			_page = new String(_page.getBytes("8859_1"), "utf-8");
			_nickname = new String(_nickname.getBytes("8859_1"), "utf-8");			
			_content = new String(_content.getBytes("8859_1"), "utf-8");
			
			int pageSeq = Integer.parseInt(_page);
			
			free_bbs_commentDao dao = free_bbs_commentDao.getInstance();
			free_bbs_commentDto dto = new free_bbs_commentDto(pageSeq, _id, _nickname, _content);
			
			boolean bool = false;
			bool = dao.addReply(dto);
			
			System.out.println("reply add success? : " + bool);
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			
			if(bool == true){
				writer.println("<script type='text/javascript'>");
				writer.println("alert('REPLY ADD SUCCESS');");
				writer.println("history.back();");
				writer.println("</script>");
				
				resp.sendRedirect("freeControl?seqVal="+pageSeq+"&command=forDetail");
				
			}else {
				writer.println("<script type='text/javascript'>");
				writer.println("alert('REPLY ADD FAIL');");
				writer.println("history.back();");
				writer.println("</script>");
			}
		}
	// REPLY delete
		else if ("forReplyDelete".equals(command)) {
		//bbs Seq
			String bbsSeq = req.getParameter("bbsSeq");
			
			String _replySeq = req.getParameter("replySeq");
			System.out.println("replySeq : " + _replySeq);
			
			int replySeq = Integer.parseInt(_replySeq);
			
			free_bbs_commentDao dao = free_bbs_commentDao.getInstance();
			boolean bool = false;
			
			try {
				bool = dao.deleteReply(replySeq);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("reply delete success? : " + bool);
			
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			
			if(bool == true){
				writer.println("<script type='text/javascript'>");
				writer.println("alert('REPLY DELETE SUCCESS');");
				writer.println("history.back();");
				writer.println("</script>");
				
				resp.sendRedirect("freeControl?seqVal="+bbsSeq+"&command=forDetail");
				
			}else {
				writer.println("<script type='text/javascript'>");
				writer.println("alert('REPLY DELETE FAIL');");
				writer.println("history.back();");
				writer.println("</script>");
			}
			
		}
	// REPLY update
		else if ("forReplyUpdate".equals(command)) {
		// bbsSeq(page)
			String page = req.getParameter("bbsSeq");
			System.out.println("@CONTOLLER reply update bbsSeq : " + page);
		// reply seq
			String _replySeq = req.getParameter("replySeq");
			System.out.println("replySeq : " + _replySeq);			
			int replySeq = Integer.parseInt(_replySeq);
		// content
			String updatedReply = req.getParameter("hdnUdtReplyContent");
			updatedReply = new String(updatedReply.getBytes("8859_1"), "utf-8");			
			System.out.println("@CONTROLLER reply update content" + updatedReply);
			
			free_bbs_commentDao dao = free_bbs_commentDao.getInstance();
			boolean bool = false;
			
			try {
				bool = dao.updateReply(replySeq, updatedReply);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("REPLY UPDATE success?? : " + bool);
			
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			
			if(bool == true){
				writer.println("<script type='text/javascript'>");
				writer.println("alert('REPLY UPDATE SUCCESS');");
				writer.println("history.back();");
				writer.println("</script>");
				
				resp.sendRedirect("freeControl?seqVal="+page+"&command=forDetail");
				
			}else {
				writer.println("<script type='text/javascript'>");
				writer.println("alert('REPLY UPDATE FAIL');");
				writer.println("history.back();");
				writer.println("</script>");
			}
			
		}	
	//reReply add(INSERT)
		
		else if ("reReplyAdd".equals(command)) {
			System.out.println("@CONTROLLER doPost reReply add 진입ㅊㅋ");
			
			//BBS PAGE SEQ
			String _page = req.getParameter("seq");
			int page = Integer.parseInt(_page);
			
			String parentReplySeq = req.getParameter("parentReplySeq");
			String reReplyContent = req.getParameter("reReplyContent");
			String reReplyNickname = req.getParameter("reReplyNickname");
						
			parentReplySeq = new String(parentReplySeq.getBytes("8859_1"), "utf-8");
			reReplyContent = new String(reReplyContent.getBytes("8859_1"), "utf-8");
			reReplyNickname = new String(reReplyNickname.getBytes("8859_1"), "utf-8");
			
			int _parentReplySeq = Integer.parseInt(parentReplySeq);
			
			free_bbs_commentDao dao = free_bbs_commentDao.getInstance();
			free_bbs_commentDto dto = new free_bbs_commentDto(reReplyNickname, reReplyContent, _parentReplySeq, page);

			boolean bool = false;
			
			try {
				bool = dao.addReReply(dto);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("reReply add success? : " + bool);
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			
			
			if(bool == true){
				writer.println("<script type='text/javascript'>");
				writer.println("alert('reREPLY ADD SUCCESS');");
				writer.println("history.back();");
				writer.println("</script>");
				
				resp.sendRedirect("freeControl?seqVal="+page+"&command=forDetail");
				
			}else {
				writer.println("<script type='text/javascript'>");
				writer.println("alert('reREPLY ADD FAIL');");
				writer.println("history.back();");
				writer.println("</script>");
			}
		
		}

		else if ("reReplyDel".equals(command)) {

			String _reReplyDelSeq = "";
			String _replysequence = "";
			int reReplyDelSeq = 0;
			int replysequence = 0;
			//대댓글 시퀀스
			if(req.getParameter("reReplyDelSeq")!=null) {
				_reReplyDelSeq = req.getParameter("reReplyDelSeq");
				reReplyDelSeq = Integer.parseInt(_reReplyDelSeq);
			}	
			//댓글 시퀀스	
			if(req.getParameter("replysequence")!=null) {
				_replysequence = req.getParameter("replysequence");
				replysequence = Integer.parseInt(_replysequence);
			}
			
			
			free_bbs_commentDao commentDao = free_bbs_commentDao.getInstance();
			
			
			
			boolean bool = false;
			
			try {
				bool = commentDao.delteReReply(reReplyDelSeq);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			int reReplyCount = commentDao.getReCommentListCnt(replysequence);
			
			if(bool) {System.out.println("대댓글 삭제완료");};
			
			
			PrintWriter writer = resp.getWriter();
			
			resp.setContentType("text/html; charset=UTF-8");
			
			JSONObject jsonOb = new JSONObject();
			jsonOb.put("count", reReplyCount);
			jsonOb.put("result", bool);
			writer.println(jsonOb);
			
			/* resp.sendRedirect("freeControl?seqVal="+page+"&command=forDetail"); */
			
		}	
	
		
		
	}	
}
