package com.rim.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.action.Action;
import com.rim.action.ActionForward;
import com.rim.upload.UploadDAO;
import com.rim.upload.UploadService;

/**
 * Servlet implementation class UploadController
 */
@WebServlet("/UploadController")
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UploadService uploadService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadController() {
        super();
        uploadService = new UploadService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getPathInfo();
		ActionForward actionForward = null;
		
		if(command.equals("/fileDelete")) {
			actionForward= uploadService.delete(request, response);
		}else if(command.equals("/fileUpload")){
			actionForward = uploadService.insert(request, response);
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
