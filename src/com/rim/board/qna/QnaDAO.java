package com.rim.board.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rim.board.BoardDAO;
import com.rim.board.BoardDTO;
import com.rim.page.SearchRow;
import com.rim.util.DBConnector;

public class QnaDAO implements BoardDAO {

	@Override
	public int getNum() throws Exception {
		// TODO Auto-generated method stub
		return 0;
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
		
		String sql="select * from "
				+ "(select rownum R, Q.* from "
				+ "(select * from qna where "+searchRow.getSearch().getKind()+" like ? order by ref desc, step asc) Q) "
				+ "where R between ? and ?";
		
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		pst.setInt(2, searchRow.getStartRow());
		pst.setInt(3, searchRow.getLastRow());
		
		ResultSet rs = pst.executeQuery();
		
		QnaDTO qnaDTO = null;
		while(rs.next()) {
			qnaDTO = new QnaDTO();
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setReg_date(rs.getDate("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
			arr.add(qnaDTO);
		}
		
		DBConnector.disConnect(pst,conn);
		
		return arr;
	}

	@Override
	public int insert(BoardDTO boardDTO, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return 0;
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
	
	public int reply(QnaDTO qnaDTO) throws Exception {
		
		 return 0;
	}

}
