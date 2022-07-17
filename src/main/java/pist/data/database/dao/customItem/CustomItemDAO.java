package main.java.pist.data.database.dao.customItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import main.java.pist.data.database.DBConnection;
import main.java.pist.plugins.customItem.object.CustomItemDTO;
import main.java.pist.plugins.customItem.object.enums.CustomType;
import main.java.pist.util.ConfigurationSerializableAdapter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomItemDAO {
	
	private static final Gson g = new GsonBuilder()
		    .setPrettyPrinting()    // Optional
		    .disableHtmlEscaping()
		    .registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationSerializableAdapter())
		    .create();
	
	public static int write(CustomItemDTO customItem) {
		String SQL = "REPLACE INTO CUSTOMITEM VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		Gson gson = new Gson();
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, customItem.getKey());
			pstmt.setString(2, customItem.getBaseName());
			pstmt.setString(3, gson.toJson(customItem.getBaseLore()));
			
			JsonArray jsArray = new JsonArray();
			
			for(ItemStack a : customItem.getContents())
				jsArray.add(g.toJson(a));
			
			pstmt.setString(4, jsArray.toString());
			pstmt.setString(5, customItem.getType().toString());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		return -1;
	}
	
	public static ArrayList<CustomItemDTO> getList() {
		ArrayList<CustomItemDTO> customItemList = new ArrayList<CustomItemDTO>();
		String SQL = "SELECT * FROM CUSTOMITEM";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				List<String> lore = new ArrayList<String>();
				List<ItemStack> contents = new ArrayList<ItemStack>();
				
				JSONParser jsonParser = new JSONParser();
				JSONArray jsonLore = (JSONArray) jsonParser.parse(rs.getString(3));
				for (int i=0; i<jsonLore.size(); i++)
				    lore.add((String)jsonLore.get(i));
				
				JSONArray jsonContent = (JSONArray) jsonParser.parse(rs.getString(4));
				for (int i=0; i<jsonContent.size(); i++)
				    contents.add(g.fromJson((String)jsonContent.get(i),ItemStack.class));
				
				CustomItemDTO customDTO = new CustomItemDTO(
						rs.getString(1),
						rs.getString(2),
						lore,
						contents,
						CustomType.valueOf(rs.getString(5))
						);
				customItemList.add(customDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if(conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(pstmt != null) pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
			try { if(rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
		}
		return customItemList;
	}
	
	public static int reset() {
		String SQL = "TRUNCATE CUSTOMITEM";
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
