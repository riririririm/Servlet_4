package com.rim.board.qna;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rim.action.Action;
import com.rim.action.ActionForward;
import com.rim.board.BoardDTO;
import com.rim.page.SearchMakePage;
import com.rim.page.SearchPager;
import com.rim.page.SearchRow;
import com.rim.upload.UploadDAO;
import com.rim.upload.UploadDTO;
import com.rim.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class QnaService implements Action {
	private QnaDAO qnaDAO;
	private UploadDAO uploadDAO;
	
	public QnaService() {
		qnaDAO = new QnaDAO();
		uploadDAO = new UploadDAO();
	}
	
	
	public ActionForward reply(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		int curPage=1;
		
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}catch (Exception e) {
			// TODO: handle exception
		}
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		
		SearchMakePage searchMakePage = new SearchMakePage(curPage, kind, search);
		
		//row
		 SearchRow searchRow = searchMakePage.makeRow();
		//page
		 int totalCount=0;
		 Connection con = null;
		try {
			con = DBConnector.getConnection();
			totalCount = qnaDAO.getTotalCount(searchRow, con);
			List<BoardDTO> ar = qnaDAO.selectList(searchRow, con);
			System.out.println(ar.size());
			request.setAttribute("list", ar);
			request.setAttribute("board", "qna");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 SearchPager searchPager = searchMakePage.makePage(totalCount);
		 request.setAttribute("pager", searchPager);
		 
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/board/boardList.jsp");
		
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		BoardDTO boardDTO=null;
		List<UploadDTO> ar = null;
		Connection con = null;
		try {
			con = DBConnector.getConnection();
			int num = Integer.parseInt(request.getParameter("num"));
			boardDTO = qnaDAO.select(num, con);
			ar = uploadDAO.selectList(num, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String path="";
		if(boardDTO != null) {
			request.setAttribute("dto", boardDTO);
			request.setAttribute("upload", ar);
			path ="../WEB-INF/views/board/boardSelect.jsp";
		}else {
			request.setAttribute("message", "No Data");
			request.setAttribute("path", "./qnaList");
			path="../WEB-INF/views/common/result.jsp";
		}
		//글이 있으면 출력
		//글이 없으면 삭제되었거나 없는 글입니다.(alert) 리스트로 
		actionForward.setCheck(true);
		actionForward.setPath(path);
		return actionForward;
	}


	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/board/boardWrite.jsp");
		String method = request.getMethod();
		if(method.equals("POST")) {
			String saveDirectory = request.getServletContext().getRealPath("upload");
			
			File file = new File(saveDirectory);
			if(!file.exists()) {
				file.mkdirs();
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
			 	QnaDTO qnaDTO = new QnaDTO();
			 	qnaDTO.setTitle(multipartRequest.getParameter("title"));
			 	qnaDTO.setWriter(multipartRequest.getParameter("writer"));
			 	qnaDTO.setContents(multipartRequest.getParameter("contents"));
			 	
			 	con = DBConnector.getConnection();
			 	
			 	//1.시퀀스번호
			 	int num = qnaDAO.getNum();
			 	qnaDTO.setNum(num);
			 	con.setAutoCommit(false);
			 	//2. qna insert
			 	num = qnaDAO.insert(qnaDTO, con);
			 	
			 	//3. upload insert
			 	for(UploadDTO uploadDTO:ar) {
			 		uploadDTO.setNum(qnaDTO.getNum());
			 		num = uploadDAO.insert(uploadDTO, con);
			 		if(num<1) {
			 			throw new Exception();
			 		}
			 	}
			 	
			 	con.commit();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
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
			actionForward.setPath("./qnaList");
			
		}//post끝
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/board/boardUpdate.jsp");
		String method = request.getMethod();
		
		if(method.equals("POST")) {
			//MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		}else {
			int num = Integer.parseInt(request.getParameter("num"));
			Connection con=null;
			BoardDTO boardDTO = null;
			List<UploadDTO> ar = null;
			try {
				con = DBConnector.getConnection();
				boardDTO=qnaDAO.select(num, con);
				ar = uploadDAO.selectList(num, con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//finally
			request.setAttribute("dto", boardDTO);
			request.setAttribute("upload", ar);
		}//GET
		
		
		
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
