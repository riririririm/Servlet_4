package com.rim.board.contents;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.action.Action;
import com.rim.action.ActionForward;
import com.rim.page.SearchMakePage;
import com.rim.page.SearchRow;
import com.rim.util.DBConnector;

public class CommentsService implements Action {
	private CommentsDAO commentsDAO;
	
	public CommentsService() {
		commentsDAO = new CommentsDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		int curPage =1;
		int num=0;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
			
			//ar = commentsDAO.selectList(searchRow, conn);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		num=Integer.parseInt(request.getParameter("num"));
		
		SearchMakePage searchMakePage = new SearchMakePage(curPage, "", "");
		SearchRow searchRow = searchMakePage.makeRow();
		
		Connection conn = null;
		List<CommentsDTO> ar = null;
		try {
			conn = DBConnector.getConnection();
			ar =commentsDAO.selectList(searchRow, conn, num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		request.setAttribute("commentsList", ar);
		
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/list.jsp");
		
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setNum(Integer.parseInt(request.getParameter("num")));
		commentsDTO.setWriter(request.getParameter("writer"));
		commentsDTO.setContents(request.getParameter("contents"));
		
		Connection conn=null;
		int result=0;
		try {
			conn = DBConnector.getConnection();
			result = commentsDAO.insert(commentsDTO, conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		request.setAttribute("result", result);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		CommentsDTO commentsDTO  = new CommentsDTO();
		commentsDTO.setCnum(Integer.parseInt(request.getParameter("cnum")));
		commentsDTO.setContents(request.getParameter("contents"));
		
		Connection conn = null;
		int result=0;
		try {
			conn = DBConnector.getConnection();
			result= commentsDAO.update(commentsDTO, conn);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		request.setAttribute("result", result);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		Connection conn = null;
		int result=0;
		try {
			int cnum = Integer.parseInt(request.getParameter("cnum"));
			conn = DBConnector.getConnection();
			result= commentsDAO.delete(cnum, conn);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		request.setAttribute("result",result);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		return actionForward;
	}

}
