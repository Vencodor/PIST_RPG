package main.java.pist.plugins.stat.inventory;

import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.stat.StatPlugin;
import main.java.pist.plugins.stat.object.EditDTO;
import main.java.pist.plugins.stat.object.PlayerStatDTO;
import main.java.pist.plugins.stat.object.enums.Stat;
import main.java.pist.plugins.stat.object.enums.StatEffect;
import main.java.pist.util.HeadEnum;
import main.java.pist.util.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class StatGUI extends GuiManager{
	
	
	public int setNextClickIncrease(Player p) {
		EditDTO editDTO = StatPlugin.editInfo.get(p.getName());
		if(editDTO.getIncreaseCount()==1) {
			editDTO.setIncreaseCount(3);
			return 3;
		} else if(editDTO.getIncreaseCount()==3) {
			editDTO.setIncreaseCount(5);
			return 5;
		} else if(editDTO.getIncreaseCount()==5) {
			editDTO.setIncreaseCount(10);
			return 10;
		} else if(editDTO.getIncreaseCount()==10) {
			editDTO.setIncreaseCount(1);
			return 1;
		} else {
			return 1;
		}
	}
	
	public Inventory getStatInv(Player p,PlayerStatDTO stat,boolean editor) {
		Inventory i = Bukkit.createInventory(null, 9*6, orange+bold+"Status");
		Heads icon = new Heads();
		EditDTO edit = null;
		
		StatPlugin.editInfo.put(p.getName(), new EditDTO(stat.getStatPoint(),Stat.STR));
		edit = StatPlugin.editInfo.get(p.getName());
		
		updateStatGui(i, edit, stat);
		
		if(editor) {
			
			List<String> increaseLore = Arrays.asList(" ",white+"클릭하여 스텟 증가폭을 "+green+"설정"+white+"합니다", " ");
			ItemStack increase = createItem(icon.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0=")
					, green+"클릭당 증가폭",increaseLore);
			i.setItem(35, increase);
			
			List<String> backLore = Arrays.asList(" ",white+"클릭하여 변경된 스탯을 "+yellow+"초기화"+white+"합니다", " ");
			ItemStack back = createItem(icon.getHead(HeadEnum.RESET.getValue())
					, yellow+"초기화",backLore);
			i.setItem(44, back);
			
			List<String> saveLore = Arrays.asList(" ",white+"클릭하여 변경된 스탯을 "+orange+"저장합니다", " ");
			ItemStack save = createItem(icon.getHead(HeadEnum.SAVE.getValue())
					, orange+"저장",saveLore);
			i.setItem(53, save);
		}
		
//		ItemStack emptyYellow = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)4)," ");
//		ItemStack empty = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7)," ");
//		for(int k=0; k<=44; k++) {
//			if(k%9>0&&k%9<7) {
//				if(i.getItem(k)==null) {
//					i.setItem(k, empty);
//				}
//			}
//		}
		
		setUI(i,3);
		
		return i;
	}
	
	public Inventory getStatInfoInv(Stat stat) {
		Inventory i = Bukkit.createInventory(null, 9*6, orange+bold+"Status Info - "+white+stat.toString());
		Heads icon = new Heads();
		
		i.setItem(22, createItem(getUI(stat.getIcon().getIcon()), stat.getColor()+stat.toString()+" "+gray+stat.getName(), Arrays.asList(" ",gray+""+stat.getDescription()," ",dgray+stat.toString()+"의 정보를 열람중입니다")));
		
		int effectSlot = 38;
		Iterator<String> numbers = Arrays.asList("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0="
				,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNkOWVlZWU4ODM0Njg4ODFkODM4NDhhNDZiZjMwMTI0ODVjMjNmNzU3NTNiOGZiZTg0ODczNDE0MTk4NDcifX19"
				,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ0ZWFlMTM5MzM4NjBhNmRmNWU4ZTk1NTY5M2I5NWE4YzNiMTVjMzZiOGI1ODc1MzJhYzA5OTZiYzM3ZTUifX19").iterator();
		for(StatEffect a : stat.getEffect()) {
			if(numbers.hasNext()) {
				List<String> effectLore = Arrays.asList(" ",white+"레벨당 증가량 : "+orange+bold+a.getIncrease()," ",gray+a.getDescription());
				i.setItem(effectSlot, createItem(icon.getHead(numbers.next()), stat.getColor()+a.getName(),effectLore));
				effectSlot = effectSlot + 2;
			}
		}
		
		if(effectSlot == 31) {
			i.setItem(33, createItem(new ItemStack(Material.BARRIER)," "));
		}
		
//		ItemStack empty = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7)," ");
//		int emptySlot = 0;
//		for(int k=0; k<=5; k++) {
//			i.setItem(emptySlot, empty);
//			i.setItem(emptySlot+8, empty);
//			emptySlot = emptySlot + 9;
//		}
		
		List<String> cancelLore = Arrays.asList(" ",white+"클릭하여 "+red+"이전으로"+white+" 돌아갑니다"," ");
		ItemStack cancel = createItem(icon.getHead(HeadEnum.BACK.getValue())
				, red+"뒤로가기", cancelLore);
		i.setItem(53, cancel);
		
		setUI(i, 3);
		
		return i;
	}
	
	public void updateStatGui(Inventory i,EditDTO edit,PlayerStatDTO stat) { //조금 이상함. 나중에 통계표 수정하기
		//Heads icon = new Heads();
		
		i.setItem(12, getStatItem(Stat.STR,edit,stat));
		i.setItem(23, getStatItem(Stat.DEX,edit,stat));
		i.setItem(40, getStatItem(Stat.CON,edit,stat));
		i.setItem(38, getStatItem(Stat.INT,edit,stat));
		i.setItem(19, getStatItem(Stat.WIS,edit,stat));
	}
	
	private ItemStack getStatItem(Stat a,EditDTO edit,PlayerStatDTO stat) {
		int point = edit.getIncreaseStat().getStat(a.toString()) + stat.getStat().getStat(a.toString());
		//투자한 포인트
		ItemStack item = createItem(getUI(a.getIcon().getIcon()), a.getColor()+bold+a.toString()+" "+gray+a.getName()+" "+dgray+bold
				+"("+yellow+bold+(point)+dgray+bold+")"
				, Arrays.asList(" ",gray+""+a.getDescription()," ",dgray+"좌클릭: 스텟 투자 ( "+(stat.getStatPoint()-edit.getIncreaseStat().getAllPoint())+"남음 )",dgray+"우클릭: 정보 보기"));
		return item;
	}
	
	/*
	public void setStatPlayerInv(Player p) {
		Heads icon = new Heads();
		Inventory i = p.getInventory();
		i.clear();
		List<String> cancelLore = Arrays.asList(" ",white+"클릭하여 창을 "+red+"닫습니다"," ");
		ItemStack cancel = createItem(icon.getHead(HeadEnum.CANCEL.getValue())
				, red+"창 닫기", cancelLore);
		i.setItem(19, cancel);
		
		ItemStack info = getPlayerInfoItem(p);
		i.setItem(21, info);
		
		Stat recommandStat = Stat.STR;
		
		ItemStack recommand = createItem(recommandStat.getIcon().getIcon(), red+"추천스텟");
		i.setItem(24, recommand);
		
		ItemStack empty = createItem(recommandStat.getIcon().getStatGuiBar());
		ItemStack emptyGray = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7)," ");
		
		List<Integer> emptySlots = Arrays.asList(14,15,16,23,25,32,33,34);
		for(int a : emptySlots)
			i.setItem(a, empty);
		
		i.setItem(17, emptyGray);
		i.setItem(26, emptyGray);
		i.setItem(35, emptyGray);
	}
	*/
	
}
