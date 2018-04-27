package com.coaxial.tspweb.model;

import com.google.maps.model.DistanceMatrixElement;

import java.util.Comparator;

/**
 * This special class creates an index array, which is to be externally sorted using this comparator.
 * This way, you can get an index array in order of the sizes of the original array  to retrieve the elements
 * of each line of the matrix in the correct order without ordering itself.
 */
public class ElementIndexComparator implements Comparator<Integer>
{
    private final DistanceMatrixElement[] array;

    public ElementIndexComparator(DistanceMatrixElement[] array)
    {
        this.array = array;
    }

    public Integer[] createIndexArray()
    {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
        {
            indexes[i] = i; // Autoboxing
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2)
    {
        return (int) (array[index1].distance.inMeters - array[index2].distance.inMeters);
    }
}
