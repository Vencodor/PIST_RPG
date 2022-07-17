package main.java.pist.plugins.customItem.inventory;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.customItem.object.CustomItemDTO;
import main.java.pist.plugins.customItem.object.enums.CustomType;
import main.java.pist.util.HeadEnum;
import main.java.pist.util.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GUI_CustomItem extends GuiManager {
	
	public Inventory getCustomGUI(CustomItemDTO custom,int page) {
		Inventory i = Bukkit.createInventory(null, 9*6,prefix+"Custom#"+custom.getKey()+"#Page"+page);
		
		int count = 45*(page-1);
		for(ItemStack a : custom.getContents()) {
			count++;
			if(count>45*page)
				break;
			i.addItem(a);
		}
		Heads icon = new Heads();
		
		i.setItem(50, createItem(icon.getHead(HeadEnum.NEXT.getValue()),yellow+"다음"));
		i.setItem(48, createItem(icon.getHead(HeadEnum.PREVIOIS.getValue()),yellow+"이전"));
		
		i.setItem(45, createItem(icon.getHead(HeadEnum.PLUS.getValue()),orange+"아이템 추가"
				,Arrays.asList(" ",gray+"아이템을 들고 클릭하세요"," ")));
		i.setItem(53, createItem(icon.getHead(HeadEnum.MORE.getValue()),orange+"설정"));
		
		if(custom.getType().isEdit()) {
			if(custom.getBaseName()!=null&&custom.getBaseLore().size()>0) {
				i.setItem(49, createItem(new ItemStack(Material.PAPER),custom.getBaseName()+gray+" (base lore)",custom.getBaseLore()));
			} else {
				i.setItem(49, createItem(new ItemStack(Material.BARRIER),red+"설정된 기본 로어가 없습니다."));
			}
		} else {
			i.setItem(49, createItem(new ItemStack(Material.BARRIER),red+"해당 설정에서는 로어수정이 불가능합니다."));
		}
		
		if(custom.getType() == CustomType.RANDOM) {
			ItemStack low = NbtUtil.setNBT(createItem(new ItemStack(Material.STAINED_GLASS_PANE
					,1,(byte)5),gray+" < 확률 "+green+"높음"),"cancel","true");
			ItemStack medium = NbtUtil.setNBT(createItem(new ItemStack(Material.STAINED_GLASS_PANE
					,1,(byte)1),gray+" < 확률 "+orange+"중간"),"cancel","true");
			ItemStack high = NbtUtil.setNBT(createItem(new ItemStack(Material.STAINED_GLASS_PANE
					,1,(byte)14),gray+" < 확률 "+red+"낮음"),"cancel","true");
			
			i.setItem(8, low);
			i.setItem(17, low);
			i.setItem(26, medium);
			i.setItem(35, medium);
			i.setItem(44, high);
		}
		
		return i;
	}
	
	public Inventory getCustomSettingGUI(CustomItemDTO custom) {
		Inventory i = Bukkit.createInventory(null, 9*3,prefix+"C Setting#"+custom.getKey());
		CustomType type = custom.getType();
		i.setItem(13, createItem(new ItemStack(type.getIcon()),orange+type.toString()
		,Arrays.asList(" ",gray+type.getDescription()," ")));
		
		return i;
	}
	
	public Inventory getCustomItemGUI(CustomItemDTO custom, int index) {
		Inventory i = Bukkit.createInventory(null, 9*3,prefix+"C Item#"+custom.getKey()+"#"+index);
		
		ItemStack item = custom.getContents().get(index);
		Heads icon = new Heads();
		CustomType type = custom.getType();
		
		i.setItem(11, item);
		
		if(type.isEdit())
			i.setItem(13, createItem(new ItemStack(icon.getHead(HeadEnum.RESET.getValue())),yellow+"로어 리셋 & 설정"));
		i.setItem(14, createItem(new ItemStack(icon.getHead(HeadEnum.SAVE.getValue())),orange+"가져오기"));
		i.setItem(15, createItem(new ItemStack(icon.getHead(HeadEnum.REMOVE.getValue())),red+"삭제"));
		
		i.setItem(26, createItem(new ItemStack(icon.getHead(HeadEnum.BACK.getValue())),green+"뒤로가기"));
		
		return i;
	}
	
	public Inventory getCustomItemLoreGUI(CustomItemDTO custom, int index) {
		Inventory i = Bukkit.createInventory(null, 9*5,prefix+"C Lore#"+custom.getKey()+"#"+index);
		ItemStack item = custom.getContents().get(index);
		Heads icon = new Heads();
		
		i.setItem(39, createItem(new ItemStack(Material.PAPER),custom.getBaseName()+gray+" (base lore)",custom.getBaseLore()));
		i.setItem(40, item);
		i.setItem(44, createItem(icon.getHead(HeadEnum.BACK.getValue()),orange+"뒤로가기"));
		
		int count = 0;
		for(char a : item.getItemMeta().getDisplayName().toCharArray()) {
			if(a == '$') {
				i.addItem(createItem(new ItemStack(Material.NAME_TAG),yellow+"이름"+black+"#"+count
						,Arrays.asList(white+(count+1)+"번째 변수 수정")));
				count++;
			}
		}
		ItemStack empty = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)15));
		for(int k=0; k<9; k++)
			if(i.getItem(k)==null)
				i.setItem(k, empty);

		List<String> lore = item.getItemMeta().getLore();
		
		int line = 0;
		for(String a : lore) {
			int loreCount = 0;
			for(char b : a.toCharArray()) {
				if(b == '$') {
					i.addItem(createItem(new ItemStack(Material.BOOK),yellow+"설명"+black+"#"+line+"-"+loreCount
							,Arrays.asList(white+(line+1)+"번째 줄 "+(loreCount+1)+"번째 변수 수정")));
					loreCount++;
				}
			}
			line++;
		}
		
		
		
		return i;
	}
	
}
