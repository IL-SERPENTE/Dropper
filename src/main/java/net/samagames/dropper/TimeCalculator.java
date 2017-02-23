package net.samagames.dropper;

/**
 * @author Vialonyx
 */

public class TimeCalculator {

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