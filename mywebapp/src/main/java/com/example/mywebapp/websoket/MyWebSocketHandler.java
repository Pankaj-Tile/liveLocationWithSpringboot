package com.example.mywebapp.websoket;

import com.example.mywebapp.model.VehicleLocation;
import com.example.mywebapp.service.VehicleLocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private VehicleLocationService vehicleLocationService;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        VehicleLocation location;

        try {
            if (payload.contains("removeLocation")) {
                String userId = objectMapper.readTree(payload).get("userId").asText();
                vehicleLocationService.deleteLocationByUserId(userId);
            } else if (payload.contains("fetchLocations")) {
                List<VehicleLocation> locations = vehicleLocationService.getAllLocations();
                String locationsJson = objectMapper.writeValueAsString(locations);
                session.sendMessage(new TextMessage(locationsJson));
            } else {
                location = objectMapper.readValue(payload, VehicleLocation.class);
                vehicleLocationService.saveOrUpdateLocation(location);

                for (WebSocketSession webSocketSession : sessions) {
                    if (webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(new TextMessage(payload));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}
