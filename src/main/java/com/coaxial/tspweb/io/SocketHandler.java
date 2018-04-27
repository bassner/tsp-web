package com.coaxial.tspweb.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles connection building.
 */
@Component
public class SocketHandler extends TextWebSocketHandler
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Maps session ids to the corresponding SessionWorkers.
     */
    private Map<String, SessionWorker> workerMap = new ConcurrentHashMap<>();

    /**
     * Receives text messages from the client and passes them to the correct {@link SessionWorker}.
     *
     * @param session the session the message belongs to
     * @param message the message
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
    {
        log.info("Received calculation request: " + message.getPayload());
        Optional.ofNullable(workerMap.get(session.getId()))
                .ifPresent(s -> s.onMessage(message.getPayload()));
    }

    /**
     * Creates a new {@link SessionWorker}; should be invoked by Spring only
     *
     * @param session the newly created session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
    {
        workerMap.put(session.getId(), new SessionWorker(session));
    }

    /**
     * Removes the {@link SessionWorker} corresponding to the given session.
     * Should be invoked by Spring only
     *
     * @param session the session that has been closed
     * @param status  the close status; unused
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
    {
        workerMap.remove(session.getId());
    }
}
