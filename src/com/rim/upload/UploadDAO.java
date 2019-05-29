package com.rim.upload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rim.util.DBConnector;

public class UploadDAO {
	
	public int delete(int pnum, Connection conn) throws Exception {
		int result=0;
		
		String sql="delete upload where pnum=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, pnum);
		result = pst.executeUpdate();
		
		pst.close();
		
		return result;
	}

	public int insert(UploadDTO uploadDTO, Connection conn) throws Exception {
		int result =0;
		
		String sql = "insert into upload values(notice_seq.nextval, ?,?,?)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, uploadDTO.getNum());
		pst.setString(2, uploadDTO.getOname());
		pst.setString(3, uploadDTO.getFname());
		
		result = pst.executeUpdate();
		
		pst.close();
		
		return result;
	}
	
	public UploadDTO selectOne(int num) throws Exception {
		UploadDTO uploadDTO = null;
		Connection conn = DBConnector.getConnection();
		String sql = "select * from upload where num=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, num);
		
		ResultSet rs = pst.executeQuery();
		
		if(rs.next()) {
			uploadDTO = new UploadDTO();
			uploadDTO.setPnum(rs.getInt("pnum"));
			uploadDTO.setNum(rs.getInt("num"));
			uploadDTO.setOname(rs.getString("oname"));
			uploadDTO.setFname(rs.getString("fname"));
		}
		DBConnector.disConnect(rs, pst, conn);
		return uploadDTO;
	}
	
	//selectList
		public List<UploadDTO> selectList(int num, Connection con) throws Exception{
			ArrayList<UploadDTO> ar = new ArrayList<UploadDTO>();
			String sql = "select * from upload where num=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, num);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				UploadDTO uploadDTO = new UploadDTO();
				uploadDTO.setPnum(rs.getInt("pnum"));
				uploadDTO.setNum(rs.getInt("num"));
				uploadDTO.setOname(rs.getString("oname"));
				uploadDTO.setFname(rs.getString("fname"));
				ar.add(uploadDTO);
			}
			rs.close();
			st.close();
			return ar;
		}
}
