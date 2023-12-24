package id.achfajar.challenge8.dto;

import lombok.Data;

@Data
public class Message {
    private String from;
    private String to;
    private String message;
    public Message(String message) {
        this.message = message;
    }
}
