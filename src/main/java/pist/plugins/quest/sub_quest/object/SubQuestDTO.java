package main.java.pist.plugins.quest.sub_quest.object;

import main.java.pist.plugins.quest.integrated.object.questDefault.QuestMessageDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestNeedDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestRewardDTO;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SubQuestDTO implements Cloneable {
	int treeLine = 1;
	String key = null;
	String title = null;
	String npc = null;
	QuestNeedDTO need = new QuestNeedDTO();
	List<QuestMessageDTO> message = new ArrayList<QuestMessageDTO>();
	List<Object> quest = new ArrayList<Object>(); //퀘스트 내용을 입력. 나중에 PlayerQuestDTO에서 이 내용을 그대로 복사하여 진행상황 저장
	QuestRewardDTO reward = new QuestRewardDTO();
	
	public int getTreeLine() {
		return treeLine;
	}
	public void setTreeLine(int treeLine) {
		this.treeLine = treeLine;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNpc() {
		return npc;
	}
	public void setNpc(String npc) {
		this.npc = npc;
	}
	public QuestNeedDTO getNeed() {
		return need;
	}
	public void setNeed(QuestNeedDTO need) {
		this.need = need;
	}
	public List<QuestMessageDTO> getMessage() {
		return message;
	}
	public void setMessage(List<QuestMessageDTO> message) {
		this.message = message;
	}
	public List<Object> getQuest() {
		return quest;
	}
	public void setQuest(List<Object> quest) {
		this.quest = quest;
	}
	public QuestRewardDTO getReward() {
		return reward;
	}
	public void setReward(QuestRewardDTO reward) {
		this.reward = reward;
	}
	public SubQuestDTO(String key) {
		this.key = key;
	}
	public SubQuestDTO() {
		
	}
	
	@Override
	public SubQuestDTO clone() throws CloneNotSupportedException{
		SubQuestDTO quest =  null;
		try {
			quest = (SubQuestDTO)super.clone();
			List<Object> copyQuest = new ArrayList<Object>();
		
			for (Object a : quest.getQuest()) {
				Method m = a.getClass().getMethod("clone");
				Object result = m.invoke(a);
				copyQuest.add(result);
			}
			quest.setQuest(copyQuest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quest;
	}
}
