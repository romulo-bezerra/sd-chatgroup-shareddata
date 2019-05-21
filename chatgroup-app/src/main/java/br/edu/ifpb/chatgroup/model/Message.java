package br.edu.ifpb.chatgroup.model;

import java.time.LocalDateTime;

public class Message {
	private String id;
    private String message;
    private LocalDateTime timestamp;

    public Message() {}

    public Message(String id, String message, LocalDateTime timestamp) {
		this.id = id;
		this.message = message;
		this.timestamp = timestamp;
	}
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
