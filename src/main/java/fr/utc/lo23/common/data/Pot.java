package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Classe repr�sentant la somme totale mis�e dans un pot.
 * Il peut y avoir plusieurs pots s�par�s lors du m�me tour.
 * Created by Haroldcb on 21/10/2015.
 */
public class Pot {
    /**
     * valeurPot : valeur totale dans le pot
     * players : joueurs ayant mis� dans le pot
     */
    private ArrayList<Integer> valeurPot;
    private ArrayList<UserLight> players;
}

