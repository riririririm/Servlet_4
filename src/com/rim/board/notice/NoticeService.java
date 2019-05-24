package com.rim.board.notice;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.rim.action.Action;
import com.rim.action.ActionForward;
import com.rim.board.BoardDTO;
import com.rim.board.qna.QnaDAO;
import com.rim.board.qna.QnaDTO;
import com.rim.page.SearchMakePage;
import com.rim.page.SearchPager;
import com.rim.page.SearchRow;
import com.rim.upload.UploadDAO;
import com.rim.upload.UploadDTO;
import com.rim.util.DBConnector;

public class NoticeService implements Action {
	private NoticeDAO noticeDAO;
	private UploadDAO uploadDAO;
	
	public NoticeService() {
		noticeDAO = new NoticeDAO();
		uploadDAO = new UploadDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		int curPage =1;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		
		//1. row
		SearchRow searchRow = s.makeRow();
				
		//page
		int totalCount =0;
		Connection conn = null;
		try {
			conn = DBConnector.getConnection();
		
			totalCount= noticeDAO.getTotalCount(searchRow, conn);
			List<BoardDTO> ar = noticeDAO.selectList(searchRow,conn);
			request.setAttribute("list", ar);
			
			SearchPager searchPager = s.makePage(totalCount);
			request.setAttribute("pager", searchPager);
			
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/board/boardList.jsp");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("message", "server error");
			request.setAttribute("path", "../index.do");
		
			actionForward.setCheck(true);
			actionForward.setPath("../WEB-INF/views/common/result.jsp");
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/board/boardWrite.jsp");
		String method = request.getMethod();
		if(method.equals("POST")) {
			String saveDirectory = request.getServletContext().getRealPath("upload");
			File f = new File(saveDirectory);
			if(!f.exists()) {
				f.mkdirs();
			}
			int maxPostSize=1024*1024*100;
			Connection con=null;
			try {
				MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", new DefaultFileRenamePolicy());
				Enumeration<String> e= multipartRequest.getFileNames();//파일의 파라미터 이름들
				ArrayList<UploadDTO> ar = new ArrayList<UploadDTO>();
				while(e.hasMoreElements()) {
					String s = e.nextElement();
					String fname = multipartRequest.getFilesystemName(s);
					String oname = multipartRequest.getOriginalFileName(s);
					UploadDTO uploadDTO = new UploadDTO();
					uploadDTO.setFname(fname);
					uploadDTO.setOname(oname);
					ar.add(uploadDTO);
				}
				NoticeDTO noticeDTO = new NoticeDTO();
				noticeDTO.setTitle(multipartRequest.getParameter("title"));
				noticeDTO.setWriter(multipartRequest.getParameter("writer"));
				noticeDTO.setContents(multipartRequest.getParameter("contents"));

				con = DBConnector.getConnection();

				//1.시퀀스번호
				int num = noticeDAO.getNum();
				noticeDTO.setNum(num);
				con.setAutoCommit(false);
				//2. qna insert
				num = noticeDAO.insert(noticeDTO, con);

				//3. upload insert
				for(UploadDTO uploadDTO:ar) {
					uploadDTO.setNum(noticeDTO.getNum());
					num = uploadDAO.insert(uploadDTO, con);
					if(num<1) {
						throw new Exception();
					}
				}

				con.commit();

			} catch (Exception e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actionForward.setCheck(false);
			actionForward.setPath("./noticeList");

		}//post끝
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
