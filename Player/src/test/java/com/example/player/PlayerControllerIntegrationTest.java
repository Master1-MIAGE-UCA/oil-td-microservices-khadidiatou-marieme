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
    public class PlayerControllerIntegrationTest {

        private MockMvc mockMvc;

        @Mock
        private DiceClient diceClient;

        @InjectMocks
        private PlayerController playerController;

        @BeforeEach
        public void setUp() {
            this.mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        }

        @Test
        public void testRollDice() throws Exception {
            // Simule une réponse de `DiceClient`
            when(diceClient.rollDice()).thenReturn(Arrays.asList(3, 5));

            mockMvc.perform(MockMvcRequestBuilders.get("/player/rollDice"))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Vérifie que le statut est 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())  // Vérifie que la réponse est un tableau
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(3))  // Vérifie que l'élément 0 est 3
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(5));  // Vérifie que l'élément 1 est 5
        }

    }
