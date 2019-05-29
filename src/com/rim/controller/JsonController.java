package com.rim.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.json.JsonService;

/**
 * Servlet implementation class JsonController
 */
@WebServlet("/JsonController")
public class JsonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JsonService jsonService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonController() {
        super();
        jsonService = new JsonService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getPathInfo();
		System.out.println(command);
		if(command.equals("/jsonTest1")) {
			jsonService.test1(request, response);
		}else if(command.equals("/jsonTest2")) {
			jsonService.test2(request, response);
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
