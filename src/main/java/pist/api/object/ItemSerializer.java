package main.java.pist.api.object;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ItemSerializer implements JsonSerializer<ItemDTO>{

	@SuppressWarnings("deprecation")
	@Override
	public JsonElement serialize(ItemDTO item, Type type, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		Gson gson = new Gson();
		
		obj.addProperty("material", item.getMeterial().getId());
		obj.addProperty("display", item.getDisplay());
		obj.add("lore", gson.toJsonTree(item.getLore()));
		obj.addProperty("amount", item.getAmount());
		obj.addProperty("nbt", item.getNbt().toString());
		
		return obj;
	}
	
}
