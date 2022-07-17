package main.java.pist.plugins.cook.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerCookDTO {
	List<CookDataDTO> unlock = new ArrayList<CookDataDTO>();

	public List<CookDataDTO> getUnlock() {
		return unlock;
	}

	public void setUnlock(List<CookDataDTO> unlock) {
		this.unlock = unlock;
	}

	public PlayerCookDTO(List<CookDataDTO> unlock) {
		super();
		this.unlock = unlock;
	}
	
	public void update() {
		Collections.sort(unlock,new CookCountComparator());
	}
	
	public PlayerCookDTO() {

	}

}

class CookCountComparator implements Comparator<CookDataDTO> {
	@Override
	public int compare(CookDataDTO f1, CookDataDTO f2) {
		if (f1.cookCount > f2.cookCount) {
			return 1;
		} else if (f1.cookCount < f2.cookCount) {
			return -1;
		}
		return 0;
	}
}
