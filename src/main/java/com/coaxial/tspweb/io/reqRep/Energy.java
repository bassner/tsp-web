package com.coaxial.tspweb.io.reqRep;

/**
 * Represents the energy properties of the used vehicle, i.e. additional consumption on slopes
 */
public class Energy
{
    /**
     * The main consumption of the vehicle, in kWh/km
     */
    private double consumption;

    /**
     * Whether the linear interpolation of additional energy consumption and recuperation should be interpolated
     * instead of using the provided thresholds as limit values.
     */
    private boolean interpolation;

    /**
     * The positive slope from which the vehicle consumes more power.
     */
    private SlopeEnergy additional;

    /**
     * The negative slope from which the vehicle consumes less power.
     */
    private SlopeEnergy recuperation;

    public Energy(double consumption, boolean interpolation, SlopeEnergy additional, SlopeEnergy recuperation)
    {
        this.consumption = consumption;
        this.interpolation = interpolation;
        this.additional = additional;
        this.recuperation = recuperation;
    }

    public double getConsumption()
    {
        return consumption;
    }

    public void setConsumption(double consumption)
    {
        this.consumption = consumption;
    }

    public boolean isInterpolation()
    {
        return interpolation;
    }

    public void setInterpolation(boolean interpolation)
    {
        this.interpolation = interpolation;
    }

    public SlopeEnergy getAdditional()
    {
        return additional;
    }

    public void setAdditional(SlopeEnergy additional)
    {
        this.additional = additional;
    }

    public SlopeEnergy getRecuperation()
    {
        return recuperation;
    }

    public void setRecuperation(SlopeEnergy recuperation)
    {
        this.recuperation = recuperation;
    }

    @Override
    public String toString()
    {
        return "Energy{" +
                "consumption=" + consumption +
                ", interpolation=" + interpolation +
                ", additional=" + additional +
                ", recuperation=" + recuperation +
                '}';
    }
}
