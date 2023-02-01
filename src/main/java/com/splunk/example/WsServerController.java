package com.splunk.example;

import com.splunk.example.model.ExampleMessage;
import com.splunk.example.model.TimestampedMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WsServerController {

    public static final String WS_URL = "ws://localhost:8080/tube";

    @MessageMapping("/tube")
    @SendTo("/topic/messages")
    public TimestampedMessage send(ExampleMessage exampleMessage) throws Exception {
        var time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return new TimestampedMessage(time, exampleMessage.getFrom(), exampleMessage.getSubject(), exampleMessage.getBody());
    }

}
