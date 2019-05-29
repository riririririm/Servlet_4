package com.rim.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.action.ActionForward;
import com.rim.board.qna.QnaService;


/**
 * Servlet implementation class QnaController
 */
@WebServlet("/QnaController")
public class QnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private QnaService qnaService;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnaController() {
        super();
        qnaService = new QnaService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getPathInfo();
		ActionForward actionForward = null;
		if(command.equals("/qnaList")) {
			actionForward=qnaService.list(request, response);
		}
		else if(command.equals("/qnaWrite")) {
			actionForward=qnaService.insert(request, response);
		}
		else if(command.equals("/qnaSelect")) {
			actionForward=qnaService.select(request, response);
		}
		else if(command.equals("/qnaUpdate")) {
			actionForward=qnaService.update(request, response);
		}
		else {
			actionForward = new ActionForward();
		}
		
		request.setAttribute("board", "qna");
		
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
