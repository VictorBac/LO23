package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Created by Jianghan on 20/10/2015.
 */
public class Stats {

    private ArrayList<Integer> listMiseBegin;
    private ArrayList<Integer> listPointsByGame;

    /**
     * Method to update these two ArrayLists
     * @param beginMse : mise de depart du joueur
     * @param points : points par partie du joueur
     */
    public void updateStats(int beginMse, int points){
        listMiseBegin.add(beginMse);
        listPointsByGame.add(points);
    }

    /**
     * Ajoute une nouvelle stat au joueur
     * @param newStat
     * TODO : how does it work?
     */
    public void addNewStat(int newStat){


    }

    /**
     * getter of listPointsByGame
     * @return the attribute listPointsByGame
     */
    public ArrayList<Integer> getStats(){
        return listPointsByGame;
    };
}
