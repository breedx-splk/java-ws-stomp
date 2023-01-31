package com.splunk.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class WsClient extends SpringBootServletInitializer {

    private final ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void runForever(){
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

//        stompClient.connect();

        pool.scheduleAtFixedRate(this::sendOne, 0, 2, TimeUnit.SECONDS);




/*        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // OpenTelemetry instrumentation Code
        // This is definitely wrong.
        URL url = new URL("http://127.0.0.1:" + 8080);
        HttpURLConnection transportLayer = (HttpURLConnection) url.openConnection();

        Span span =
                tracer.spanBuilder("Consumer-POST /").setSpanKind(SpanKind.CONSUMER).startSpan();

        try (Scope scope = span.makeCurrent()) {
            // **************
            ClientOneSessionHandler clientOneSessionHandler = new ClientOneSessionHandler();
            ListenableFuture<StompSession> sessionAsync =
                    stompClient.connect("ws://localhost:8080/websocket-server", clientOneSessionHandler);
            StompSession session = sessionAsync.get();

            session.subscribe("/topic/messages", clientOneSessionHandler);

            UUID uuid = UUID.randomUUID();

            span.setAttribute("UUID", uuid.toString());
            // **************

            textMapPropagator.inject(Context.current(), transportLayer, setter);
            System.out.println("client Context after inject is: " + Context.current());

            while (true) {
                try {
                    session.send("/app/process-message", new IncomingMessage(uuid.toString() + " " + System.currentTimeMillis()));
                    Thread.sleep(10000);
                } finally {
                    // Close the span
                    span.end();
                }
            }
        }*/
    }

    private void sendOne() {

    }
}
