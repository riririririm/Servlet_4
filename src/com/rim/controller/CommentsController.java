package com.rim.controller;

import java.io.IOException;

import javax.print.attribute.ResolutionSyntax;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.action.ActionForward;
import com.rim.board.contents.CommentsDAO;
import com.rim.board.contents.CommentsService;

/**
 * Servlet implementation class CommentsController
 */
@WebServlet("/CommentsController")
public class CommentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CommentsService commentsService;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsController() {
        super();
        commentsService = new CommentsService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getPathInfo();
		ActionForward actionForward =null;
		
		if(command.equals("/commentsWrite")) {
			System.out.println("write");
			actionForward = commentsService.insert(request, response);
		}else if(command.equals("/commentsDelete")) {
			actionForward = commentsService.delete(request, response);
		}else if(command.equals("/commentsUpdate")) {
			actionForward = commentsService.update(request, response);
		}else if(command.equals("/commentsList")) {
			actionForward = commentsService.list(request, response);
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
