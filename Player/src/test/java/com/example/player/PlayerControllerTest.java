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
import java.util.Map;
import static org.mockito.Mockito.*;

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

        when(restTemplate.postForEntity(eq(discoveryServiceUrl), eq(serviceInfo), eq(String.class)))
            .thenReturn(new ResponseEntity<>("Service registered", HttpStatus.OK));

        playerController.registerWithDiscovery();

        verify(restTemplate, times(1)).postForEntity(
            eq(discoveryServiceUrl),
            eq(serviceInfo),
            eq(String.class)
        );
    }

    @Test
    public void testRegisterWithDiscoveryWhenFailure() {
        when(restTemplate.postForEntity(eq("http://localhost:8083/discovery/register"),
            eq(Map.of("name", "player", "url", "http://localhost:8082")),
            eq(String.class)))
            .thenReturn(new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST));

        playerController.registerWithDiscovery();

        verify(restTemplate, times(1)).postForEntity(
            eq("http://localhost:8083/discovery/register"),
            eq(Map.of("name", "player", "url", "http://localhost:8082")),
            eq(String.class)
        );
    }
}
