package id.achfajar.challenge8.service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface NotificationPromo {
    void sendMessageToToken(UUID productID, Integer amount)
            throws InterruptedException, ExecutionException;
}
