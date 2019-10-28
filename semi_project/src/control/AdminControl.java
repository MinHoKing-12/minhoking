package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import calendar.CalendarDao;
import calendar.CalendarDto;
import calendar.iCalendar;
import free_bbs.free_bbsDao;
import free_bbs.free_bbsDto;
import free_bbs.free_bbs_commentDao;
import free_bbs.free_bbs_commentDto;
import free_bbs.free_bbs_comment_likeDao;
import free_bbs.free_bbs_likeDao;
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
import sellcomment.sell_comment_Dto;
import util.utilMethod;
import votes.VResultDao;
import votes.VResultDto;
import votes.VcommentDao;
import votes.VcommnetDto;
import votes.VoteDao;
import votes.VoteDto;
import votes.iVResultDao;
import votes.iVcomment;
import votes.iVoteDao;

public class AdminControl extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String command = req.getParameter("command");
		
		HttpSession session = req.getSession();
		MemberDto user = (MemberDto)session.getAttribute("login");
		
		iMember memDao = MemberDao.getInstance();
		iMessage msgDao = MessageDao.getInstance();
		isell_Dao sellDao = sell_Dao.getInstance();
		isell_comment_Dao sellcommentDao = sell_comment_Dao.getInstance();
		iCalendar caldao = CalendarDao.getInstance();
		iVResultDao redao = VResultDao.getInstance();
		iVoteDao votedao = VoteDao.getInstance();
		iVcomment vocomdao = VcommentDao.getInstance();
		
		// Member Admin Command
		if(command.equals("memlist")) {
			System.out.println("admincontrol command=memlist");
			String select = req.getParameter("search-select");
			String text = req.getParameter("search-text");
			String spage = req.getParameter("page");
			int page = 1;
			if(spage != null) {
				page = Integer.parseInt(spage);
			}
			
			List<MemberDto> list = memDao.getMemberList(select, text, page);
			int totalPages = memDao.getTotalPages(select, text);
			int totalMemCount = memDao.memCount(); 
			
			req.setAttribute("list", list);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("totalMemCount", totalMemCount);
			req.setAttribute("page", page);
			req.setAttribute("select", select);
			req.setAttribute("text", text);
			
			req.getRequestDispatcher("./admin/memberAdmin.jsp").forward(req, resp);
		}else if(command.equals("memdelete")) {
			
		}else if(command.equals("multi-memdelete")) {
			String[] delarr = req.getParameterValues("delete-check");
			boolean isS = memDao.multiDelete(delarr);
			System.out.println("Admincontrol : " + isS);
			
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			
			if(isS) {
				out.println("<script type='text/javascript'>");
				out.println("alert('성공적으로 회원을 삭제했습니다');");
				out.println("location.href='admincontrol?command=memlist'");
				out.println("</script>");
				
				out.flush();
			}
		}
		
		
		// Sellbbs Admin Command
		else if(command.equals("sellbbslist")) {
			int page1 = Integer.parseInt(req.getParameter("page"));
			List<sell_Dto> list = sellDao.getSellListadmin(page1);

			int count = sellDao.pageCountMain();
			
			req.setAttribute("count", count);
			req.setAttribute("list", list);
			req.getRequestDispatcher("./admin/sellbbsAdmin.jsp").forward(req, resp);

		}else if(command.equals("sellsearch")) {
			String searchText = req.getParameter("searchText");
			String searchValue = req.getParameter("searchValue");
			int count = sellDao.pageCount(searchValue, searchText);

			List<sell_Dto> list = sellDao.getFindList(1, searchText, searchValue);
			System.out.println("검색한 리스트 수 : " +list.size());

			req.setAttribute("list", list);
			req.setAttribute("count", count);
			req.setAttribute("text", searchText);
			req.setAttribute("searchValue", searchValue);
			req.getRequestDispatcher("admin/sellbbsAdmin.jsp").forward(req, resp);
		}else if(command.equals("delete")) {
			// 중고 거래 게시판 멀티 삭제 부분입니다.
			String[] Schildcheck = req.getParameterValues("childcheck");
			int[] childcheck = new int[Schildcheck.length];
			for(int i = 0; i<childcheck.length; i++) {
				childcheck[i] = Integer.parseInt(Schildcheck[i]);
				System.out.println("거래게시판 시퀀스 :" + childcheck[i]);
				//댓글삭제
				boolean a = sellDao.multidelsellComment(childcheck);
				if(a) {
					//좋아요 삭제
					boolean b = sellDao.multidelsellLike(childcheck);
					if(b) {
						// 게시글 삭제
						boolean c = sellDao.multidelsellbbs(childcheck);
						if(c) {
							System.out.println("삭제 성공");
						}
					}
				}else {
					System.out.println("삭제 실패");
				}
			}
			resp.sendRedirect("admincontrol?command=sellbbslist&page=1");
		}else if(command.equals("commentdelete")) {
			//거래 게시판 댓글 멀티 삭제 부분입니다.
			System.out.println("거래게시판 댓글 삭제");
			int seq = Integer.parseInt(req.getParameter("detailSeq"));
			String[] Schildcheck = req.getParameterValues("childcheck");
			int[] childcheck = new int[Schildcheck.length];
			for(int i=0; i<childcheck.length; i++) {
				childcheck[i] = Integer.parseInt(Schildcheck[i]);
				System.out.println(childcheck[i]);
				boolean a = sellDao.adminmultidelsellComment(childcheck);
				if(a) {
					System.out.println("댓글 삭제 완료");
				}
			}
			resp.sendRedirect("admincontrol?command=selldetail&page=1&seq="+seq);
		}
		else if(command.equals("selldetail")) {
			// 관리자 페이지 게시글 댓글 확인 및 삭제용
			int seq = Integer.parseInt(req.getParameter("seq"));
			int page1 = Integer.parseInt(req.getParameter("page"));
			// 게시판 상세정보를 위한 dto
			sell_Dto dto = sellDao.detailSell(seq);
			System.out.println("관리자 detail 들어가기 전 : " + dto.toString());
			
			// 댓글 갯수(페이징) 및 목록을 가져오기 위한 list
			int count = sellcommentDao.commentCount(seq);
			List<sell_comment_Dto> commentCount = sellcommentDao.getSell_Comment_List(seq);
			
			List<sell_comment_Dto> list = sellcommentDao.getSell_Comment_List(seq, page1);
			req.setAttribute("count", count);
			req.setAttribute("detailsell", dto);
			req.setAttribute("commentlist", list);
			req.setAttribute("commentCount", commentCount);
			req.getRequestDispatcher("admin/sellbbsAdmindetail.jsp").forward(req, resp);
			
		}else if(command.equals("sellcommentsearch")) {
			System.out.println("이것도 해야함.");
			
		}

		// Free bbs Admin Command
		else if(command.equals("freebbslist")) {
	    	 
	    	free_bbsDao dao = free_bbsDao.getInstance();
	  		
	    	  String choice = "";
	    	  String text = "";
	    	  int allPosting = dao.allPosting(choice, text);
	    	  int pageNumber = 1;
	    	  
	    	  if(req.getParameter("pageNumber")!=null) {
	    		  pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
	    	  }
	    	  
	    	  if(req.getParameter("choice")!=null && req.getParameter("text")!=null) {
	    		  choice = req.getParameter("choice");
	    		  text = req.getParameter("text");
	    		  
	    		  
	    		  allPosting = dao.allAdminPosting(choice, text);
	    	  }
	    	  
	    	  System.out.println("freebbslist pageNumber : " + pageNumber);
	    	  System.out.println("freebbslist choice : " + choice);
    		  System.out.println("freebbslist text : " + text);
	    	  
	    	  List<free_bbsDto> list = dao.getAllBbsList(pageNumber, choice, text);
	    	  
	    	  req.setAttribute("allPosting", allPosting);
	    	  req.setAttribute("list", list);
	    	  req.getRequestDispatcher("./admin/freebbsAdmin.jsp").forward(req, resp);

	      }else if(command.equals("multi-bbsdelete")) {
			  System.out.println("command : multi-bbsdelete");
			  free_bbs_comment_likeDao commentLikeDao = free_bbs_comment_likeDao.getInstance();
			  free_bbs_likeDao likeDao = free_bbs_likeDao.getInstance();
			  free_bbs_commentDao commentDao = free_bbs_commentDao.getInstance();
			  free_bbsDao bbsDao = free_bbsDao.getInstance();
			  
			  	
			    String[] _pageDelArr = req.getParameterValues("delete-check");
			    int[] pageDelArr = new int[_pageDelArr.length];
				
				for(int i = 0; i<pageDelArr.length; i++) {
					System.out.println(pageDelArr[i]);
					pageDelArr[i] = Integer.parseInt(_pageDelArr[i]);
					
					//1.댓글 좋아요 delete
					boolean bool1 = commentLikeDao.adminDeleteReplyLike(pageDelArr);
					if(bool1 == true) {
						//2.게시물 좋아요 delete
						boolean bool2 = likeDao.adminDeletePageLike(pageDelArr);
						if(bool2==true) {
								//3.댓글 delete
								boolean bool3 = commentDao.adminDeleteAllReply(pageDelArr);
								if(bool3==true) {
									//4.게시물 delete
									boolean bool4 = bbsDao.adminDeletePage(pageDelArr);
									System.out.println("@관리자페이지(자유게시판) 게시글 삭제 완료!");
								}
						}
					}else {
						System.out.println("@관리자페이지(자유게시판) 게시글 삭제 실패ㅠㅠ");
					}
					//resp.sendRedirect("admincontrol?command=freebbslist");
					req.getRequestDispatcher("admincontrol?command=freebbslist").forward(req, resp);
				}	
		  }
	    
	      else if(command.equals("forbbsDetail")) {
	    	  System.out.println("command : " + command);
	    	  free_bbsDao bbsDao = free_bbsDao.getInstance();
	    	  free_bbs_commentDao commentDao = free_bbs_commentDao.getInstance();
	    	  
	    	  int seqVal = 0;
			  int rPageNumber = 1;
			  int allReply = 0;
			  String choice = "";
			  String text = "";
			  
			  if(req.getParameter("seqVal")!=null) {
				  String _seqVal = req.getParameter("seqVal");
				  seqVal = Integer.parseInt(_seqVal);
			  }
			  if(req.getParameter("rPageNumber")!=null) {
				  String _rPageNumber = req.getParameter("rPageNumber");
				  rPageNumber = Integer.parseInt(_rPageNumber);
				  System.out.println("@관리자컨트롤러 forbbsDetail 댓글페이지 : " + rPageNumber);
			  }
			 
			  System.out.println("@관리자컨트롤러 forbbsDetail 게시글번호 : " + seqVal);
			  
			  allReply = commentDao.getAdminCommentListCnt(seqVal, choice, text);
			  
			  if(req.getParameter("choice")!=null && req.getParameter("text")!=null) {
	    		  choice = req.getParameter("choice");
	    		  text = req.getParameter("text");
	    		  System.out.println("choice : " + choice);
	    		  System.out.println("text : " + text);
	    	  }
			  
			  List<free_bbs_commentDto> replyList = commentDao.getAdminBbsCommentList(seqVal, rPageNumber, choice, text);
			  free_bbsDto bbsdto = bbsDao.getDetailBbs(seqVal);
			  
			  int replyCount = commentDao.getAdminCommentListCnt(seqVal, choice, text);
			  
			  req.setAttribute("replyCount", replyCount);			  
			  req.setAttribute("bbsdto", bbsdto);
			  req.setAttribute("seqVal", seqVal);
			  req.setAttribute("allReply", allReply);
			  req.setAttribute("replyList", replyList);
	    	  req.getRequestDispatcher("./admin/freebbsDetailAdmin.jsp").forward(req, resp);
			  
		  }
		//자유게시판 관리 댓글삭제
	      else if(command.equals("multi-replyDelete")) {
				
	    	  int page = 1;
			  page = Integer.parseInt(req.getParameter("page"));
			  System.out.println("page seq : " + page);
	    	  
			  free_bbs_comment_likeDao commentLikeDao = free_bbs_comment_likeDao.getInstance();
			  free_bbs_likeDao likeDao = free_bbs_likeDao.getInstance();
			  free_bbs_commentDao commentDao = free_bbs_commentDao.getInstance();
			  free_bbsDao bbsDao = free_bbsDao.getInstance();
			  
			    String[] _replyDelArr = req.getParameterValues("delete-check");
			    int[] replyDelArr = new int[_replyDelArr.length];
				
				for(int i = 0; i<replyDelArr.length; i++) {
					System.out.println(replyDelArr[i]);
					replyDelArr[i] = Integer.parseInt(_replyDelArr[i]);
					
					//1.댓글 좋아요 delete
					boolean bool1 = commentLikeDao.adminDeleteReplyLike2(replyDelArr);
					if(bool1 == true) {
						//2.댓글 delete
						boolean bool2 = commentDao.adminDeleteAllReply2(replyDelArr);
						System.out.println("@관리자페이지(자유게시판) 댓글 삭제 완료!");
					}else {
						System.out.println("@관리자페이지(자유게시판) 댓글 삭제 실패..");
					}
				//resp.sendRedirect("admincontrol?command=forbbsDetail&seqVal="+page);
					req.getRequestDispatcher("admincontrol?command=forbbsDetail&seqVal="+page).forward(req, resp);
					 
					
				}	
				
		  }
		
		
		// Calendar Admin Command
		else if(command.equals("calendarlist")) {
			List<CalendarDto> list = caldao.getDayList();

	    	 String year = req.getParameter("year");
	    	 String month = req.getParameter("month");

	    	 req.setAttribute("list", list);
	    	 req.setAttribute("year", year);
	    	 req.setAttribute("month", month);

	         req.getRequestDispatcher("./admin/calendarAdmin.jsp").forward(req, resp);
		}else if(command.equals("writecal")) { 
	    	 System.out.println("CalendarControl doGet writecal 입니다");

	    	 String year = req.getParameter("year");
	    	 String month = req.getParameter("month");
	    	 String day = req.getParameter("day");

	    	 req.setAttribute("year", year);
	    	 req.setAttribute("month", month);
	    	 req.setAttribute("day", day);

	         req.getRequestDispatcher("./admin/calwrite.jsp").forward(req, resp);
	         
	      }else if(command.equals("updatecal")) {
	    	System.out.println("CalendarControl doGet updatecal 입니다");
	    	  
			String _seq = req.getParameter("seq");
			int seq = Integer.parseInt(_seq);
			
			CalendarDto dto = caldao.getDay(seq);
			req.setAttribute("cal", dto);
			
			req.getRequestDispatcher("./admin/calupdate.jsp").forward(req, resp);
	      }else if(command.equals("deletecal")) {
	    	  System.out.println("CalendarControl doGet deletecal 입니다");
	    	  
	    	  int seq = Integer.parseInt(req.getParameter("seq"));
	    	  
	    	  boolean bool = false;
	    	  bool = caldao.deleteCalendar(seq);
	    	  resp.setContentType("text/html; charset=UTF-8");
	    	  PrintWriter writer = resp.getWriter();
	    	  
	    	  if(bool) {
	    		writer.println("<script type='text/javascript'>");
	 			writer.println("alert('DELETE SUCCESS');");
	 			writer.println("location.href='admincontrol?command=calendarlist'");
	 			writer.println("</script>");
	    	  }
	      }
		
		// Vote Admin command
	    else if(command.equals("votedetail")) {
	    	
		   String id = req.getParameter("id");
		   System.out.println("vote관리) 회원의 Id: " +id);
		   int bbsLen =  vocomdao.getAllvoteComment(id);
		   System.out.println("회원 댓글 갯수: " +bbsLen);
		   
		   String spage = req.getParameter("Page");
		  
		  
			int page = 0;
			if(spage != null && !spage.equals("") ) {
				page = Integer.parseInt(spage);
			}
			
		   System.out.println("관리자 누른 페이지: "+ page);
		   
		   List<VcommnetDto> list = vocomdao.votePagingList(id, page);
		   
		   req.setAttribute("id", id);
		   req.setAttribute("list", list);
		   req.setAttribute("bbslen", bbsLen+"");
		   req.getRequestDispatcher("./admin/votebbsdetailAdmin.jsp").forward(req, resp);
		   
	   }
		
	   else if(command.equals("votedelete")) {
		   
		   System.out.println("관리자모드 투표삭제");
		   String id = req.getParameter("id");
		   String[] Sseq = req.getParameterValues("seq");
		   
		   int[] seq = new int[Sseq.length];
			  for(int i=0; i<seq.length; i++) {
					seq[i] = Integer.parseInt(Sseq[i]);
					System.out.println("삭제할 seq: " +seq[i]);
					
					resp.setContentType("text/html; charset=UTF-8");
					PrintWriter out = resp.getWriter();
					
					boolean a = vocomdao.managerdelete(seq); 
						  if(a) {
						  
								out.println("<script type='text/javascript'>");
								out.println("alert('성공적으로 댓글을 삭제했습니다');");
								out.println("location.href='admincontrol?command=votedetail&id=" + id + "'");
								out.println("</script>");
								
								out.flush();
						  }
			}
		   
	   }
		
	    else if(command.equals("votelist")) {
			String spage = req.getParameter("Page");
			
			String pickId = req.getParameter("pickId");
			if(pickId != null) {
			System.out.println("votelist에들어온 pickid : " +pickId);
			req.setAttribute("pickId",pickId);
			}
			String count = req.getParameter("count");
			System.out.println(count);
			
			int page = 0;
			if(spage != null && !spage.equals("") ) {
				page = Integer.parseInt(spage);
			}
			
			System.out.println("관리자모드 페이지: " + page);
			
			String snum = req.getParameter("num");
			
			int num = 0 ;
			
			if(snum != null) {
		
				num = Integer.parseInt(snum);
				System.out.println("검색한 번호: " +num);
			}
			
			List<VResultDto> list = redao.VotepagingList(page, num);	
			int bbslen = redao.getAllpick(num);
			
			System.out.println("투표자 총 수:" + bbslen);
			
			req.setAttribute("count", count);
			req.setAttribute("bbslen", bbslen+"");
			req.setAttribute("list", list);
			req.getRequestDispatcher("./admin/votebbsAdmin.jsp").forward(req, resp);
		}
		
		
	    else if(command.equals("lotto")) {
		
			String smvPlayer = (String)req.getParameter("mvPlayer");
			
			String count = "3";
			
			
			int mvPlayer = Integer.parseInt(smvPlayer);
			
			if(mvPlayer > 27) {
			resp.setContentType("text/html; charset=UTF-8"); 
			 
			PrintWriter out = resp.getWriter();
			out.println("<script>alert('선수의 번호가 아닙니다. 다시입력해주세요');");
			out.println("location.href='admincontrol?command=votelist'");
			out.println("</script>");
			out.flush();
			}
			
			int all = redao.allwinner(mvPlayer);
			System.out.println("mvp맞춘 회원들: " + all);
			
			if(all > 0) {
			int rdNumber = (int)(Math.random() * all ) + 1;
			System.out.println("나온 랜덤넘버 : " + rdNumber);
			
			VResultDto dto = redao.FindWinner(mvPlayer, rdNumber);
			String pickId = dto.getId().trim();
			System.out.println("추첨 결과: " + pickId );
			
			String content = "축하드립니다 ! mvp 맞추기 이벤트에 당첨되셨습니다 ! " ;
			
			redao.winmessage( pickId, "manager" , content );
			
			resp.sendRedirect("admincontrol?command=votelist&pickId="+pickId+"&count="+count);
		}
		
		else if(all == 0) {
			System.out.println("맞춘사람 0");
		resp.setContentType("text/html; charset=UTF-8"); 
			 
		 PrintWriter out = resp.getWriter();
		 out.println("<script>alert('mvp선수를 맞춘 회원이 없습니다');");
		 out.println("location.href='admincontrol?command=votelist'");
		 out.println("</script>");
				 out.flush();
			}
			
		}
	
		
		// MyPage Admin command
		else if(command.equals("msgbox")) {
			String id = user.getId();
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
			
			String select = req.getParameter("search-select");
			String str = req.getParameter("search-text");
			
			req.setAttribute("select", select);
			req.setAttribute("text", str);
			
			if(str != null && select.equals("id")) {
				str = memDao.getId(str);
			}
			
			int totalPages = msgDao.getPageCount(type, id, select, str);
			List<MessageDto> list = msgDao.getMsgList(type, page, id, select, str);
			
			req.setAttribute("msglist", list);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("page", page);
			
			if(type.equals("receive")) {
			req.getRequestDispatcher("./admin/myrecvmsgAdmin.jsp").forward(req, resp);
			}else if(type.equals("send")) {
			req.getRequestDispatcher("admin/mysendmsgAdmin.jsp").forward(req, resp);
			}
		}else if(command.equals("multi-msgdelete")) {
			String sseq[] = req.getParameterValues("delete-check");
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
				out.println("location.href='admincontrol?command=msgbox&type=" + type + "'");
				out.println("</script>");
				
				out.flush();
			}
		}else if(command.equals("admininfo")) {
			req.getRequestDispatcher("./admin/myinfoAdmin.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String command = req.getParameter("command");
	    
		iMember memDao = MemberDao.getInstance();
		iMessage msgDao = MessageDao.getInstance();
		iCalendar calDao = CalendarDao.getInstance();
		
		//Member Admin command
		
		if(command.equals("writemsg")) {
			
			HttpSession session = req.getSession();
			MemberDto user = (MemberDto)session.getAttribute("login");
			
			String from_nick = user.getNickname();
			String to_nick = req.getParameter("to_nick");
			String content = req.getParameter("content");
			
			String to_id = memDao.getId(to_nick);
			String from_id = memDao.getId(from_nick);
			
			MessageDto dto = new MessageDto(to_id, from_id, content);
			System.out.println(dto.toString());
			
			boolean isS = msgDao.writeMsg(dto);
			
			PrintWriter out = resp.getWriter();
			out.println(isS);
			out.flush();
		}else if(command.equals("pwdupdate")) {
			String pwd = req.getParameter("pwd");
			HttpSession sess = req.getSession();
			String id = ((MemberDto)sess.getAttribute("login")).getId();
			
			boolean isS = memDao.pwdUpdate(id, pwd);
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out = resp.getWriter();
			
			if(isS) {
				out.println("<script type='text/javascript'>");
				out.println("alert('비밀번호가 변경되었습니다. 다시 로그인해주세요.');");
				out.println("location.href='memcontrol?command=logout'");
				out.println("</script>");
			}
		}
		
		
	    //Calendar Admin command
		else if (command.equals("writeAf")) {
	    	System.out.println("doPost dao 진입");
	   		
	   		String team = req.getParameter("team");
	   		String stadium = req.getParameter("stadium");
	   		String year = req.getParameter("year");
   			String month = req.getParameter("month");
   			String day = req.getParameter("day");
   			String hour = req.getParameter("hour");
   			String min = req.getParameter("min");
   			
			
			String sche = String.format("%s%s%s%s%s", year, utilMethod.two(month),
			 utilMethod.two(day), utilMethod.two(hour), utilMethod.two(min));
   			
   			String outcome=req.getParameter("outcome");
   			String memo=req.getParameter("memo");
   		
   			
   			
   			CalendarDto dto = new CalendarDto(team, stadium, sche, outcome, memo);
   			
   			boolean bool = false;
   			bool = calDao.addCalendar(dto);
   			
   			System.out.println("write success? : " + bool);
   			resp.setContentType("text/html; charset=UTF-8");
   			PrintWriter writer = resp.getWriter();
   			
   			if(bool == true){
   				writer.println("<script type='text/javascript'>");
   				writer.println("alert('WRITE SUCCESS');");
   				writer.println("location.href='admincontrol?command=calendarlist&year=" + year + "&month=" + month + "'");
   				
   				writer.println("</script>");
   				
   			}else {
   				writer.println("<script type='text/javascript'>");
   				writer.println("alert('WRITE FAIL');");
   				writer.println("history.back();");
   				writer.println("</script>");
   			}
   		}else if (command.equals("updateAf")) {
   			System.out.println("command updateAf");
   			
   			int seq = Integer.parseInt(req.getParameter("seq"));
   			String team = req.getParameter("team");
   			String stadium = req.getParameter("stadium");
   			String year = req.getParameter("year");
   			String month = req.getParameter("month");
   			String day = req.getParameter("day");
   			String hour = req.getParameter("hour");
   			String min = req.getParameter("min");
   			
   			String sche = String.format("%s%s%s%s%s", year, utilMethod.two(month), 
   							utilMethod.two(day), utilMethod.two(hour), utilMethod.two(min));
   			
   			String outcome=req.getParameter("outcome");
   			String memo=req.getParameter("memo");
   		
   			
   			System.out.println("update team : " + team);
   			System.out.println("update stadium : " + stadium);
   			
   			CalendarDto dto = new CalendarDto(seq, team, stadium, sche, outcome, memo);
   			
   			boolean bool = false;
   			bool = calDao.updateCalendar(dto);
   			
   			System.out.println("update success? : " + bool);
   			resp.setContentType("text/html; charset=UTF-8");
   			PrintWriter writer = resp.getWriter();
   			
   			if(bool == true){
   				writer.println("<script type='text/javascript'>");
   				writer.println("alert('UPDATE SUCCESS');");
   				writer.println("location.href='admincontrol?command=calendarlist&year=" + year + "&month=" + month + "'");
   				writer.println("</script>");
   				
   				resp.sendRedirect("admincontrol?command=calendarlist&year=" + year + "&month=" + month + "");
   				
   			}else {
   				writer.println("<script type='text/javascript'>");
   				writer.println("alert('UPDATE FAIL');");
   				writer.println("history.back();");
   				writer.println("</script>");
   			}
   		}
	}
	
	
	
}
