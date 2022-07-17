package main.java.pist.data.database.dao.quest;

import com.google.gson.Gson;
import main.java.pist.data.database.DBConnection;
import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class MainQuestDAO {
	public static int write(MainQuestDTO quest) {
		String SQL = "REPLACE INTO MAINQUEST VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		Gson gson = new Gson();
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, quest.getKey());
			pstmt.setString(2, gson.toJson(quest));
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		return -1;
	}
	
	public static HashMap<String,MainQuestDTO> getList() {
		HashMap<String,MainQuestDTO> questList = new HashMap<String,MainQuestDTO>();
		String SQL = "SELECT * FROM MAINQUEST";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Gson gson = new Gson();
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MainQuestDTO mainQuest = gson.fromJson(rs.getString(2), MainQuestDTO.class);
				questList.put(rs.getString(1),mainQuest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		return questList;
	}
	
	public static int reset() {
		String SQL = "TRUNCATE MAINQUEST";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		return -1;
	}
}
