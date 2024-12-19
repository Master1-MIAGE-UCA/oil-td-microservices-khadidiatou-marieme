package com.example.player;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PlayerController {
    DiceClient diceClient ;

    @Autowired
    public PlayerController(DiceClient diceClient) {
        this.diceClient = diceClient;
    }

    @GetMapping("/rollDice")
    public int rollDice() {
        return diceClient.rollDice();

    }

    @PostConstruct
    public void registerWithDiscovery() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> serviceInfo = new HashMap<>();
        serviceInfo.put("pseudo", "player");
        serviceInfo.put("url", "http://localhost:8082");
        restTemplate.postForEntity("http://localhost:8083/discovery/", serviceInfo, String.class);
    }
}
