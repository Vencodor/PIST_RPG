package main.java.pist.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.java.pist.vencoder.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class Heads {
	//public ArrayList<String> heads = new ArrayList<String>();

	public ItemStack getRandomHead() {
		int r = 0;
		Random ran = new Random();
		r = ran.nextInt(10);

		return getHead(getHeads().get(r));
	}

	public String getRandomHeadUrl() {
		int r = 0;
		Random ran = new Random();
		r = ran.nextInt(10);

		return getHeads().get(r);
	}

	@SuppressWarnings("deprecation")
	public ItemStack getHead(String value) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		UUID hashAsId = new UUID(value.hashCode(), value.hashCode());
		return Bukkit.getUnsafe().modifyItemStack(skull,
				"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");

	}
	
	public String getHeadValue(String name) {
		if(Main.textureCache.get(name)!=null)
			return Main.textureCache.get(name);
		try {
			String result = getURLContent("https://api.mojang.com/users/profiles/minecraft/" + name);
			Gson g = new Gson();
			JsonObject obj = g.fromJson(result, JsonObject.class);
			String uid = obj.get("id").toString().replace("\"", "");
			
			String signature = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);
			obj = g.fromJson(signature, JsonObject.class);
			String value = obj.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
			String decoded = new String(Base64.getDecoder().decode(value));
			obj = g.fromJson(decoded, JsonObject.class);
			String skinURL = obj.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
			byte[] skinByte = ("{\"textures\":{\"SKIN\":{\"url\":\"" + skinURL + "\"}}}").getBytes();
		
			String v = new String(Base64.getEncoder().encode(skinByte));
			Main.textureCache.put(name, v);
			return v;
		} catch (Exception ignored) {
		}
		return null;
	}

	private static String getURLContent(String urlStr) {
		URL url;
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(urlStr);
			in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception ignored) {
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ignored) {
			}
		}
		return sb.toString();
	}

	public ArrayList<String> getHeads() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGE3ZTBkNTVlYTgwNTE1Njk5NTVkYTEzODY5NzE3NGEwYmQ3NjE5MGIyZDU4Yjk5NzUwOTczN2RjZTVmYjYxZiJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY0OTc3ODZkN2JhMTkyMzY5OTMyOTY2Njg2YWE5ZmI3MTQ3ZmE1ODg0ZjlhNjIwOWM0YTczZTJkNjBmMzlmNSJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY4ZWEzODdiOTYwZTlmMmE0MWRiMzIzZjZkZThlYzgxYzY1MmVhZmE5MzNhNDEwMmI5NmI5ZWY0MGM4MzZlNSJ9fX0=");
		//list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2I2ZjRjMDEzMDQ2NDdjODUwZjhjZjQ4MjU5YjlhZDkyZjRjOWJhM2Y4ODJlZTFiOGYyZDgyNzc2ZGVjMmQ0YyJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2ExN2Y5ZGJhYzEwNWFjMjZiNDcwZjk3MTU1YjM5OGMwOWNlN2MzYjBkNTQwOWRiNTVlMWJjMjU0ODY0Y2E2MyJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDllZjdhNjQ1NDE0ZTkzMzkxYjcxMzNlM2U4MDJiZmZiYjUwNThlMDQ2M2ViMDQ4MTg3Zjc3ZmM2NWRiMzc2ZiJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjljMDk0NGRjOGUwMmRmODI1MzExY2M2ZDYxZjIzZDZmODQwZjdkMzA4MTFmMjgxNWFjMTJlYTcwYmQ4NzBlNSJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIyMWZhOWIwZjQ3ZTdhMDEwM2JhOGI1YjdjODc4MjZmOGM1M2VjMDkzOGVlNmMwNDc1ZTA3Y2ExYTA2ZGEzYiJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiMTA3YjZlOGUyMTMxNTk1N2VhMzVhN2ViZTAwM2VhNDRkOTViMTdmYTliZjViNTBhYmRjZDkxM2MzZDFmOSJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjU5MjhlMmM5YjZlMTJjYzhhM2FmMjI1Y2YyODQwNzQ4MGQzN2NkOTQzZDZlZTRmZTJlOTg2NjIxYzNmMTNhMSJ9fX0=");
		
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDkyM2RmODRhMjRiNjU0NTQzMDJkYWExNmZiYmUwZDQ5OTM0NzFmNTQxYjhlMjY3ZGYwMDFjYWJhNThlNDhhYSJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q2ZTNiZjE3OTUxOGM0NDk0MDNmNWEyYWM0MTlmODBiYmMyZGY4ZGZhZTg3MTU2ZDM3NTQ4MjY1YWYxMGNjMyJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGYyYzkyZWZlOGY0Yjg5NjUwMWE0ZWIwZDhhMDZiZDJkZGY2MTBjMzI1ZDZjNWQ4ZTRmNzhkN2Q5MWYyMWQ2ZiJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGIzODhjMGM1MDcxMGJkMmFkOTY2ODZlYjEzMGJkMmYzZDg3NjYwYjgzODMyYmI0MzA0MmU5YWJhYjhlYjcwZiJ9fX0=");
		list.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTEwNzVlNDBlZTA3MjFhMmEyNjc4ODY1NzZmOWU5NTlkODA0MjkyMzA5MGJlZjBhOGE2OGRkOWRkMTY4ZDAzNSJ9fX0=");
		
		return list;
	}
}
