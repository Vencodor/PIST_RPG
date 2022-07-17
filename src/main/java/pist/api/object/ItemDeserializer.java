package main.java.pist.api.object;

import com.google.gson.*;
import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ItemDeserializer implements JsonDeserializer<ItemDTO>{

	@SuppressWarnings("deprecation")
	@Override
	public ItemDTO deserialize(JsonElement json, Type type, JsonDeserializationContext arg2) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		
		NBTTagCompound nbt = new NBTTagCompound();
		try {
			nbt = MojangsonParser.parse(obj.get("nbt").getAsString());
		} catch (MojangsonParseException e) {
			e.printStackTrace();
		}
		
		List<String> lore = new ArrayList<String>();
		if(obj.get("lore")!=null) {
			JsonArray array = obj.get("lore").getAsJsonArray();
			for(int i=0; i<array.size(); i++)
				lore.add(array.get(i).getAsString());
		}
		String display = null;
		if(obj.get("display")!=null) {
			display = obj.get("display").getAsString();
		}
		return new ItemDTO(Material.getMaterial(obj.get("material").getAsInt())
				, display
				, lore, obj.get("amount").getAsInt(), nbt);
	}

	
	
}
