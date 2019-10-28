package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.MemberDto;
import votes.VResultDto;
import votes.VcommnetDto;
import votes.VoteDto;
import votes.VResultDao;
import votes.VcommentDao;
import votes.VoteDao;
import votes.iVResultDao;
import votes.iVcomment;
import votes.iVoteDao;

public class VoteControl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
			String command = req.getParameter("command");
				
			iVoteDao dao = VoteDao.getInstance();
			iVResultDao ddao = VResultDao.getInstance();
			iVcomment com = VcommentDao.getInstance();
			HttpSession session = req.getSession();
			MemberDto user = (MemberDto)session.getAttribute("login");
			
			
			if(command.equals("main")) {
				resp.sendRedirect("./main/main.jsp");
			}
	        
		    if(command.equals("start")) {
		    	resp.sendRedirect("./VoteControl?command=list");
		    }
			
			
			//VoteView에 나오는 리스트 (초기화면 == 이름순)
			if(command.equals("list")) {
			
			List<VoteDto> list = dao.getVotelist();
			String val = req.getParameter("val");
			System.out.println(val);
			
			req.setAttribute("filename", list);
			req.setAttribute("val", val);
	
			req.getRequestDispatcher("./vote/VoteView.jsp").forward(req, resp);
		   }
			
			
			//VoteView에 나오는 리스트 (랭킹순)
			if(command.equals("rank")) {
				
			List<VoteDto> list = dao.ranklist();
			String val = req.getParameter("val");
				
			req.setAttribute("filename", list);
			req.setAttribute("val", val);
			req.getRequestDispatcher("./vote/VoteView.jsp").forward(req, resp);	
			}
	
			
			//프로필사진 눌렀을때 이동
			if(command.equals("vote")) {
			//System.out.println("선수 사진 눌렀다");
				//로그인이 안되있을 때 선수에게 투표못하게 막아놓기
			if(session.getAttribute("login") == null) {
		      
				resp.setContentType("text/html; charset=UTF-8"); 
				 
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('투표게시판을 이용하시려면 로그인을 해주세요'); history.go(-1);</script>");
				out.flush();
			}
			else {
			
			String sseq = req.getParameter("seq");
			int seq = Integer.parseInt(sseq);	
			VoteDto dto = dao.whoplayer(seq);	
			String spage = req.getParameter("Page");
			
			System.out.println("내가누른 페이지번호: " + spage);
			
			int page = 0;
			if(spage != null && !spage.equals("") ){
				page = Integer.parseInt(spage);
			}
			
			List<VcommnetDto> paginglist = com.getBbsPagingList(seq, page);
			VcommnetDto comdto = com.reply(seq);
			
			int allpage = com.getAllComment(seq);//페이징하기위해 댓글 개수 알아내기
			System.out.println("총글갯수: " +allpage);
			
			req.setAttribute("allpage", allpage+"");
			req.setAttribute("pagenum", spage);
			req.setAttribute("list", paginglist);
			req.setAttribute("who", dto);
			req.setAttribute("reply",comdto);
			req.setAttribute("seq", sseq);
			
			req.getRequestDispatcher("./vote/Vote.jsp").forward(req, resp);
			}	
				}
		
		
		
		
			// 투표하기 버튼 눌렀을때
			if(command.equals("pick")) {
			
			String sseq = req.getParameter("seq");
			int seq = Integer.parseInt(sseq);
			String nickname = req.getParameter("nickname");

			String id = com.getId(nickname);
           
			//투표함에 아이디가 있나 check
			boolean idcheck = ddao.votenick(id);
			//System.out.println(id);	
			if(idcheck) {	
				  resp.setContentType("text/html; charset=UTF-8"); 
				  
				  PrintWriter out = resp.getWriter();
				  out.println("<script>alert('투표를 이미 하셨습니다.'); history.go(-1);</script>");
				  out.flush();
			}	
			else {
			       boolean b = ddao.Pickplayer(new VResultDto(0,id,seq));
					if(b) { 
						
						resp.setContentType("text/html; charset=UTF-8"); 
						  
						PrintWriter out = resp.getWriter();
					    out.println("<script>alert('투표해주셔서 감사합니다.'); history.go(-1);</script>");
						out.flush();
						
						//여기서 likes 수 올려줌 (이걸로 정렬)
						iVoteDao vodao = VoteDao.getInstance();
						vodao.likecount(seq);
			
						resp.sendRedirect("./VoteControl?seq="+sseq+"&command=list");	
					} else {
							System.out.println("투표못함"); 
					    }				
				}
			}
		
			//댓글등록 눌렀을때		
			if(command.equals("command")) {

			String text = req.getParameter("text");
			String sseq1 = req.getParameter("seq");
			String nickname1 = req.getParameter("nickname");
			int seq1 = Integer.parseInt(sseq1);
			
			//System.out.println(nickname1);
			
			String id = com.getId(nickname1);
			//System.out.println("찾은 id: " +id);
			
			VcommnetDto dto;
			boolean b = com.addcomment(new VcommnetDto(id , text, seq1));
			
			if(b) {
				//System.out.println("댓글작성완료");	
				resp.sendRedirect("./VoteControl?seq="+sseq1+"&command=vote");			
			 }
			
			else {
				System.out.println("댓글 실패");
				}
			}
		
			//댓글 업데이트
			if(command.equals("update")) {
				
               String text = req.getParameter("text");
               String sseq = req.getParameter("seq");
               int seq = Integer.parseInt(sseq);
               System.out.println("들어온내용:" +text+ "  댓글 넘버: "+seq);
               String num = req.getParameter("num");
            
               boolean b = com.Updatereply(text, seq);
               if(b) {
            	   	System.out.println("댓글수정완료");
			
            	   	resp.sendRedirect("./VoteControl?command=vote&seq="+num);
               		}
               }
		
		    //댓글 삭제
			if(command.equals("deleted")) {
			
			String sseq = req.getParameter("seq");
			int seq = Integer.parseInt(sseq);//댓글의 seq
			
			String snum = req.getParameter("num");//선수(게시글)seq
			int num = Integer.parseInt(snum);
			
			System.out.println("게시글고유seq: " +seq);
			
			boolean b = com.deletereply(3, seq);
			if(b) {
				System.out.println("댓글삭제완료");		
				resp.sendRedirect("./VoteControl?command=vote&seq="+num);
			}
		  }
		
			//선수검색
			if(command.equals("search")) {
			
			String name = req.getParameter("name");
			System.out.println(name);
			
			//검색한 리스트 뿌려주기
			List<VoteDto> list = dao.findList(name);
			
			req.setAttribute("filename", list);
			req.getRequestDispatcher("./vote/VoteView.jsp").forward(req, resp);
			}
		
			//결과 그래프 뿌려주려고
			if(command.equals("result")) {
		
			System.out.println("결과창 들어왔다!");
			
			List<VoteDto> list = dao.getVotelist();
			req.setAttribute("result", list);
		    req.getRequestDispatcher("./vote/VResultView.jsp").forward(req, resp);
			}
		}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
