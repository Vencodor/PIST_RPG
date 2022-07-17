package main.java.pist.plugins.cook.inventory;

import main.java.pist.api.function.util.DateUtil;
import main.java.pist.api.function.util.InventoryUtil;
import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.cook.CookPlugin;
import main.java.pist.plugins.cook.object.CookDTO;
import main.java.pist.plugins.cook.object.CookDataDTO;
import main.java.pist.plugins.cook.object.CookingDTO;
import main.java.pist.plugins.cook.object.PlayerCookDTO;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CookGUI extends GuiManager{
	
	public static HashMap<String,CookDTO> selectCook = new HashMap<String,CookDTO>();
	
	public Inventory getCookInv(Player p, PlayerCookDTO playerCook, boolean swapPage) {
		Inventory inv = Bukkit.createInventory(null, 9*6, orange+"요리");
		if(playerCook.getUnlock().size()>0) {
			CookDTO select = null;
			if(getSelected(p)!=null) {
				select = selectCook.get(p.getName());
			} else {
				select = CookPlugin.getCook(playerCook.getUnlock().get(0).getKey());
				selectCook.put(p.getName(), select);
			}
			
			
			ItemStack toolSlot = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)0),red+"어떠한 도구도 필요하지 않습니다");
			if(select.getTool()!=null) {
				toolSlot = select.getTool().getItem();
			}
			inv.setItem(23, toolSlot);
			
			if(!InventoryUtil.haveItems(p.getInventory(),select.getMaterial())) {
				ItemStack start = createItem(new ItemStack(Material.BARRIER),red+"재료가 부족합니다");
				inv.setItem(32, start);
			} else if(select.getTool()!=null&&!InventoryUtil.haveItems(p.getInventory(),Arrays.asList(select.getTool()))) {
				ItemStack start = createItem(new ItemStack(Material.BARRIER),red+"도구가 없습니다");
				inv.setItem(32, start);
			} else {
				ItemStack start = NbtUtil.setNBT(
						createItem(new ItemStack(Material.CAULDRON_ITEM),green+"조리 시작", 
								Arrays.asList(" "
										,gray+"소요 시간 : "+orange+DateUtil.toStrTime(select.getTime())
										," ")
								),"start","true");
				inv.setItem(32, start);
			}
			
			inv.setItem(25, select.getResult());
			playerCook.update();
			
			setInputField(inv, select);
			if(swapPage) {
				int index = 0;
				for(CookDataDTO a : playerCook.getUnlock()) {
					if(a.getKey().equals(select.getKey())) {
						index = playerCook.getUnlock().indexOf(a);
					}
				}
				setRecipePage(inv, playerCook, index/7, select);
			} else {
				setRecipePage(inv, playerCook, 0, select);
			}
		}
		
		inv.setItem(36, getUI(7));
		return inv;
	}
	
	public Inventory getProgressCookInv(CookingDTO cook) {
		Inventory inv = Bukkit.createInventory(null, 9*5, orange+cook.getCook().getDisplay()+"을(를) 조리중입니다.. ");
		
		int leftTime = (int) (cook.getCook().getTime()-(System.currentTimeMillis()-cook.getStartTime())/1000);
		inv.setItem(22, createItem(new ItemStack(Material.CAULDRON_ITEM), orange+cook.getCook().getDisplay(),
				Arrays.asList(" "
						,gray+"소요 시간 : "+orange+DateUtil.toStrTime(cook.getCook().getTime())
						,gray+"남은 시간 : "+orange+DateUtil.toStrTime(leftTime)
						," ")));
		
		inv.setItem(40, NbtUtil.setNBT(createItem(new ItemStack(Material.BARRIER),red+"중단하기"
				,Arrays.asList(" ",red+bold+"  주의! "+gray+"조리를 중단하면 재료들이 소멸됩니다.  "," ")), "cancel", "true"));
		
		return inv;
	}
	
	public static CookDTO getSelected(Player p) {
		return selectCook.get(p.getName());
	}
	
	public static void setSelected(Player p, CookDTO cook) {
		selectCook.put(p.getName(), cook);
	}
	
	public void setInputField(Inventory inv, CookDTO cook) {
		ItemStack empty = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));
		
		int count = 0;
		for(int i=18; i<36; i++) {
			if(0<i%9&&i%9<4) {
				if(cook.getMaterial().size()>count) {
					inv.setItem(i, cook.getMaterial().get(count).getItem());
					count++;
				} else {
					inv.setItem(i, empty);
				}
			}
		}
		
	}
	
	public void setRecipePage(Inventory inv, PlayerCookDTO playerCook, int page, CookDTO select) {
		int count = 0;
		int emptyCount = 0;
		for(int i=46; i<=52; i++) {
			if(playerCook.getUnlock().size()>count+page) {
				CookDTO cook = CookPlugin.getCook(playerCook.getUnlock().get(count+page).getKey());
				count++;
				
				String name = orange+cook.getDisplay();
				List<String> lore = new ArrayList<String>();
				if(cook.getDescription()!=null)
					lore = Arrays.asList(" ",gray+" "+cook.getDescription()," ");
				
				ItemStack icon = null;
				if(cook.getIcon()==null) {
					icon = new ItemStack(Material.BOOK);
				} else {
					icon = cook.getIcon().getItem();
				}
				
				if(cook.getKey().equals(select.getKey())) {
					icon.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
					inv.setItem(i, NbtUtil.setNBT(createItem(icon,name,lore),"key",cook.getKey()));
				} else {
					inv.setItem(i, NbtUtil.setNBT(createItem(icon,name,lore),"key",cook.getKey()));
				}
			} else {
				inv.setItem(i, null);
				emptyCount++;
			}
		}
		
		//if(page-1>=0)
		inv.setItem(45, NbtUtil.setNBT(createItem(getUI(13),red+"이전"), "page", page-1+"")); //왼쪽 화살표
		//if(playerCook.getUnlock().size()-page*7>=7)
		if(emptyCount<3)
			inv.setItem(53, NbtUtil.setNBT(createItem(getUI(14),green+"다음"), "page", page+1+"")); //오른쪽 화살표
	}
	
}
