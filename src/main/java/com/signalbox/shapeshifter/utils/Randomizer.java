package com.signalbox.shapeshifter.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Randomizer
{
    static public List<int[]> generateRandomShape(int size,int startX,int startY,int circuitSize)
    {
        Random rand = new Random();
        List<int[]> list = new ArrayList<int[]>();
        list.add(new int[]{startX,startY});

        int remainingPoints = size - 1;
        while (remainingPoints > 0)
        {
            int[] currentPoint = list.getLast();
            int maxIterations = 10;
            int iterations = 0;
            int[] nextPoint = new int[]{(currentPoint[0] + rand.nextInt(3)-1),
                    (currentPoint[1] + rand.nextInt(3)-1)};
            while((listContains(list,nextPoint) ||
                    (nextPoint[0] == circuitSize || nextPoint[1] == circuitSize) ||
                    (nextPoint[0] < 0 || nextPoint[1] < 0) )&&
                    (iterations < maxIterations))
            {
                System.out.println(iterations);
                int randomInt1 = rand.nextInt(3)-1;
                int randomInt2 = rand.nextInt(3)-1;
                nextPoint = new int[]{(currentPoint[0] + randomInt1),
                        (currentPoint[1] + randomInt2)};
                iterations++;
            }
            list.add(nextPoint);
            remainingPoints--;
        }
        return list;
    }
    static private boolean listContains(List<int[]> list,int[] item)
    {
        for(int[] arr : list)
        {
            if(Arrays.equals(arr,item))
            {
                return true;
            }
        }
        return false;
    }
}
