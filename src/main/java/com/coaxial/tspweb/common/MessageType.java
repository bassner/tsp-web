package com.coaxial.tspweb.common;

import com.coaxial.tspweb.io.GsonWrapper;

/**
 * Represents the type of the message sent to the client. Can be either a status message about the progress, or
 * a message containing the result.
 */
public enum MessageType
{
    STATUS("STATUS"),
    RESULT("RESULT");

    private final String type;

    MessageType(final String code)
    {
        this.type = code;
    }

    @Override
    public String toString()
    {
        return type;
    }

    public GsonWrapper toGsonWrapper(GsonWrapper wrapper)
    {
        return (wrapper == null ? new GsonWrapper() : wrapper).add("type", type);
    }

    public GsonWrapper toGsonWrapper()
    {
        return toGsonWrapper(null);
    }
}
