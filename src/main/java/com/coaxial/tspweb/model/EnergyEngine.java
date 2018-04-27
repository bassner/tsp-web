package com.coaxial.tspweb.model;

import com.coaxial.tspweb.io.reqRep.Energy;
import com.google.maps.model.DistanceMatrixElement;

/**
 * Performs calculations regarding the electric vehicle used to travel.
 */
public class EnergyEngine
{
    /**
     * Performs calculations to determine the energy consumption of the way of each location to ever other.
     * This is used instead of the pure distances to perform the TSP solving.
     *
     * @param distances  the pure distances between the locations
     * @param elevations the elevations of the locations, in the same order as a row in distances
     * @param energy     the energy properties of the vehicle
     * @return the power consumption matrix, stable
     */
    public static double[][] getEnergyConsumptionMatrix(DistanceMatrixElement[][] distances, double[] elevations,
                                                        Energy energy)
    {
        double[][] result = new double[distances.length][distances[0].length];

        for (int i = 0; i < result.length; ++i)
            for (int j = 0; j < result.length; ++j)
            {
                double distance = distances[i][j].distance.inMeters * 1D;
                double slope = (elevations[j] - elevations[i]) / distance * 100D;
                result[i][j] = (distance * energy.getConsumption()); //general energy consumption; we don't round here since this would not affect calculation results
                if(i==0 && j==1)
                    System.out.println("hey");
                //now modify consumption based on slopes
                if (energy.isInterpolation())
                {
                    if(slope > 0 && energy.getAdditional().getThreshold() != 0)
                        result[i][j] *= (1D + ((slope / energy.getAdditional().getThreshold()) *
                            (energy.getAdditional().getValue() / 100D)));
                    else if(slope < 0 && energy.getRecuperation().getThreshold() != 0)
                        //using '1D +' instead of '1D -' since slope is negative and threshold positive
                        result[i][j] *= (1D + ((slope / energy.getRecuperation().getThreshold()) *
                                (energy.getRecuperation().getValue() / 100D)));
                } else
                {
                    result[i][j] *= (slope >= energy.getAdditional().getThreshold() ? (1D + energy.getAdditional().getValue() / 100D) : 1)
                            * (slope <= -1D * energy.getRecuperation().getThreshold() ? (1D - energy.getRecuperation().getValue() / 100D) : 1);
                }
            }
        return result;
    }
}
