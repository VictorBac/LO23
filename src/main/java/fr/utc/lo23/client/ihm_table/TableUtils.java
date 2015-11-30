package fr.utc.lo23.client.ihm_table;

import javafx.geometry.Point2D;

/**
 * Created by Bastien F. on 23/11/2015.
 */
public class TableUtils {
    public static final Point2D tableWidthHeight = new Point2D(765.0, 315.0), tableCenter = new Point2D(510.0, 215.0);

    /**
     *
     * @param playerId Id of player between 1 and maxPlayers
     * @param maxPlayers Max number of players
     * @param center Center of table
     * @param width_height Width and Height of table
     * @return player position around table
     */
    public static Point2D getPlayerPosition(int playerId, int maxPlayers, Point2D center, Point2D width_height) {
        // x = width / 2 * cos(angle) + center.x
        // y = height / 2 * sin(angle) + center.y
        //angle
        //Angle depart = 270 - ((360 / maxPlayers) * (playerId - 1))
        double angle = (1.0/2.0 * Math.PI) + ((2.0 * Math.PI / maxPlayers) * (playerId - 1));
        double x = (width_height.getX() / 2.0) * Math.cos(angle) + center.getX();
        double y = (width_height.getY() / 2.0) * Math.sin(angle) + center.getY();
        return new Point2D(x, y);
    }

    /**
     *
     * @param playerId Id of player between 1 and maxPlayers
     * @param maxPlayers Max number of players
     * @return player position around table
     */
    public static Point2D getPlayerPosition(int playerId, int maxPlayers) {
        return getPlayerPosition(playerId, maxPlayers, tableCenter, tableWidthHeight);
    }

    /**
     *
     * @param maxPlayers Max number of players
     * @param center Center of table
     * @param width_height Width and Height of table
     * @return All players positions
     */
    public static Point2D[] getPlayersPosition(int maxPlayers, Point2D center, Point2D width_height) {
        Point2D[] playersPosition = new Point2D[maxPlayers];
        for(int idPlayer = 1; idPlayer <= maxPlayers; ++idPlayer) {
            playersPosition[idPlayer - 1] = getPlayerPosition(idPlayer, maxPlayers, center, width_height);
        }
        return playersPosition;
    }

    /**
     *
     * @param maxPlayers Max number of players
     * @return All players positions
     */
    public static Point2D[] getPlayersPosition(int maxPlayers) {
        return getPlayersPosition(maxPlayers, tableCenter, tableWidthHeight);
    }
}
