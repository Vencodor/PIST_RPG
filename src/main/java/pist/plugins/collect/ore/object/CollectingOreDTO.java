package main.java.pist.plugins.collect.ore.object;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.api.object.ItemDTO;
import org.bukkit.block.Block;

public class CollectingOreDTO {
	Block block = null;
	ItemDTO item = null;
	double time = 1;
	long start = 0;
	public Block getBlock() {
		return block;
	}
	public void setBlock(Block block) {
		this.block = block;
	}
	public ItemDTO getItem() {
		return item;
	}
	public void setItem(ItemDTO item) {
		this.item = item;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public CollectingOreDTO(Block block, ItemDTO item) {
		super();
		this.block = block;
		this.item = item;
		
		double time = 1;
		try {
			time = Integer.parseInt(NbtUtil.getNBT(item.getItem(), "time"));
		} catch(NumberFormatException e1) {
			e1.printStackTrace();
		}
		this.time = time;
		this.start = System.currentTimeMillis();
	}
}
