package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.MemberDao;
import member.MemberDto;
import member.iMember;

public class MemberControl extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String command = req.getParameter("command");
		iMember dao = MemberDao.getInstance();
		
		if(command.equals("main")) {
			resp.sendRedirect("./main/main.jsp");
		}else if(command.equals("login")) {
			resp.sendRedirect("./login/login.jsp");
		}else if(command.equals("logout")) {
			HttpSession session = req.getSession();
			session.removeAttribute("login");
			resp.sendRedirect("./main/main.jsp");
		}else if(command.equals("account")) {
			resp.sendRedirect("./login/account.jsp");
		}else if(command.equals("idcheck")) {
			String id = req.getParameter("id");
			System.out.println("id = " + id);
			
			boolean isExist = dao.duplicateCheck("id", id.trim());
			System.out.println(isExist);
			PrintWriter out = resp.getWriter();
			out.println(isExist);
			out.flush();
		}else if(command.equals("nickcheck")) {
			String nickname = req.getParameter("nickname");
			String type = req.getParameter("type");
			
		//	System.out.println("nickname = " + nickname);
			
			if(type != null) {
				HttpSession session = req.getSession();
				MemberDto user = (MemberDto)session.getAttribute("login");
			//	System.out.println("userDto : " + user.toString());
				if(user.getNickname().equals(nickname)) {
					PrintWriter out = resp.getWriter();
					out.println("false");
					out.flush();
					return;
				}
			}
			
			boolean isExist = dao.duplicateCheck("nickname", nickname.trim());
			System.out.println(isExist);
			PrintWriter out = resp.getWriter();
			out.println(isExist);
			out.flush();
		}else if(command.equals("emailcheck")) {
			String email = req.getParameter("email");
			String type = req.getParameter("type");
			
			if(type != null) {
				HttpSession session = req.getSession();
				MemberDto user = (MemberDto)session.getAttribute("login");
			//	System.out.println("userDto : " + user.toString());
				if(user.getEmail().equals(email)) {
					PrintWriter out = resp.getWriter();
					out.println("false");
					out.flush();
					return;
				}
			}
			
		//	System.out.println("email = " + email);
			
			boolean isExist = dao.duplicateCheck("email", email.trim());
			System.out.println(isExist);
			PrintWriter out = resp.getWriter();
			out.println(isExist);
			out.flush();
		} else if(command.equals("idfind")) {
			String email = req.getParameter("email");
			
			String id = dao.idfind(email);
			
			resp.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.println(id);
			out.flush();
		} else if(command.equals("pwdfind")) {
			String id = req.getParameter("id");
			
			boolean isExist = dao.duplicateCheck("id", id);
			if(isExist) {
				dao.tempPassword(id);
				MemberDto dto = dao.getMemberDto(id);
				dao.sendEmail(dto);
				System.out.println("이메일 전송 완료");
			}
			
			resp.setContentType("text/html; charset=UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.println(isExist);
			out.flush();
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String command = req.getParameter("command");
		iMember dao = MemberDao.getInstance();
		
		if(command.equals("accountAf")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String nickname = req.getParameter("nickname");
			String name = req.getParameter("name");
			int postnum = Integer.parseInt(req.getParameter("postnum"));
			String address = req.getParameter("address");
			String address_detail = req.getParameter("address_detail");
			String email = req.getParameter("email");
			
			MemberDto dto = new MemberDto(id, pwd, nickname, name, postnum, address, address_detail, email);
			
			boolean isS = dao.addMember(dto);
			
			if(isS) {
				resp.sendRedirect("./login/login.jsp");
			}else if(!isS) {
				resp.sendRedirect("./login/account.jsp");
			}
			
		}else if(command.equals("loginAf")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			
			boolean isS = dao.login(id, pwd);
			PrintWriter out = resp.getWriter();
			
			if(isS) {
				HttpSession session = req.getSession();
				
				if(session.getAttribute("login") == null) {
					System.out.println("새로운 세션을 생성합니다.");
					MemberDto user = dao.getMemberDto(id);
					System.out.println(user.toString());
					session.setAttribute("login", user);
					session.setMaxInactiveInterval(60 * 60 * 30);
					
					if(user.getAuth() == 1) {
						out.println("admin");
					}else if(user.getAuth() == 3) {
						out.println("member");
					}
				};
			}else {
				out.println(isS);
			}
			
		}else if(command.equals("update")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			String nickname = req.getParameter("nickname");
			String name = req.getParameter("name");
			int postnum = Integer.parseInt(req.getParameter("postnum"));
			String address = req.getParameter("address");
			String address_detail = req.getParameter("address_detail");
			String email = req.getParameter("email");
			
			MemberDto dto = new MemberDto(id, pwd, nickname, name, postnum, address, address_detail, email);
			
			boolean isS = dao.updateMember(dto);
			
			if(isS) {
				
				resp.setContentType("text/html; charset=UTF-8"); 
				
				PrintWriter out = resp.getWriter();
				out.println("<script type='text/javascript'>alert('회원정보 수정을 성공했습니다. 다시 로그인해주시기 바랍니다'); "
						+ " location.href='memcontrol?command=logout'</script>");
				out.flush();
				
				
			}else {
				
			}
		}else if(command.equals("withdrawal-check")) {
			String id = req.getParameter("id");
			String pwd = req.getParameter("pwd");
			
			boolean isS = dao.login(id, pwd);
			PrintWriter out = resp.getWriter();
			out.println(isS);
		}
	}
	
}
