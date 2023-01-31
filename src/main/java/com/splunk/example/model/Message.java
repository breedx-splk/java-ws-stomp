package com.splunk.example.model;

public class Message {

    private final String from;
    private final String subject;
    private final String body;

    public Message(String from, String subject, String body) {
        this.from = from;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
