package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.MessageChat;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.IncorrectMessageOrRightsException;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.UUID;

/**
 * Created by raphael on 24/11/15.
 * Sends the new chat message to the other players
 */

    public class SendChatMessageMessage extends Message {

        private UserLight sender;
        private MessageChat messageSend;
        private UUID tableConcerned;
        public SendChatMessageMessage(UserLight u,MessageChat message,UUID table) {
            this.sender = u;
            this.messageSend = message;
            this.tableConcerned = table;
        }

        @Override
        public void process(ConnectionThread threadServer) {
            PokerServer myServ = threadServer.getMyServer();

            Console.log("SendChatMessage message received");
            try {
                //the message is not send only if the user is a spectator and the chat for spectator is not accepted on the table
                if(myServ.getNetworkManager().getDataInstance().getTableFromId(tableConcerned).isAcceptChatSpectator()
                        ||  !myServ.getNetworkManager().getDataInstance().getTableFromId(tableConcerned).determineTypeUser(sender).equals(EnumerationTypeOfUser.SPECTATOR)) {
                    //the message can be send to other users and saved on the server
                    myServ.getNetworkManager().getDataInstance().saveMessageChat(tableConcerned,messageSend);
                    SendChatMessageMessage chatMessage = new SendChatMessageMessage(sender,messageSend,tableConcerned);
                    myServ.sendToListOfUsers(myServ.getNetworkManager().getDataInstance().getUsersByTable(tableConcerned),chatMessage);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
//  Send message to all after it has been stored on the Server

        }

        @Override
        public void process(ServerLink threadClient) {
            Console.log("message received :" + messageSend.getText());

            threadClient.getNetworkManager().getDataInstance().transmitMessage(messageSend);
        }
    }
