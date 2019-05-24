package com.rim.upload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rim.util.DBConnector;

public class UploadDAO {

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
}
