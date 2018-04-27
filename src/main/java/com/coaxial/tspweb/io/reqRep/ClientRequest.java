package com.coaxial.tspweb.io.reqRep;

import com.google.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Represents all data originating from a client.
 */
public class ClientRequest
{
    /**
     * A list of locations for which the TSP should be solved.
     */
    private ArrayList<LatLng> locations;

    /**
     * The energy properties of the vehicle.
     */
    private Energy energy;

    public ClientRequest(ArrayList<LatLng> locations, Energy energy)
    {
        this.locations = locations;
        this.energy = energy;
    }

    public ArrayList<LatLng> getLocations()
    {
        return locations;
    }

    public void setLocations(ArrayList<LatLng> locations)
    {
        this.locations = locations;
    }

    public Energy getEnergy()
    {
        return energy;
    }

    public void setEnergy(Energy energy)
    {
        this.energy = energy;
    }

    @Override
    public String toString()
    {
        return "ClientRequest{" +
                "locations=" + locations +
                ", energy=" + energy +
                '}';
    }
}
