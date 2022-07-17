package main.java.pist.data.player.object;

import main.java.pist.api.function.Key;
import main.java.pist.plugins.cook.object.PlayerCookDTO;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.stat.object.PlayerStatDTO;

import java.util.UUID;

public class PlayerDTO {
	String id = Key.getRandomKey(12);
	UUID uuid = null;
	String name = null;
	
	PlayerStatDTO stat = new PlayerStatDTO();
	PlayerQuestDTO quest = new PlayerQuestDTO();
	PlayerCookDTO cook = new PlayerCookDTO();
	
	PlayerInfoDTO info = new PlayerInfoDTO();
	PlayerDynamicDTO dynamic = new PlayerDynamicDTO();

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setPlayer(UUID player) {
		this.uuid = player;
	}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public PlayerStatDTO getStat() {
		return stat;
	}
	public void setStat(PlayerStatDTO stat) {
		this.stat = stat;
	}
	public PlayerQuestDTO getQuest() {
		return quest;
	}
	public void setQuest(PlayerQuestDTO quest) {
		this.quest = quest;
	}
	public PlayerCookDTO getCook() {
		return cook;
	}
	public void setCook(PlayerCookDTO cook) {
		this.cook = cook;
	}
	public PlayerInfoDTO getInfo() {
		return info;
	}
	public void setInfo(PlayerInfoDTO info) {
		this.info = info;
	}
	public PlayerDynamicDTO getDynamic() {
		return dynamic;
	}
	
	public void setDynamic(PlayerDynamicDTO dynamic) {
		this.dynamic = dynamic;
	}

	public PlayerDTO(UUID uuid) {
		this.uuid = uuid;
	}

	public String toString() {
		return "name:"+name
				+",uuid:"+uuid
				+",level:"+info.getLevel()
				+",money:"+info.getMoney()
				+",cash:"+info.getCash();
	}
}
