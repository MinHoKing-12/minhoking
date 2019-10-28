package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import free_bbs.free_bbsDao;
import free_bbs.free_bbsDto;
import free_bbs.free_bbs_commentDao;
import free_bbs.ifree_bbsDao;
import free_bbs.ifree_bbs_commentDao;
import member.MemberDao;
import member.MemberDto;
import member.iMember;
import message.MessageDao;
import message.MessageDto;
import message.iMessage;
import sellbbs.isell_Dao;
import sellbbs.sell_Dao;
import sellbbs.sell_Dto;
import sellcomment.isell_comment_Dao;
import sellcomment.sell_comment_Dao;
import votes.VcommentDao;
import votes.iVcomment;
import votes.iVoteDao;

public class MypageControl extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		String command = req.getParameter("command");
		
		System.out.println(command);
		
		iMember memDao = MemberDao.getInstance();
		iMessage msgDao = MessageDao.getInstance();
		isell_Dao sellDao = sell_Dao.getInstance();
		ifree_bbsDao freeDao = free_bbsDao.getInstance();
		
		isell_comment_Dao sellcommDao = sell_comment_Dao.getInstance();
		ifree_bbs_commentDao freecommDao = free_bbs_commentDao.getInstance();
		iVcomment votecommDao = VcommentDao.getInstance();
		
		HttpSession session = req.getSession();
		MemberDto user = (MemberDto)session.getAttribute("login");
		
		if(user == null) {
			resp.setContentType("text/html; charset=UTF-8");
   			PrintWriter out = resp.getWriter();
   			
			out.println("<script type='text/javascript'>");
			out.println("alert('로그인이 필요한 페이지 입니다');");
			out.println("location.href='memcontrol?command=login'");
			out.println("</script>");
		}else {
			String id = user.getId();
			String grade = memDao.getMemberGrade(id);
			String imgname = memDao.getGradeImg(grade);
			int newMsg = msgDao.notReadMsgCount(id);
			
			
			// 나의 쪽지함 : 받은쪽지, 보낸쪽지
			if(command.equals("mymsgbox")) {
				String type = req.getParameter("type");
				if(type == null) {
					type = "receive";
				}
				
				String spage = req.getParameter("page");
				int page = 0;
				if(spage == null) {
					page = 1;
				}else {
					page = Integer.parseInt(spage);
				}
				
				req.setAttribute("grade", grade);
				req.setAttribute("imgname", imgname);
				req.setAttribute("newMsg", newMsg);
				
				String select = req.getParameter("search-select");
				String str = req.getParameter("search-text");
				String category = req.getParameter("category");
				req.setAttribute("category", category);
				req.setAttribute("select", select);
				req.setAttribute("searchText", str);
				
				if(select != null && select.equals("id")) {
					str = memDao.getId(str);
				}
				System.out.println("검색어 : " + str);
				int totalPages = msgDao.getPageCount(type, id, select, str, category);
				System.out.println("로그인된 아이디 : " + id);
				List<MessageDto> list = msgDao.getMsgList(type, page, id, select, str, category);
				
				req.setAttribute("msgList", list);
				req.setAttribute("totalPages", totalPages);
				req.setAttribute("page", page);
				
				if(type.equals("receive")) {
					req.getRequestDispatcher("./mypage/myrecvmsg.jsp").forward(req, resp);
				}else if(type.equals("send")) {
					req.getRequestDispatcher("./mypage/mysendmsg.jsp").forward(req, resp);
				}
				
			}else if(command.equals("msgread")) {
				String sseq = req.getParameter("seq");
				int seq = Integer.parseInt(sseq);
				
				msgDao.readMessage(seq);
				
			}else if(command.equals("deletemsg")) {
				String type = req.getParameter("type");
				String sseq = req.getParameter("delete-seq");
				int seq = Integer.parseInt(sseq);

				boolean isS = msgDao.deleteMessage(seq);
				
				if(isS) {
					resp.sendRedirect("mypagecontrol?command=mymsgbox&type=" + type);
				}
			}
			
			// 나의 게시판 : 자유, 거래
			else if(command.equals("myfreebbs")) {
				String select = req.getParameter("search-select");
				String str = req.getParameter("search-text");
				String spage = req.getParameter("page");
				
				int page = 0;
				
				if(spage == null) {
					page = 1;
				}else {
					page = Integer.parseInt(spage);
				}
				
				int totalPages = freeDao.getMyListCount(id, select, str);
				List<free_bbsDto> list = freeDao.getMyList(id, page, select, str);
				
				req.setAttribute("grade", grade);
				req.setAttribute("imgname", imgname);
				req.setAttribute("newMsg", newMsg);
				
				req.setAttribute("myfreeList", list);
				req.setAttribute("totalPages", totalPages);
				req.setAttribute("page", page);
				
				req.setAttribute("select", select);
				req.setAttribute("searchText", str);
				
				req.getRequestDispatcher("./mypage/myfreebbs.jsp").forward(req, resp);
			}else if(command.equals("mysellbbs")) {
				
				String select = req.getParameter("search-select");
				String str = req.getParameter("search-text");
				String spage = req.getParameter("page");
				
				int page = 0;
				
				if(spage == null) {
					page = 1;
				}else {
					page = Integer.parseInt(spage);
				}
				
				int totalPages = sellDao.getMyListCount(id, select, str);
				List<sell_Dto> list = sellDao.getMyList(id, page, select, str);
				
				req.setAttribute("grade", grade);
				req.setAttribute("imgname", imgname);
				req.setAttribute("newMsg", newMsg);
				
				req.setAttribute("mysellList", list);
				req.setAttribute("totalPages", totalPages);
				req.setAttribute("page", page);
				
				req.setAttribute("select", select);
				req.setAttribute("searchText", str);
				
				req.getRequestDispatcher("./mypage/mysellbbs.jsp").forward(req, resp);
				
			}
			
			// 나의 댓글보기
			else if(command.equals("myfreecomm")) {
				
			}else if(command.equals("mysellcomm")) {
				
			}
			
			// 나의 좋아요 게시글
			else if(command.equals("myfreelike")) {
				
				String select = req.getParameter("search-select");
				String str = req.getParameter("search-text");
				String spage = req.getParameter("page");
				
				int page = 0;
				
				if(spage == null) {
					page = 1;
				}else {
					page = Integer.parseInt(spage);
				}
				
				int totalPages = freeDao.getMyLikeListCount(id);
				List<free_bbsDto> list = freeDao.getMyLikeList(id, page);
				
				req.setAttribute("grade", grade);
				req.setAttribute("imgname", imgname);
				req.setAttribute("newMsg", newMsg);
				
				req.setAttribute("myfreeList", list);
				req.setAttribute("totalPages", totalPages);
				req.setAttribute("page", page);
				
				req.setAttribute("select", select);
				req.setAttribute("searchText", str);
				
				req.getRequestDispatcher("./mypage/myfreelike.jsp").forward(req, resp);
				
			}else if(command.equals("myselllike")) {
				String select = req.getParameter("search-select");
				String str = req.getParameter("search-text");
				String spage = req.getParameter("page");
				
				int page = 0;
				
				if(spage == null) {
					page = 1;
				}else {
					page = Integer.parseInt(spage);
				}
				
				int totalPages = sellDao.getMyLikeListCount(id);
				List<sell_Dto> list = sellDao.getMyLikeList(id, page);
				
				req.setAttribute("grade", grade);
				req.setAttribute("imgname", imgname);
				req.setAttribute("newMsg", newMsg);
				
				req.setAttribute("mysellList", list);
				req.setAttribute("totalPages", totalPages);
				req.setAttribute("page", page);
				
				req.setAttribute("select", select);
				req.setAttribute("searchText", str);
				
				req.getRequestDispatcher("./mypage/myselllike.jsp").forward(req, resp);
			}
			
			// 나의 정보 수정
			else if(command.equals("myinfo")) {
				
				req.setAttribute("grade", grade);
				req.setAttribute("imgname", imgname);
				req.setAttribute("newMsg", newMsg);
				req.setAttribute("loginInfo", user);
				req.getRequestDispatcher("./mypage/myinfo.jsp").forward(req, resp);
				
			}
			// 회원 탈퇴
			else if(command.equals("member-withdrawal")) {
				// 회원 쪽지 갯수
				int receiveMsg = msgDao.msgCount("receive", id);
				int sendMsg = msgDao.msgCount("send", id);
				// 회원 게시글 갯수
				int freebbs = freeDao.bbsCount(id);
				int sellbbs = sellDao.bbsCount(id);
				// 회원 댓글 갯수
				int freecomm = freecommDao.myCommentCount(id);
				int sellcomm = sellcommDao.myCommentCount(id);
				int votecomm = votecommDao.myCommentCount(id);
				
				req.setAttribute("grade", grade);
				req.setAttribute("imgname", imgname);
				req.setAttribute("newMsg", newMsg);
				req.setAttribute("receiveMsg", receiveMsg);
				req.setAttribute("sendMsg", sendMsg);
				req.setAttribute("bbsCount", freebbs + sellbbs);
				req.setAttribute("commCount", freecomm + sellcomm + votecomm);
				
				req.getRequestDispatcher("./mypage/member-withdrawal.jsp").forward(req, resp);
			}else if(command.equals("withdrawalAf")) {
				System.out.println(id);
				boolean isS = memDao.deleteMember(id);
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				
				if(isS) {
					out.println("<script type='text/javascript'>");
					out.println("alert('성공적으로 회원 탈퇴가 되었습니다. 그 동안 이용해주셔서 감사합니다.');");
					out.println("location.href='memcontrol?command=logout'");
					out.println("</script>");
				}else {
					out.println("<script type='text/javascript'>");
					out.println("alert('예기치 못한 오류로 회원탈퇴를 실패했습니다. 다시시도해주십시오');");
					out.println("location.href='mypagecontrol?command=member-withdrawal");
					out.println("</script>");
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String command = req.getParameter("command");
		iMessage msgDao = MessageDao.getInstance();
		iMember memDao = MemberDao.getInstance();
		
		// 쪽지쓰기
		if(command.equals("writemsgAf")) {
			String to_nick = req.getParameter("to_nick");
			String from_nick = req.getParameter("from_nick");
			String content = req.getParameter("content");
			
			String to_id = memDao.getId(to_nick);
			String from_id = memDao.getId(from_nick);
			
			MessageDto dto = new MessageDto(to_id, from_id, content);
			System.out.println(dto.toString());
			
			boolean isS = msgDao.writeMsg(dto);
			
			PrintWriter out = resp.getWriter();
			out.println(isS);
			out.flush();
			
		}
		// 쪽지 여러개 삭제
		else if(command.equals("multidelmsg")) {
			String sseq[] = req.getParameterValues("delcheck");
			String type = req.getParameter("type");
			
			int seq[] = new int[sseq.length];
			
			for (int i = 0; i < seq.length; i++) {
				seq[i] = Integer.parseInt(sseq[i]);
				System.out.println(seq[i]);
			}
			
			boolean isS = msgDao.multidelMessage(seq);
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			
			if(isS) {
				System.out.println("isS == true");
				out.println("<script type='text/javascript'>");
				out.println("alert('성공적으로 쪽지를 삭제했습니다');");
				out.println("location.href='mypagecontrol?command=mymsgbox&type=" + type + "'");
				out.println("</script>");
				
				out.flush();
			}
		}
	}
	
}
