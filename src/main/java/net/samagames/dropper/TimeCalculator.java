package net.samagames.dropper;

/*
 * This file is part of Dropper.
 *
 * Dropper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dropper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dropper.  If not, see <http://www.gnu.org/licenses/>.
 */
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