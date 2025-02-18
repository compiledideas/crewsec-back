package com.compiledideas.crewsecback.pushnotification.services;

import com.compiledideas.crewsecback.exceptions.NotificationException;
import com.compiledideas.crewsecback.pushnotification.model.NotificationRequest;
import io.github.jav.exposerversdk.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class FCMService {

    private Logger logger = LoggerFactory.getLogger(FCMService.class);

    public  void sendPushNotification(NotificationRequest request) throws PushClientException {
        if (!PushClient.isExponentPushToken(request.getToken())) throw new Error("Token:" + request.getToken() + " is not a valid token.");

        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().add(request.getToken());
        expoPushMessage.setTitle(request.getTitle());
        expoPushMessage.setBody(request.getBody());
        expoPushMessage.setData(new HashMap<>());

        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        expoPushMessages.add(expoPushMessage);

        PushClient client = new PushClient();
        List<List<ExpoPushMessage>> chunks = client.chunkPushNotifications(expoPushMessages);

        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();

        for (List<ExpoPushMessage> chunk : chunks) {
            messageRepliesFutures.add(client.sendPushNotificationsAsync(chunk));
        }

        List<ExpoPushTicket> allTickets = new ArrayList<>();
        for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture : messageRepliesFutures) {
            try {
                allTickets.addAll(messageReplyFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new NotificationException(e.getMessage());
            }
        }

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = client.zipMessagesTickets(expoPushMessages, allTickets);

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages = client.filterAllSuccessfulMessages(zippedMessagesTickets);
        String okTicketMessagesString = okTicketMessages.stream().map(p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId()).collect(Collectors.joining(","));
        logger.info("Recieved OK ticket for " + okTicketMessages.size() + " messages: " + okTicketMessagesString);

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages = client.filterAllMessagesWithError(zippedMessagesTickets);
        String errorTicketMessagesString = errorTicketMessages.stream().map(p -> "Title: " + p.message.getTitle() + ", Error: " + p.ticket.getDetails().getError()).collect(Collectors.joining(","));
        logger.error("Recieved ERROR ticket for " + errorTicketMessages.size() + " messages: " + errorTicketMessagesString);
    }
}
