package main.java.pist.plugins.quest.main_quest.object;

import main.java.pist.plugins.quest.integrated.object.questDefault.QuestMessageDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestNeedDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestRewardDTO;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainQuestDTO implements Cloneable {
	int treeLine = 1;
	String key = null;
	int priorty = 1;
	String title = null;
	String npc = null;
	boolean talk = false;
	QuestNeedDTO need = new QuestNeedDTO();
	List<QuestMessageDTO> message = new ArrayList<QuestMessageDTO>();
	List<Object> quest = new ArrayList<Object>(); // 퀘스트 내용을 입력. 나중에 PlayerQuestDTO에서 이 내용을 그대로 복사하여 진행상황 저장
	QuestRewardDTO reward = new QuestRewardDTO();
	String nextQuest = null;

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
	public int getPriorty() {
		return priorty;
	}

	public void setPriorty(int priorty) {
		this.priorty = priorty;
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

	public boolean isTalk() {
		return talk;
	}

	public void setTalk(boolean talk) {
		this.talk = talk;
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

	public String getNextQuest() {
		return nextQuest;
	}

	public void setNextQuest(String nextQuest) {
		this.nextQuest = nextQuest;
	}

	public MainQuestDTO(String key) {
		this.key = key;
	}

	public MainQuestDTO() {

	}
	
	public MainQuestDTO(int treeLine, String key, int priorty, String title, String npc, boolean talk,
			QuestNeedDTO need, List<QuestMessageDTO> message, List<Object> quest, QuestRewardDTO reward,
			String nextQuest) {
		super();
		this.treeLine = treeLine;
		this.key = key;
		this.priorty = priorty;
		this.title = title;
		this.npc = npc;
		this.talk = talk;
		this.need = need;
		this.message = message;
		this.quest = quest;
		this.reward = reward;
		this.nextQuest = nextQuest;
	}

	@Override
	public MainQuestDTO clone() { //Quest List 깊은복사 해야함
		MainQuestDTO quest = null;
		try {
			quest = (MainQuestDTO)super.clone();
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
