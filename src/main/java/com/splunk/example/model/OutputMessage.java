package com.splunk.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS;

@JsonTypeInfo(use= CLASS, property="class")
public class OutputMessage {
    private final String time;
    private final String from;
    private final String subject;
    private final String body;


    @JsonCreator
    public OutputMessage(@JsonProperty("time") String time,
                         @JsonProperty("from") String from,
                         @JsonProperty("subject") String subject,
                         @JsonProperty("body") String body) {
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
