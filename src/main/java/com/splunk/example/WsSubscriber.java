package com.splunk.example;

import com.splunk.example.model.ExampleMessage;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.splunk.example.WsServerController.WS_URL;

@Component
public class WsSubscriber extends StompSessionHandlerAdapter {

    private final static Logger logger = Logger.getLogger(WsSubscriber.class.getName());
    private final ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
    private final WebSocketClient client;
    private final WebSocketStompClient stompClient;
    private StompSession session;

    public WsSubscriber() {
        client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        ObjectMapper mapper = converter.getObjectMapper();

//        mapper.registerModule() // configure to support our types ? boo hoo
        stompClient.setMessageConverter(converter);
    }

    @PostConstruct
    public void runForever() {
        logger.info("Websocket subscriber is starting.");
        pool.scheduleAtFixedRate(this::attemptStartup, 2, 2, TimeUnit.SECONDS);
    }

    @WithSpan
    private void attemptStartup() {
        if (!stompClient.isRunning()) {
            logger.info("Subscriber is attempting connection.");
            stompClient.start();
            stompClient.connect(WS_URL, this);
        }
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;
        pool.shutdown();
        //TODO: Migrate this to WsSubscriber
        session.subscribe("/topic/messages", this);
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        ExampleMessage msg = (ExampleMessage) payload;
        logger.info("   From : " + msg.getFrom());
        logger.info("Subject : " + msg.getSubject());
        logger.info("   Body : " + msg.getBody());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.log(Level.WARNING, "ERROR: ", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.log(Level.WARNING, "TRANSPORT ERROR: ", exception);
    }
}
