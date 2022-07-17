package main.java.pist.plugins.customItem.object;

public class LoreEditDTO {
	boolean name = false;
	int line = 0; //줄
	int count = 0; //몇번째
	String result = null;
	CustomItemDTO custom = null;
	int index = 0;
	public boolean isName() {
		return name;
	}
	public void setName(boolean name) {
		this.name = name;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public CustomItemDTO getCustom() {
		return custom;
	}
	public void setCustom(CustomItemDTO custom) {
		this.custom = custom;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public LoreEditDTO(boolean name, int line, int count, CustomItemDTO custom, int index) {
		super();
		this.name = name;
		this.line = line;
		this.count = count;
		this.custom = custom;
		this.index = index;
	}
}
