package it.qube.core;




import it.qube.persistence.dto.Users;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthenticationFilter implements Filter{

	public static final String AFTER_LOGIN_PAGE = "afterLoginPage";
	private static final String TIMEOUT_ZUL = "/timeout.zul";
	public static final String LOGIN_PAGE = "/login.zul";
	public static final String LOGIN_RESET = "/loginreset.zul";
	public static final String LOGGED_USER = "~LOGGED~USER~";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	// /zkau /img "/zkau",

	private static final Set<String> excludeSet = new HashSet<String>();

	private static final String[] exclude = new String[]{"/img","/zkau"};

	static{
		excludeSet.add(TIMEOUT_ZUL);
		excludeSet.add(LOGIN_PAGE);
		excludeSet.add(LOGIN_RESET);
	}

	public static boolean toExclude(String path){
		if (path==null)
			return true;
		if (excludeSet.contains(path))
			return true;
		for (int i = 0; i < exclude.length; i++) {
			if (path.startsWith(exclude[i]))
				return true;
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest)req;
			HttpServletResponse response = (HttpServletResponse) resp;
			String path = request.getServletPath();
			if (toExclude(path)){
				chain.doFilter(req,resp);
				return;
			}
			Users user = (Users)request.getSession().getAttribute(LOGGED_USER);
			if (user==null){
				RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_PAGE);
				request.setAttribute(AFTER_LOGIN_PAGE, path);
				dispatcher.forward(request, response);
			}
		}
		chain.doFilter(req,resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

}
