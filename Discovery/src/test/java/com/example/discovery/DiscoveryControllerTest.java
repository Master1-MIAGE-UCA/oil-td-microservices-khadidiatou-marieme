package com.example.discovery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class DiscoveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final Map<String, String> serviceInfo = new HashMap<>();

    @BeforeEach
    public void setUp() {
        serviceInfo.clear();
    }

    @Test
    public void testRegisterService() throws Exception {
        serviceInfo.put("name", "testService");
        serviceInfo.put("url", "http://localhost:8081");

        mockMvc.perform(post("/discovery/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testService\", \"url\":\"http://localhost:8081\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value("Service testService registered at http://localhost:8081"));
    }

    @Test
    public void testGetServices() throws Exception {
        // Enregistrement d'un service pour le test
        serviceInfo.put("name", "testService");
        serviceInfo.put("url", "http://localhost:8081");
        mockMvc.perform(post("/discovery/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"testService\", \"url\":\"http://localhost:8081\"}"))
            .andExpect(status().isOk());

        // Vérification de la récupération des services enregistrés
        mockMvc.perform(get("/discovery/services"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.testService").value("http://localhost:8081"));
    }

    @Test
    public void testCleanUpOldServices() throws Exception {
        // Enregistrement d'un service
        serviceInfo.put("name", "oldService");
        serviceInfo.put("url", "http://localhost:8082");
        mockMvc.perform(post("/discovery/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"oldService\", \"url\":\"http://localhost:8082\"}"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/discovery/services"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.oldService").value("http://localhost:8082"));


    }
}
