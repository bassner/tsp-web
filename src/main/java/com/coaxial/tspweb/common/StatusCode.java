package com.coaxial.tspweb.common;

import com.coaxial.tspweb.io.GsonWrapper;

/**
 * For status messages, this enum represents the possible status codes.
 */
public enum StatusCode
{
    /**
     * Represents the state when the server is ready to perform calculations and the connection is established.
     */
    READY("READY"),
    /**
     * Represents the state during the calculation.
     */
    CALCULATING("CALC"),
    /**
     * Indicates a server side error.
     */
    ERROR("ERROR"),
    /**
     * Sent as soon as the calculation is done and the client should expect the result.
     */
    DONE("DONE");

    private final String code;

    StatusCode(final String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return code;
    }

    public GsonWrapper toGsonWrapper(GsonWrapper wrapper)
    {
        return (wrapper == null ? new GsonWrapper() : wrapper).add("status", code);
    }

    public GsonWrapper toGsonWrapper()
    {
        return toGsonWrapper(null);
    }
}

