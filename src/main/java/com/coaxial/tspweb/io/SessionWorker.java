package com.coaxial.tspweb.io;

import com.coaxial.tspweb.common.MessageType;
import com.coaxial.tspweb.common.StatusCode;
import com.coaxial.tspweb.io.reqRep.ClientRequest;
import com.coaxial.tspweb.model.Solver;
import com.coaxial.tspweb.model.SolverResult;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

/**
 * Handles message exchange with a single client.
 */
public class SessionWorker
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * The session corresponding to this client.
     */
    private WebSocketSession session;

    SessionWorker(WebSocketSession session)
    {
        this.session = session;
        //Send READY Status to indicate full initialization
        StatusCode.READY.toGsonWrapper(MessageType.STATUS.toGsonWrapper()).send(session);
    }

    /**
     * Handles a incoming message. This must be a TSP solve request and should be able to be transformed to a
     * {@link ClientRequest} object by GSON.
     * @param json the message body; must be a JSON string that can be transformd to a {@link ClientRequest} object
     */
    void onMessage(String json)
    {
        ClientRequest request = new Gson().fromJson(json, ClientRequest.class);
        SolverResult result = new Solver().exactSolve(request, this);

        //try multiple times, just in case the socket fails
        if (result != null)
            for(int i=0; i<5 && !result.toGsonWrapper(MessageType.RESULT.toGsonWrapper())
                    .log("Sending result: ", log).send(session); ++i);
    }

    /**
     * Sends a status update about current operations to the client.
     * @param status the current StatusCode; must be one of the members of {@link StatusCode}.
     * @param message a text message to be displayed at the client
     * @param percent if the current status includes a progress, this value should indicate that progress; set to -1 if unused
     */
    public void tellStatus(StatusCode status, String message, double percent)
    {
        MessageType.STATUS.toGsonWrapper(status == null ? null : status.toGsonWrapper())
                .addIf(message != null, "message", message)
                .addIf(percent >= 0D, "progress", percent)
                .log("Sending status message: " , log)
                .send(session);

    }

    /**
     * Sends a status update about current operations to the client.
     * @param status the current StatusCode; must be one of the members of {@link StatusCode}.
     * @param message a text message to be displayed at the client
     */
    public void tellStatus(StatusCode status, String message)
    {
        tellStatus(status, message, -1);
    }

    /**
     * Sends a status update about current operations to the client.
     * @param status the current StatusCode; must be one of the members of {@link StatusCode}.
     */
    public void tellStatus(StatusCode status)
    {
        tellStatus(status, null, -1);
    }
}
