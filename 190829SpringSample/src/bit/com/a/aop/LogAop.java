package bit.com.a.aop;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import bit.com.a.model.MemberDto;

@Aspect
public class LogAop {
	
	
	// @Around("within(bit.com.a.controller.*)")	컨트롤러만 감시한다
	@Around("within(bit.com.a.controller.*)")
	public Object loggerAop(ProceedingJoinPoint joinpoint) throws Throwable {
		
		String signatureStr = joinpoint.getSignature().toShortString();
		
		/*
		HttpServletRequest req = null;
		HttpServletResponse resp = null;
		
		for ( Object o : joinpoint.getArgs() ) {
			if( o instanceof HttpServletRequest ) {
				req = (HttpServletRequest)o;
			}
			if ( o instanceof HttpServletResponse )
				resp = (HttpServletResponse)o;
		}
		
		try {
		System.out.println("loggerAop: " + signatureStr + "메소드가 실행 되었습니다.");
		if(req != null) {
		
		if(req.getSession().getAttribute("login") == null
				|| req.getSession().getAttribute("login").equals("")) {
			System.out.println("로그인이 필요합니다.");
			
			throw new RuntimeException("로그인을 하셔야 합니다");
			}
		}
			// 실행 전 처리
		Object obj = joinpoint.proceed();	// 메소드가 실행 된
		return obj;
		
		}finally {
			
			// 실행 후 처리
		//	System.out.println("loggerAop: " + signatureStr + "메소드가 종료 되었습니다.");
		}
		*/
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		// request 취득
		HttpServletRequest req = 
			((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		HttpServletResponse resp = 
				((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getResponse();
		
		if(req != null) {
			HttpSession session = req.getSession();
			
			MemberDto dto = (MemberDto)session.getAttribute("login");
			if(dto == null ) {
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script> alert('로그인이 필요한 페이지 입니다..');"
						+ "location.href='login.do'; </script>");
				out.flush();
				out.close();
				}
			}
			else {
				System.out.println("loginId : null");
		}
		
		Object obj = joinpoint.proceed();
		stopWatch.stop();
		System.out.println("loggerAop: " + signatureStr + "메소드가 실행 되었습니다." + stopWatch.getTotalTimeMillis());
		
		return obj;
	}
}
