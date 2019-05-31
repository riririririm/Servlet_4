package com.rim.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rim.member.MemberDTO;

/**
 * Servlet Filter implementation class Filtertest1
 */
@WebFilter("/Filtertest1")
public class Filtertest1 implements Filter {
	private HashMap<String, Boolean> map;
    /**
     * Default constructor. 
     */
    public Filtertest1() {
        map= new HashMap<String, Boolean>();
        map.put("/noticeList",false);
        map.put("/noticeSelect",false);
        map.put("/noticeWrite",true);
        map.put("/noticeUpdate",true);
        map.put("/noticeDelete",true);
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		
		//request
		HttpSession session = ((HttpServletRequest)request).getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		String command = ((HttpServletRequest)request).getPathInfo();
		boolean check = map.get(command);
		if(check) {
			//관리자만 통과
			if(memberDTO != null && memberDTO.getId().equals("admin")) {
				chain.doFilter(request, response);
			}else {
				((HttpServletResponse)response).sendRedirect("../index.do");
			}
			
		}else {
			chain.doFilter(request, response);
		}
	
		System.out.println("Filter in");
		
		//response
		System.out.println("Filter out");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
