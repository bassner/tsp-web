package com.coaxial.tspweb.io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * A simple wrapper around Gson that provides a method chaining mechanism that simplifies the creation of simple json
 * objects. It also provides a method to include the sending process into the method chain.
 */
public class GsonWrapper
{
    private JsonObject object;

    public GsonWrapper()
    {
        object = new JsonObject();
    }

    /**
     * Add a string key-value pair to the json object.
     *
     * @param key the key
     * @param val the value
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper add(String key, String val)
    {
        object.addProperty(key, val);
        return this;
    }

    /**
     * Add a Number key-value pair to the json object.
     *
     * @param key the key
     * @param val the value
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper add(String key, Number val)
    {
        object.addProperty(key, val);
        return this;
    }

    /**
     * Add a Number-Array key-value pair to the json object.
     *
     * @param key the key
     * @param val the value
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper add(String key, Number[] val)
    {
        JsonArray array = new JsonArray(val.length);
        for(Number n:val)
            array.add(n);
        object.add(key, array);
        return this;
    }

    /**
     * Add a boolean key-value pair to the json object.
     *
     * @param key the key
     * @param val the value
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper add(String key, boolean val)
    {
        object.addProperty(key, val);
        return this;
    }

    /**
     * Add a custom JsonElement to this json object.
     * @param key the key
     * @param element the element
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper add(String key, JsonElement element)
    {
        object.add(key, element);
        return this;
    }

    /**
     * Add a string key-value pair to the json object if the given condition is true.
     *
     * @param key the key
     * @param val the value
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper addIf(boolean condition, String key, String val)
    {
        if (condition)
            add(key, val);
        return this;
    }

    /**
     * Add a Number key-value pair to the json object if the given condition is true.
     *
     * @param key the key
     * @param val the value
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper addIf(boolean condition, String key, Number val)
    {
        if (condition)
            add(key, val);
        return this;
    }

    /**
     * Add a boolean key-value pair to the json object if the given condition is true.
     *
     * @param key the key
     * @param val the value
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper addIf(boolean condition, String key, boolean val)
    {
        if (condition)
            add(key, val);
        return this;
    }

    /**
     * Sends the json object as a text message via the given WebSocketSession. This should be the last call in the
     * method chain.
     *
     * @param session the session to send the json object over
     * @return true if sending the message was successful; false otherwise
     */
    public boolean send(WebSocketSession session)
    {
        try
        {
            synchronized (session)
            {
                session.sendMessage(new TextMessage(new Gson().toJson(object)));
            }
            return true;
        } catch (IOException | IllegalStateException e)
        {
            return false;
        }
    }

    /**
     * Logs the content of this wrapper onto the console (Level info) using the given logger instance.
     * @param message the message to prepend
     * @param logger the logger instance to use
     * @return this GsonWrapper for method chaining
     */
    public GsonWrapper log(String message, Logger logger)
    {
        logger.info(message + new Gson().toJson(object));
        return this;
    }
}
