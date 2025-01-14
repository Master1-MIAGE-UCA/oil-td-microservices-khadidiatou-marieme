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
    void testRollDice() {
        List<Integer> expectedResult = List.of(1, 2, 3, 4, 5);
        when(diceClient.rollDice()).thenReturn(expectedResult);

        List<Integer> result = playerController.rollDice();
        assertEquals(expectedResult, result);
        verify(diceClient, times(1)).rollDice();
    }

    @Test
    void testRegisterWithDiscovery() {
        // Simulation de la réponse de postForEntity
        String response = "Service successfully registered with discovery.";
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
            .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        // Appel de la méthode d'enregistrement
        playerController.registerWithDiscovery();

        // Vérification que la méthode postForEntity a été appelée correctement
        verify(restTemplate, times(1))
            .postForEntity(eq("http://localhost:8083/discovery/register"), any(HashMap.class), eq(String.class));
    }
}
