package com.rim.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.action.ActionForward;
import com.rim.member.MemberService;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        memberService=new MemberService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getPathInfo();
		ActionForward actionForward = null;
		
		if(command.equals("/memberCheck")) {
			actionForward = memberService.check(request,response);
		}
		else if(command.equals("/idCheck")) {
			actionForward = memberService.idCheck(request,response);
		}
		else if(command.equals("/memberJoin")) {
			actionForward = memberService.insert(request,response);
		}
		else if(command.equals("/memberLogin")) {
			actionForward = memberService.select(request,response);
		}
		else if(command.equals("/memberLogout")) {
			actionForward = memberService.logout(request,response);
		}
		else if(command.equals("/memberPage")) {
			actionForward = memberService.mypage(request,response);
		}
		else if(command.equals("/memberUpdate")) {
			actionForward = memberService.update(request,response);
		}
		else if(command.equals("/memberDelete")) {
			actionForward = memberService.delete(request,response);
		}
		else {
			actionForward = new ActionForward();
		}
		
		if(actionForward.isCheck()) {
			RequestDispatcher view = request.getRequestDispatcher(actionForward.getPath());
			view.forward(request, response);
		}else {
			response.sendRedirect(actionForward.getPath());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
