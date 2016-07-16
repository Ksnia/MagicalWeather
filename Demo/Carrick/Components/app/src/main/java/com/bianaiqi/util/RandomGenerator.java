package com.bianaiqi.util;

import java.util.Random;

/**
 * Created by Carrick on 2016/7/11.
 */
public class RandomGenerator {
    private static final Random RANDOM = new Random();

    public float getRandom(float lower, float upper){
        float min = Math.min(lower,upper);
        float max = Math.max(lower,upper);
        return getRandom(max-min) + min;
    }

    public float getRandom(float upper){
        return RANDOM.nextFloat()*upper;
    }

    public int getRandom(int upper){
        return RANDOM.nextInt(upper);
    }


    /**
     * test api
     */
    public static int i;
    public static void generatorWeatherType(){
        i = (int) (Math.random() * 7);
    }

    public static int getWeatherType(){
        return i;
    }
}
