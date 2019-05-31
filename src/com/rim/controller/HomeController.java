package com.rim.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.member.MemberDTO;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/HomeController")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = request.getParameter("s");
		
		if(s!=null && s.equals("2")) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId("admin");
			request.getSession().setAttribute("member", memberDTO);
		}else if(s!=null && s.equals("1")) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId("banana");
			request.getSession().setAttribute("member", memberDTO);
		}else if(s!=null && s.equals("3")) {
			request.getSession().invalidate();
		}
		
		System.out.println("index");
		RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/views/index.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
