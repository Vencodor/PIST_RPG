package main.java.pist.plugins.system.biome.events;

import main.java.pist.plugins.system.biome.object.BiomeDTO;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class BiomeUpdateEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	
	private BiomeDTO from;
	private BiomeDTO to;

	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public BiomeDTO getFrom() {
		return from;
	}
	public void setFrom(BiomeDTO from) {
		this.from = from;
	}
	public BiomeDTO getTo() {
		return to;
	}
	public void setTo(BiomeDTO to) {
		this.to = to;
	}

	public BiomeUpdateEvent(Player player, BiomeDTO from, BiomeDTO to) {
		super();
		this.player = player;
		this.from = from;
		this.to = to;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
