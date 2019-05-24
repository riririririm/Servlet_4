package com.rim.board.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.rim.board.BoardDAO;
import com.rim.board.BoardDTO;
import com.rim.page.SearchRow;
import com.rim.util.DBConnector;

public class NoticeDAO implements BoardDAO{

	@Override
	public int getNum() throws Exception {
		int result=0;
		Connection con = DBConnector.getConnection();
		String sql ="select notice_seq.nextval from dual";
		PreparedStatement st =con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		rs.next();
		result=rs.getInt(1);
		DBConnector.disConnect(rs, st, con);
		return result;
	}

	@Override
	public int getTotalCount(SearchRow searchRow, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoardDTO select(int num, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BoardDTO> selectList(SearchRow searchRow, Connection conn) throws Exception {
		ArrayList<BoardDTO> arr = new ArrayList<BoardDTO>();
		NoticeDTO dto = null;
		String sql="select * from "
				+ "(select rownum R, n.* from "
				+ "(select * from notice where "+searchRow.getSearch().getKind()+" like ? order by num desc) n) "
				+ "where R between ? and ?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		pst.setInt(2, searchRow.getStartRow());
		pst.setInt(3, searchRow.getLastRow());
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			dto = new NoticeDTO();
			dto.setContents(rs.getString("contents"));
			
			dto.setReg_date(rs.getDate("reg_date"));
			dto.setHit(rs.getInt("hit"));
			dto.setNum(rs.getInt("num"));
			dto.setTitle(rs.getString("title"));
			dto.setWriter(rs.getString("writer"));
			arr.add(dto);
		}
		
		rs.close();
		pst.close();
		return arr;
	}

	@Override
	public int insert(BoardDTO boardDTO, Connection conn) throws Exception {
		int result=0;
		
		String sql="insert into notice values(?,?,?,?,sysdate,0)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, boardDTO.getNum());
		pst.setString(2, boardDTO.getTitle());
		pst.setString(3, boardDTO.getContents());
		pst.setString(4, boardDTO.getWriter());
		result = pst.executeUpdate();
		pst.close();
		return result;
	}

	@Override
	public int update(BoardDTO boardDTO, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int num, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
