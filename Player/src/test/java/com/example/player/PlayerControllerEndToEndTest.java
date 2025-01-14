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
    private DiceClient diceClient; // Simulation du DiceClient

    @InjectMocks
    private PlayerController playerController; // Injecte les mocks dans PlayerController

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Initialisation de MockMvc pour effectuer des requêtes HTTP
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    public void testRollDiceEndToEnd() throws Exception {
        // Simule la réponse du DiceClient
        when(diceClient.rollDice()).thenReturn(Arrays.asList(4, 6));

        // Effectuer une requête GET sur /player/rollDice
        mockMvc.perform(MockMvcRequestBuilders.get("/player/rollDice"))
            .andExpect(MockMvcResultMatchers.status().isOk()) // Vérifie que le statut HTTP est OK (200)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(4)) // Vérifie que le premier résultat est 4
            .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(6)); // Vérifie que le deuxième résultat est 6
    }
}
