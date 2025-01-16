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

    private final DiceClient diceClient;
    private final RestTemplate restTemplate;

    @Autowired
    public PlayerController(DiceClient diceClient, RestTemplate restTemplate) {
        this.diceClient = diceClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/rollDice")
    public List<Integer> rollDice() {
        List<Integer> result = diceClient.rollDice();
        System.out.println("Rolled Dice: " + result);
        return result;
    }


    @PostConstruct
    public void registerWithDiscovery() {
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
