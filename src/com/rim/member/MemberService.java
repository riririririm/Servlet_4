package com.rim.member;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.rim.action.Action;
import com.rim.action.ActionForward;
import com.rim.upload.MUploadDAO;
import com.rim.upload.MUploadDTO;
import com.rim.upload.UploadDTO;
import com.rim.util.DBConnector;

public class MemberService implements Action {
	private MemberDAO memberDAO;
	private MUploadDAO mUploadDAO;

	public MemberService() {
		memberDAO = new MemberDAO();
		mUploadDAO = new MUploadDAO();
	}

	
	public ActionForward idCheck(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		String id = request.getParameter("id");
		
		Connection conn;
		int check=0; //사용불가한 id
		try {
			conn = DBConnector.getConnection();
			check =memberDAO.idCheck(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("result", check);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		return actionForward;		
	}
	public ActionForward check(HttpServletRequest request, HttpServletResponse response) {
		// 약관동의

		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/member/memberCheck.jsp"); // /notice/noticeUpdate이니까 현재 위치는 /notice >>
																			// 루트로 가려면 ../
		return actionForward;

	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		// 회원가입
		ActionForward actionForward = new ActionForward();

		String method = request.getMethod();
		boolean check = true;
		String path = "../WEB-INF/views/member/memberJoin.jsp";
		if (method.equals("POST")) {
			MemberDTO memberDTO = new MemberDTO();

			String saveDirectory = request.getServletContext().getRealPath("/upload");
			System.out.println(saveDirectory);

			int maxPostSize = 1024 * 1024 * 10;
			String encoding = "UTF-8";
			MultipartRequest multi = null;

			try {
				multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding,
						new DefaultFileRenamePolicy());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 파일 저장이 됨
			String fileName = multi.getFilesystemName("f1");// 파일의 파라미터 이름
			// 클라이언트가 저장한 이름
			String oName = multi.getOriginalFileName("f1");// 파일의 파라미터 이름

			MUploadDTO mUploadDTO = new MUploadDTO();
			mUploadDTO.setFname(fileName);
			mUploadDTO.setOname(oName);

			memberDTO.setId(multi.getParameter("id"));
			memberDTO.setPw(multi.getParameter("pw"));
			memberDTO.setName(multi.getParameter("name"));
			memberDTO.setEmail(multi.getParameter("email"));
			memberDTO.setPhone(multi.getParameter("phone"));
			memberDTO.setAge(Integer.parseInt(multi.getParameter("age")));

			int result = 0;
			Connection conn = null;
			try {
				conn = DBConnector.getConnection();

				// auto commit해제
				conn.setAutoCommit(false);

				result = memberDAO.memberJoin(memberDTO, conn);

				mUploadDTO.setId(memberDTO.getId());

				result = mUploadDAO.insert(mUploadDTO, conn);
				if (result < 1) {// img업로드 실패시
					throw new Exception();
				}
				conn.commit(); // 성공하면 커밋

			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					result = 0;
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				// 예외 발생하든 안하든 실행
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (result > 0) {
				check = false;// redirect
				path = "../index.do";
			} else {
				request.setAttribute("message", "회원가입 실패");
				request.setAttribute("path", "./memberCheck");
				check = true;
				path = "../WEB-INF/views/common/result.jsp";
			}
		}
		actionForward.setCheck(check);
		actionForward.setPath(path); // /notice/noticeUpdate이니까 현재 위치는 /notice >> 루트로 가려면 ../
		return actionForward;
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		// 로그인
		ActionForward actionForward = new ActionForward();

		String method = request.getMethod();
		boolean check = true;
		String path = "../WEB-INF/views/member/memberLogin.jsp";

		if (method.equals("POST")) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId(request.getParameter("id"));
			memberDTO.setPw(request.getParameter("pw"));

			// int result=0;
			Connection conn = null;
			try {
				conn = DBConnector.getConnection();

				memberDTO = memberDAO.memberLogin(memberDTO, conn);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally {
				// 예외 발생하든 안하든 실행
				try {

					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (memberDTO != null) {
				//session
				request.getSession().setAttribute("member", memberDTO);
				check = false;// redirect
				path = "../index.do";
			} else {
				request.setAttribute("message", "로그인 실패");
				request.setAttribute("path", "./memberLogin");
				check = true;
				path = "../WEB-INF/views/common/result.jsp";
			}

		} // end of post

		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;

	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		ActionForward actionForward = new ActionForward();
		
		String method = request.getMethod();
		boolean check=true;
		String path="../WEB-INF/views/member/memberUpdate.jsp";
		if(method.equals("POST")) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId(request.getParameter("id"));
			memberDTO.setPw(request.getParameter("pw"));
			memberDTO.setName(request.getParameter("name"));
			memberDTO.setPhone(request.getParameter("phone"));
			memberDTO.setEmail(request.getParameter("email"));
			memberDTO.setAge(Integer.parseInt(request.getParameter("age")));
			
			Connection conn = null;
			int result =0;
			try {
				conn=DBConnector.getConnection();
				result= memberDAO.memberUpdate(memberDTO, conn);
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
			
			if(result>0) {
				request.getSession().setAttribute("member", memberDTO);
				check=false;
				path="./memberPage";
			}else {
				check=false;
				path="./memberUpdate";
			}
			
			
		}//end of post
		
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		boolean check=true;
		String path="../WEB-INF/views/member/memberPage.jsp";
		
		String id = request.getParameter("id");
		int result=0;
		Connection conn = null;
		try {
			conn = DBConnector.getConnection();
			 result= memberDAO.memberDelete(id, conn);
			
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				conn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		if(result>0) {
			request.getSession().invalidate();
			check=false;
			path="../index.do";
		}else {
			check=false;
			path="./memberPage";
		}
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		return actionForward;
	}

	public ActionForward mypage(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/member/memberPage.jsp"); 
		return actionForward;
	}
	
	public ActionForward logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		
		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(false);
		actionForward.setPath("../index.do");
		return actionForward;
	}

}
