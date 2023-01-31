package com.splunk.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
public class WsAppMain extends SpringBootServletInitializer {

    private final ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WsAppMain.class, args);
    }

}
