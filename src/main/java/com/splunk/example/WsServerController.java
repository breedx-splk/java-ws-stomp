package com.splunk.example;

import com.splunk.example.model.ExampleMessage;
import com.splunk.example.model.TimestampedMessage;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WsServerController {

    public static final String WS_URL = "ws://localhost:8080/tube";
    private SimpMessagingTemplate template;

    @Autowired
    public WsServerController(SimpMessagingTemplate template){
        this.template = template;
    }

    // This is how you might normally do a mapping, but it doesn't provide a way
    // to inject headers into the stop message....so we have to do it the hard way
    // (see below)
//    @MessageMapping("/tube")
//    @SendTo("/topic/messages")
//    public TimestampedMessage send(ExampleMessage exampleMessage) throws Exception {
//
//        var time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        return new TimestampedMessage(time, exampleMessage.getFrom(), exampleMessage.getSubject(), exampleMessage.getBody());
//    }

    @MessageMapping("/tube")
    public void templateSend(ExampleMessage exampleMessage, MessageHeaders msgHeaders,
                                   SimpMessageHeaderAccessor headerAccessor) throws Exception {

        Context extractedContext = GlobalOpenTelemetry.getPropagators().getTextMapPropagator()
                .extract(Context.current(), headerAccessor, new TextMapGetter<SimpMessageHeaderAccessor>() {
                    @Override
                    public Iterable<String> keys(SimpMessageHeaderAccessor carrier) {
                        carrier.toMap();
                    }

                    @Nullable
                    @Override
                    public String get(@Nullable SimpMessageHeaderAccessor carrier, String key) {
                        return carrier.getFirstNativeHeader(key);
                    }
                });

//        var traceparent = headerAccessor.getFirstNativeHeader("traceparent");

//        Context extractedContext = GlobalOpenTelemetry.getPropagators().getTextMapPropagator()
//                .extract(Context.current(), httpExchange, getter);

        var time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        var tsMsg = new TimestampedMessage(time, exampleMessage.getFrom(), exampleMessage.getSubject(), exampleMessage.getBody());

        Map<String, Object> headers = new HashMap<>(msgHeaders);
        GlobalOpenTelemetry.getPropagators()
                .getTextMapPropagator()
                .inject(Context.current(), headers, (carrier, key, value) -> {
                    if(carrier != null){
                        carrier.put(key, value);
                    }
                });

//        headers.put("traceparent", traceparent);

        template.convertAndSend("/topic/messages", tsMsg, headers);
    }

}
