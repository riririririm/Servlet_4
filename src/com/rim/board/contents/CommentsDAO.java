package com.rim.board.contents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rim.board.contents.CommentsDTO;
import com.rim.page.SearchRow;;

public class CommentsDAO {
	
	public int getTotalCount(SearchRow searchRow, Connection con) throws Exception {
		int result=0;
		String sql ="select count(num) from notice where "+searchRow.getSearch().getKind()+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		rs.close();
		st.close();
		return result;
	}

	// insert
	public int insert(CommentsDTO commentsDTO, Connection conn) throws Exception {
		int result = 0;

		String sql = "insert into comments values(qna_seq.nextval,?,?,?,sysdate)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, commentsDTO.getNum());
		pst.setString(2, commentsDTO.getWriter());
		pst.setString(3, commentsDTO.getContents());

		result = pst.executeUpdate();

		pst.close();

		return result;
	}

	// delete
	public int delete(int cnum, Connection conn) throws Exception {
		int result = 0;

		String sql = "delete comments where cnum=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, cnum);

		result = pst.executeUpdate();

		pst.close();
		return result;
	}

	// update
	public int update(CommentsDTO commentsDTO, Connection conn) throws Exception {
		int result = 0;
		String sql="update comments set contents=? where cnum=?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, commentsDTO.getContents());
		pst.setInt(2, commentsDTO.getCnum());
		
		result = pst.executeUpdate();
		
		pst.close();

		return result;
	}

	// select
	public List<CommentsDTO> selectList(SearchRow searchRow, Connection con, int num) throws Exception {
		ArrayList<CommentsDTO> ar = new ArrayList<CommentsDTO>();

		String sql = "select * from "
				+ "(select rownum R, C.* from "
				+ "(select * from comments where num=? order by cnum desc) C) "
				+ "where R between ? and ?" ;
			
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, num);
		pst.setInt(2, searchRow.getStartRow());
		pst.setInt(3, searchRow.getLastRow());
		
		ResultSet rs = pst.executeQuery();
		
		while (rs.next()) {
			CommentsDTO commentsDTO = new CommentsDTO();
			commentsDTO.setCnum(rs.getInt("cnum"));
			commentsDTO.setNum(rs.getInt("num"));
			commentsDTO.setWriter(rs.getString("writer"));			
			commentsDTO.setContents(rs.getString("contents"));
			commentsDTO.setReg_date(rs.getDate("reg_date"));
			
			ar.add(commentsDTO);
		}
		rs.close();
		pst.close();
		return ar;
	}
}
