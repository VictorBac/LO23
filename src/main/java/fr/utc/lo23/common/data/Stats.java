package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Created by Rémy on 20/10/2015.
 */
public class Stats {

    private ArrayList<Integer> listMiseBegin;
    private ArrayList<Integer> listPointsByGame;

    /**
     * permet de mettre à jour les deux ArrayLists
     * @param beginMse : mise de départ du joueur
     * @param points : points par partie du joueur
     */
    public void updateStats(int beginMse, int points){
        listMiseBegin.add(beginMse);
        listPointsByGame.add(points);
    };

    /**
     * Ajoute une nouvelle stat au joueur
     * @param newStat ?
     * TODO : préciser usage fonction
     */
    public void addNewStat(int newStat){


    };

    /**
     * getter de listPointsByGame
     * @return l'attribut listPointsByGame
     */
    public ArrayList<Integer> getStats(){
        return listPointsByGame;
    };
}
