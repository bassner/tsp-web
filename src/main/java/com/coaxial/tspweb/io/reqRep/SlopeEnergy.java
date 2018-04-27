package com.coaxial.tspweb.io.reqRep;

/**
 * Represents the behavior of the vehicle on a slope. This means that a vehicle can use more or less power on positive or negative slopes.
 * In this case, this means that a vehicle i.e. uses 10% more power if the slope is greater than 3%.
 */
public class SlopeEnergy
{
    /**
     * The threshold from which the additional or less consumptions occurs.
     */
    private double threshold;
    /**
     * The amount of the addtional or less consumption.
     */
    private double value;

    public SlopeEnergy(double threshold, double value)
    {
        this.threshold = threshold;
        this.value = value;
    }

    public double getThreshold()
    {
        return threshold;
    }

    public void setThreshold(double threshold)
    {
        this.threshold = threshold;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "SlopeEnergy{" +
                "threshold=" + threshold +
                ", value=" + value +
                '}';
    }
}
