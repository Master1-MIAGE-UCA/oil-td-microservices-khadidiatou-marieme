package com.example.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerControllerEndToEndTest {

    @Mock
    private DiceClient diceClient;

    @InjectMocks
    private PlayerController playerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    public void testRollDiceEndToEnd() throws Exception {
        // Simule une réponse avec deux valeurs
        when(diceClient.rollDice()).thenReturn(Arrays.asList(4, 6));
        System.out.println("Mock response: " + diceClient.rollDice());  // Log pour vérifier la réponse simulée

        // Effectuer une requête GET sur /player/rollDice
        mockMvc.perform(MockMvcRequestBuilders.get("/player/rollDice"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[4, 6]"));  // Vérifie la réponse JSON
    }
}
