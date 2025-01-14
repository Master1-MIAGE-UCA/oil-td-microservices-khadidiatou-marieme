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
@SpringBootTest
public class PlayerControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private DiceClient diceFeignClient;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    public void testRollDice() throws Exception {
        // Simule la réponse du Feign Client
        when(diceFeignClient.rollDice()).thenReturn(Arrays.asList(4, 6));

        // Teste l'API /player/rollDice
        mockMvc.perform(MockMvcRequestBuilders.get("/player/rollDice"))
            .andExpect(MockMvcResultMatchers.status().isOk())  // Vérifie le statut HTTP 200
            .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(4))  // Vérifie la première valeur du résultat
            .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value(6));  // Vérifie la deuxième valeur du résultat
    }
}
