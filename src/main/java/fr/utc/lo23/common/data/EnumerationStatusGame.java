package fr.utc.lo23.common.data;

/**
 * List status of the game
 * Created by Ying on 21/10/2015.
 * Enumeration that corresponds to the different status that a Game can have, depending on the situation
 */
public enum EnumerationStatusGame {
    /**
     * waiting : game has not started yet
    * playing : game currently playing
    * finished : game finished but players still on table
    * closed : game finished and players are gone
    */
    Waiting, Playing, Finished, Replay
}
