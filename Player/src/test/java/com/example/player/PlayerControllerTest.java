package com.example.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {

    @Mock
    private DiceClient diceClient;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PlayerController playerController;

    @Test
    public void testRegisterWithDiscovery() {
        // Préparer les données
        Map<String, String> serviceInfo = new HashMap<>();
        serviceInfo.put("name", "player");
        serviceInfo.put("url", "http://localhost:8082");

        String discoveryServiceUrl = "http://localhost:8083/discovery/register";

        // Simuler le comportement de RestTemplate
        when(restTemplate.postForEntity(
            eq(discoveryServiceUrl),
            eq(serviceInfo),
            eq(String.class)
        )).thenReturn(new ResponseEntity<>("Service registered", HttpStatus.OK));

        // Appeler la méthode
        playerController.registerWithDiscovery();

        // Vérifier que le RestTemplate a été appelé correctement
        verify(restTemplate, times(1)).postForEntity(
            eq(discoveryServiceUrl),
            eq(serviceInfo),
            eq(String.class)
        );
    }
}
