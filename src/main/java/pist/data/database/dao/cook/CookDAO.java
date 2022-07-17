package main.java.pist.data.database.dao.cook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.pist.api.object.ItemDTO;
import main.java.pist.api.object.ItemDeserializer;
import main.java.pist.api.object.ItemSerializer;
import main.java.pist.data.database.DBConnection;
import main.java.pist.plugins.cook.object.CookDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class CookDAO {
	public static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(ItemDTO.class, new ItemSerializer())
			.registerTypeAdapter(ItemDTO.class, new ItemDeserializer()).create();
	
	public static int write(CookDTO cook) {
		String SQL = "REPLACE INTO COOK VALUES (?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, cook.getKey());
			pstmt.setString(2, gson.toJson(cook));
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		return -1;
	}
	
	public static HashMap<String,CookDTO> getList() {
		HashMap<String,CookDTO> cookList = new HashMap<String,CookDTO>();
		String SQL = "SELECT * FROM COOK";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CookDTO cook = gson.fromJson(rs.getString(2), CookDTO.class);
				cookList.put(rs.getString(1),cook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		return cookList;
	}
	
	public static int reset() {
		String SQL = "TRUNCATE COOK";
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
