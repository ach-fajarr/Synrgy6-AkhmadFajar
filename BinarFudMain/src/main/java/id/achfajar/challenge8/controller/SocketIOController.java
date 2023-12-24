package id.achfajar.challenge8.controller;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import id.achfajar.challenge8.dto.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SocketIOController {
    private final SocketIOServer socketIOServer;


    public SocketIOController(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;

        this.socketIOServer.addConnectListener(onUserConnected);
        this.socketIOServer.addDisconnectListener(onUserDisconnected);
        this.socketIOServer.addEventListener("chat", Message.class, onMessageSent);
    }

    private ConnectListener onUserConnected = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient socketIOClient) {
            log.info("Controller onConnected");
        }
    };


    private DisconnectListener onUserDisconnected = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient socketIOClient) {
            log.info("Controller onDisconnected");
        }
    };

    private final DataListener<Message> onMessageSent = new DataListener<>() {
        @Override
        public void onData(SocketIOClient socketIOClient, Message message, AckRequest ackRequest) throws Exception {
            log.info("Pesan dari"+ message.getFrom()+" ke "+ message.getTo()+": "+ message.getMessage());

            socketIOServer.getBroadcastOperations()
                    .sendEvent(message.getTo(), socketIOClient, message.getMessage());

            ackRequest.sendAckData("Pesan sudah terkirim ke "+ message.getTo());
        }
    };

}
