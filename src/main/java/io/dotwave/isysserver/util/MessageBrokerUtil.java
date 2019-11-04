package io.dotwave.isysserver.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageBrokerUtil {

    private final SimpMessagingTemplate template;

    public MessageBrokerUtil(@Autowired SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendMessage(String destination, Object message) {
        template.convertAndSend(destination, message);
    }

}
