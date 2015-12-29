package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;
import fr.utc.lo23.common.data.exceptions.StatsSizeException;

/**
 * Class for statistic.
 * Created by Jianghan on 20/10/2015.
 */
public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
    //player's start bet
    private ArrayList<Integer> startPointsList;
    //player's points per game
    private ArrayList<Integer> endPointsList;

    /**
     * Default constructor
     */
    public Stats() {
        this.startPointsList = new ArrayList<>();
        this.endPointsList = new ArrayList<>();
    }

    /**
     * Set startPoints List and endPoints List.
     * @param start start points list
     * @param end end points list
     * @return 1 if successful
     * @throws StatsSizeException
     */
    public int setStats(ArrayList<Integer> start, ArrayList<Integer> end) throws StatsSizeException {
        if (start.size() != end.size()) {
            throw new StatsSizeException(start.size(), end.size());
        } else {
            this.startPointsList = start;
            this.endPointsList = end;
            return 1;
        }
    }

    /**
     * Add a new record to the statistic
     * @param startPoint mise de depart du joueur
     * @param endPoints points par partie du joueur
     * @return 1 if successful
     * @throws StatsSizeException
     */
    public int addNewStat(Integer startPoint, Integer endPoints) throws StatsSizeException {
        if (startPoint == null) {
            throw new StatsSizeException(0, 1);
        }
        if (endPoints == null) {
            throw new StatsSizeException(1, 0);
        }
        startPointsList.add(startPoint);
        endPointsList.add(endPoints);
        return 1;
    }

    /**
     * get the game's number
     * @return : number
     */
    public int getGameNumber() {
        return this.endPointsList.size();
    }

    /**
     * get end points
     * @return : amount of points
     */
    public Integer getMeanEndPoints() {
        Integer total = 0;
        for (Integer point : endPointsList) {
            total += point;
        }
        if (endPointsList.size() == 0) {
            return 0;
        } else {
            return total/endPointsList.size();
        }
    }

    /**
     * get start points
     * @return : amount of points
     */
    public Integer getMeanStartPoints() {
        Integer total = 0;
        for (Integer point : startPointsList) {
            total += point;
        }
        for (Integer point : endPointsList) {
            total += point;
        }
        return total;
    }

    /**
     * get total points
     * @return : amount of points
     */
    public Integer getTotalPoints() {
        Integer total = 0;
        for (Integer point : startPointsList) {
            total += point;
        }
        if (startPointsList.size() == 0) {
            return 0;
        } else {
            return total/startPointsList.size();
        }
    }

    /******************* Getters **********************/
    /**
     * get start points' list
     * @return : list of points
     */
    public ArrayList<Integer> getStartPointsList() {
        return startPointsList;
    }

    /**
     * get end points' list
     * @return : list of points
     */
    public ArrayList<Integer> getEndPointsList() {
        return endPointsList;
    }
}
