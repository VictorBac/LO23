package fr.utc.lo23.common.data;

/**
 * Classe représentant la somme totale misée dans un pot.
 * Il peut y avoir plusieurs pots séparés lors du même tour.
 * Created by Haroldcb on 21/10/2015.
 */
public class Pot {
    /**
     * valeurPot : valeur totale dans le pot
     * players : joueurs ayant misé dans le pot
     */
    private ArrayList<int> valeurPot;
    private ArrayList<Userlight> players;
}

