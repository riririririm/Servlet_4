package com.rim.upload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rim.util.DBConnector;

public class MUploadDAO {

	public int insert(MUploadDTO mUploadDTO, Connection conn) throws Exception {
		int result =0;
		
		String sql = "insert into mupload values(notice_seq.nextval, ?,?,?)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, mUploadDTO.getId());
		pst.setString(2, mUploadDTO.getOname());
		pst.setString(3, mUploadDTO.getFname());
		
		result = pst.executeUpdate();
		
		pst.close();
		
		return result;
	}
	
	public MUploadDTO selectOne(String id) throws Exception {
		MUploadDTO mUploadDTO = null;
		Connection conn = DBConnector.getConnection();
		String sql = "select * from upload where id=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, id);
		
		ResultSet rs = pst.executeQuery();
		
		if(rs.next()) {
			mUploadDTO = new MUploadDTO();
			mUploadDTO.setPnum(rs.getInt("pnum"));
			mUploadDTO.setId(rs.getString("id"));
			mUploadDTO.setOname(rs.getString("oname"));
			mUploadDTO.setFname(rs.getString("fname"));
		}
		DBConnector.disConnect(rs, pst, conn);
		return mUploadDTO;
	}
}
