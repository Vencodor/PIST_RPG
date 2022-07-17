package main.java.pist.plugins.quest.integrated.object.questDefault;

public class QuestMessageDTO {
	String who = null;
	String message = null;
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public QuestMessageDTO() {
		
	}
	public QuestMessageDTO(String who, String message) {
		super();
		this.who = who;
		this.message = message;
	}
	
}
