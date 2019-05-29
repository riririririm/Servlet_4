package com.rim.test.comments;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rim.board.contents.CommentsDAO;
import com.rim.board.contents.CommentsDTO;
import com.rim.util.DBConnector;

public class CommentsDAOTest {
	private CommentsDAO commentsDAO;
	Connection conn;
	
	public CommentsDAOTest() {
		commentsDAO = new CommentsDAO();
		
	}
	@BeforeClass
	public static void start() {}
	
	@Before
	public void s() {
		try {
			conn = DBConnector.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void inserTest() throws Exception {
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setCnum(190);
		commentsDTO.setWriter("rim");
		commentsDTO.setContents("contents");
		
		int result=commentsDAO.insert(commentsDTO, conn);
		assertEquals(1, result);
	}


	
	@After
	public void a() {
		try {
			conn.rollback();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@AfterClass
	public static void after() {}
	
}
