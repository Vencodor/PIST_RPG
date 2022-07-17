package main.java.pist.plugins.quest.integrated.object;

import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;

import java.util.ArrayList;
import java.util.List;

public class PlayerQuestDTO {
	MainQuestDTO progressQuest = null;
	SubQuestDTO progressJobQuest = null;
	List<SubQuestDTO> progressSubQuest = new ArrayList<SubQuestDTO>();
	
	List<MainQuestDTO> clearQuest = new ArrayList<MainQuestDTO>();
	List<SubQuestDTO> clearSubQuest = new ArrayList<SubQuestDTO>();
	List<SubQuestDTO> clearJobQuest = new ArrayList<SubQuestDTO>();

	public List<MainQuestDTO> getClearQuest() {
		return clearQuest;
	}

	public void setClearQuest(List<MainQuestDTO> clearQuest) {
		this.clearQuest = clearQuest;
	}
	public MainQuestDTO getProgressQuest() {
		return progressQuest;
	}

	public void setProgressQuest(MainQuestDTO progressQuest) {
		this.progressQuest = progressQuest;
	}

	public SubQuestDTO getProgressJobQuest() {
		return progressJobQuest;
	}

	public void setProgressJobQuest(SubQuestDTO progressJobQuest) {
		this.progressJobQuest = progressJobQuest;
	}

	public List<SubQuestDTO> getProgressSubQuest() {
		return progressSubQuest;
	}

	public void setProgressSubQuest(List<SubQuestDTO> progressSubQuest) {
		this.progressSubQuest = progressSubQuest;
	}

	public List<SubQuestDTO> getClearSubQuest() {
		return clearSubQuest;
	}

	public void setClearSubQuest(List<SubQuestDTO> clearSubQuest) {
		this.clearSubQuest = clearSubQuest;
	}

	public List<SubQuestDTO> getClearJobQuest() {
		return clearJobQuest;
	}

	public void setClearJobQuest(List<SubQuestDTO> clearJobQuest) {
		this.clearJobQuest = clearJobQuest;
	}

	public PlayerQuestDTO(MainQuestDTO progressQuest, SubQuestDTO progressJobQuest, List<SubQuestDTO> progressSubQuest,
			List<MainQuestDTO> clearQuest, List<SubQuestDTO> clearSubQuest, List<SubQuestDTO> clearJobQuest) {
		super();
		this.progressQuest = progressQuest;
		this.progressJobQuest = progressJobQuest;
		this.progressSubQuest = progressSubQuest;
		this.clearQuest = clearQuest;
		this.clearSubQuest = clearSubQuest;
		this.clearJobQuest = clearJobQuest;
	}

	public PlayerQuestDTO() {
		
	}
}
