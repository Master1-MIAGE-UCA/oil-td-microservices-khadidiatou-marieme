package com.example.player;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {
    DiceClient diceClient ;

    @Autowired
    public PlayerController(DiceClient diceClient) {
        this.diceClient = diceClient;
    }

    @GetMapping("/rollDice")
    public List<Integer> rollDice() {
        return diceClient.rollDice();

    }

    @PostConstruct
    public void registerWithDiscovery() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> serviceInfo = new HashMap<>();
        serviceInfo.put("name", "player");
        serviceInfo.put("url", "http://localhost:8082");
        String discoveryServiceUrl = "http://localhost:8083/discovery/register";
        try {
            restTemplate.postForEntity(discoveryServiceUrl, serviceInfo, String.class);
            System.out.println("Service successfully registered with discovery.");
        } catch (Exception e) {
            System.err.println("Error registering with discovery service: " + e.getMessage());
        }
    }
}

