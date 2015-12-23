package fr.utc.lo23.common.data.exceptions;

/**
 * Created by Remy on 31/10/2015.
 */
public class StatsSizeException extends Exception {

    public StatsSizeException(Integer start, Integer end){
        System.out.println("startPointsList size != endPointsList size");
        System.out.println("startPointsList size = " + start);
        System.out.println("endPointsList size = " + end);
    }
}
