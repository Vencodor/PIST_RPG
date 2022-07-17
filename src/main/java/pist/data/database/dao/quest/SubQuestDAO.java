package main.java.pist.data.database.dao.quest;

import com.google.gson.Gson;
import main.java.pist.data.database.DBConnection;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class SubQuestDAO {
	public static int write(SubQuestDTO quest) {
		String SQL = "REPLACE INTO SUBQUEST VALUES (?, ?)";
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
	
	public static HashMap<String,SubQuestDTO> getList() {
		HashMap<String,SubQuestDTO> questList = new HashMap<String,SubQuestDTO>();
		String SQL = "SELECT * FROM SUBQUEST";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Gson gson = new Gson();
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				SubQuestDTO mainQuest = gson.fromJson(rs.getString(2), SubQuestDTO.class);
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
		String SQL = "TRUNCATE SUBQUEST";
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
