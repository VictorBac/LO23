package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for statistic. Hope it will be used.
 * Created by Jianghan on 20/10/2015.
 */
public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
    //mise de depart du joueur
    private ArrayList<Integer> startPointsList;
    //points par partie du joueur
    private ArrayList<Integer> endPointsList;

    public Stats() {
        this.startPointsList = new ArrayList<>();
        this.endPointsList = new ArrayList<>();
    }

    /**
     * Set startPoints List and endPoints List.
     * @param start start points list
     * @param end end points list
     */
    public void setStats(ArrayList<Integer> start, ArrayList<Integer> end){
        this.startPointsList = start;
        this.endPointsList = end;
    }

    /**
     * Add a new record to the statistic
     * @param startPoint mise de depart du joueur
     * @param endPoints points par partie du joueur
     */
    public void addNewStat(int startPoint, int endPoints){
        startPointsList.add(startPoint);
        endPointsList.add(endPoints);
    }

    /******************* Gettor **********************/
    public ArrayList<Integer> getStartPointsList() {
        return startPointsList;
    }

    public ArrayList<Integer> getEndPointsList() {
        return endPointsList;
    }
}
