package fr.utc.lo23.common.data;

import fr.utc.lo23.client.network.main.Console;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mar on 05/12/2015.
 */
public class HandTest {

    @Test
    public void testDistributeCard() throws Exception {

        Hand oneHand = new Hand();
        oneHand.getListPlayerHand().add(new PlayerHand());
        oneHand.getListPlayerHand().add(new PlayerHand());
        oneHand.getListPlayerHand().add(new PlayerHand());
        oneHand.getListPlayerHand().add(new PlayerHand());

        oneHand.distributeCard();
        Console.log("after distrib cards ");
        for(Card c : oneHand.getListCardField()){
            Console.log("field cards :" + c.getId());
        }
        for(PlayerHand p : oneHand.getListPlayerHand()){
            for(Card c : p.getListCardsHand()){
                Console.log("player " + p.toString() + " cards :" + c.getId());

            }

        }
    }
}