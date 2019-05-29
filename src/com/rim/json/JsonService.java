package com.rim.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.rim.board.BoardDTO;
import com.rim.board.contents.CommentsDAO;
import com.rim.board.contents.CommentsDTO;
import com.rim.board.notice.NoticeDAO;
import com.rim.page.SearchMakePage;
import com.rim.page.SearchRow;
import com.rim.util.DBConnector;

public class JsonService {
	
	public void test2(HttpServletRequest request, HttpServletResponse response){
		CommentsDAO commentsDAO = new CommentsDAO();
		int num = Integer.parseInt(request.getParameter("num"));
		SearchMakePage s = new SearchMakePage(1,"","");
		SearchRow searchRow = s.makeRow();
		Connection con=null;
		List<CommentsDTO> ar=null;
		try {
			con = DBConnector.getConnection();
			ar = commentsDAO.selectList(searchRow, con, num);
			String result="[";
//			for(int i=0;i<ar.size();i++) {
//				result= result+"{\"writer\":\""+ar.get(i).getWriter()+"\",";
//				result = result+"\"contents\":\""+ar.get(i).getContents()+"\",";
//				result = result+"\"cnum\":\""+ar.get(i).getCnum()+"\"},";
//			}
//			result= result+"]";
			
			JSONArray jsonar = new JSONArray();
			for(CommentsDTO commentsDTO:ar) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("writer",commentsDTO.getWriter());
				jsonObject.put("contents",commentsDTO.getContents());
				jsonar.add(jsonObject);
			}
			
			JSONObject js = new JSONObject();
			js.put("ar", jsonar);
			
			PrintWriter out = response.getWriter();
			out.println(js.toJSONString());
			out.close();
			
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
	}

	public void test1(HttpServletRequest request, HttpServletResponse response) {
		int num = Integer.parseInt(request.getParameter("num"));
//		Random random = new Random();
//		int result = random.nextInt(num);
	
		NoticeDAO noticeDAO = new NoticeDAO();
		Connection con =null;
		
		try {
			con =DBConnector.getConnection();
			BoardDTO boardDTO = noticeDAO.select(num, con);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("writer:",boardDTO.getWriter());
			jsonObject.put("contents:",boardDTO.getContents());
			jsonObject.put("title:",boardDTO.getTitle());
			
			PrintWriter out = response.getWriter();
			out.print(jsonObject.toJSONString());
			
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
		
		
		
		
	}
}
