package main.java.pist.plugins.system.debug.executor;

import main.java.pist.api.animation.text.TitleAnimation;
import main.java.pist.api.animation.text.TitleAnimations;
import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.entity.mob.entities.CustomHuman;
import main.java.pist.plugins.entity.mob.object.MobDTO;
import main.java.pist.plugins.entity.mob.object.enums.MobPersonality;
import main.java.pist.plugins.system.macroTest.MacroTestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

public class DebugCommand extends CommandManager {

	public DebugCommand() {
		super(Arrays.asList("debug","디버그"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(!p.isOp())
			return;
		if(args.length==0) {
			
		} else {
			if(args[0].equalsIgnoreCase("chest")) {
				if(args.length==1) {
					p.sendMessage(cmdWrong+"/debug chest <line>");
				} else {
					int line = 0;
					try {
						line = Integer.parseInt(args[1]);
					} catch(NumberFormatException e1) {
						p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
						return;
					}
					p.openInventory(Bukkit.createInventory(null, 9*line, red+"Debug Chest"));
				}
			} else if(args[0].equalsIgnoreCase("nbt")) {
				if(args.length<=2) {
					p.sendMessage(cmdWrong+"/debug nbt <Name> <String>");
				} else {
					ItemStack item = p.getInventory().getItemInMainHand();
					
//					net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
//					NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
//					itemCompound.set(args[1], new NBTTagString(args[2]));
//					nmsItem.setTag(itemCompound);
					p.getInventory().setItemInMainHand(NbtUtil.setNBT(item, args[1], args[2]));
					
					p.sendMessage(complete+"성공적으로 아이템에 NBT를 부여하였습니다 "+gray+args[1]+" | "+args[2]);
				}
				
			} else if(args[0].equalsIgnoreCase("mob")) {
				MobDTO mob = new MobDTO(null);
				mob.setPersonality(MobPersonality.중립);
				Location loc = p.getLocation();
				((CraftWorld)loc.getWorld()).getHandle().addEntity(new CustomHuman(loc, mob));
				p.sendMessage(complete+"성공적으로 CustomEntity를 소환하였습니다");
			} else if(args[0].equalsIgnoreCase("armorstand")) {
				Location loc = p.getLocation();
				ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
				
				ItemStack item = new ItemStack(Material.WOOD_SPADE);
				item.setDurability((short) 1);
				as.getEquipment().setHelmet(item);
			} else if(args[0].equalsIgnoreCase("head")) {
				PlayerInventory inv = p.getInventory();
				ItemStack item = inv.getItemInMainHand();
				
				inv.setHelmet(item);
			} else if(args[0].equalsIgnoreCase("title")) {
				TitleAnimation.run(p
						,ChatColor.YELLOW+"타이틀 테스트",TitleAnimations.ERROR);
			} else if(args[0].equalsIgnoreCase("macro")) {
				MacroTestPlugin.runMacroTest(p);
			}
		}
		
	}
	
}
