package com.rim.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rim.util.DBConnector;

public class MemberDAO {
	public int idCheck(String id) throws Exception{
		int result=0;
		Connection conn = DBConnector.getConnection();
		String sql = "select id from member where id=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, id);
		
		ResultSet rs=pst.executeQuery();
		
		int check=1;
		if(rs.next()) {
			check=0;
		}
		
		rs.close();
		pst.close();
		
		return check;
	}

	public MemberDTO memberLogin(MemberDTO dto, Connection conn) throws Exception{
		MemberDTO m = null;
		
		String sql="select * from member where id=? and pw=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, dto.getId());
		pst.setString(2, dto.getPw());
		ResultSet rs = pst.executeQuery();
		
		if(rs.next()) {
			m = new MemberDTO();
			m.setId(rs.getString("id"));
			m.setPw(rs.getString("pw"));
			m.setName(rs.getString("name"));
			m.setPhone(rs.getString("phone"));
			m.setEmail(rs.getString("email"));
			m.setAge(rs.getInt("age"));
		}
		
		return m;
	}
	
	public int memberJoin(MemberDTO dto, Connection conn) throws Exception{
		int result=0;

		String sql="insert into member values(?,?,?,?,?,?)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, dto.getId());
		pst.setString(2, dto.getPw());
		pst.setString(3, dto.getName());
		pst.setString(4, dto.getPhone());
		pst.setString(5, dto.getEmail());
		pst.setInt(6, dto.getAge());
		
		result = pst.executeUpdate();
		
		return result;
	}
	
	public int memberUpdate(MemberDTO dto, Connection conn) throws Exception{
		int result=0;
		String sql="update member set name=?, pw=?, phone=?, email=?, age=? where id=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(6, dto.getId());
		pst.setString(2, dto.getPw());
		pst.setString(1, dto.getName());
		pst.setString(3, dto.getPhone());
		pst.setString(4, dto.getEmail());
		pst.setInt(5, dto.getAge());
		
		result = pst.executeUpdate();
		
		return result;
	}
	
	public int memberDelete(String id, Connection conn) throws Exception{
		int result=0;
		
		String sql="delete member where id=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, id);
	
		result = pst.executeUpdate();
		
		
		
		return result;
	}
}
