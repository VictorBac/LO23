package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.MessageChat;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.IncorrectMessageOrRightsException;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

/**
 * Created by raphael on 24/11/15.
 */

    public class SendChatMessageMessage extends Message {

        private UserLight sender;
        private MessageChat messageSend;
        public SendChatMessageMessage(UserLight u,MessageChat message) {
            sender = u;
            messageSend = message;
        }

        @Override
        public void process(ConnectionThread threadServer) {
            PokerServer myServ = threadServer.getMyServer();

            Console.log("SendChatMessage message received");
            try {
                myServ.getNetworkManager().getDataInstance().validateMessage(sender,messageSend);
                SendChatMessageMessage chatMessage = new SendChatMessageMessage(sender,messageSend);
                myServ.sendToAll(chatMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
//  Envoie à tout le monde lu message après validation

        }

        @Override
        public void process(ServerLink threadClient) {
            Console.log("message received :" + messageSend.getText());

            // threadClient.getNetworkManager().getDataInstance().remoteUserConnected(newUser);
        }
    }
