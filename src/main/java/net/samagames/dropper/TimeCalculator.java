package net.samagames.dropper;

public class TimeCalculator {

    /**
     * This is a simple system which allows you to calculate the time a player has to complete a level.
     * @author Vialonyx
     */

    long start, end, result;

    public TimeCalculator(){
        this.start = System.currentTimeMillis();
    }

    public void stop(){
        this.end = System.currentTimeMillis();
        this.result = end-start;
    }

    public long getSeconds(){
        return (this.result / 1000) % 60;
    }

    public long getMinutes(){
        return (this.result / (1000 * 60)) % 60;
    }

    public long getHours(){
        return (this.result / (1000 * 60 * 60)) % 24;
    }

}