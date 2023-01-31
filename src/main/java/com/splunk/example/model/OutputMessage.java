package com.splunk.example.model;

public class OutputMessage {
    private final String time;
    private final String from;
    private final String subject;
    private final String body;

    public OutputMessage(String time, String from, String subject, String body) {
        this.time = time;
        this.from = from;
        this.subject = subject;
        this.body = body;
    }

    public String getTime() {
        return time;
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
