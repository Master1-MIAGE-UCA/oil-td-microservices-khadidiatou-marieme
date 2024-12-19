package com.example.player;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Dice", url = "http://localhost:8081")
public interface DiceClient {
    @GetMapping("/rollDice")
    int rollDice();

    @GetMapping ("/rollDices/{X}")
    int rollDices(@PathVariable int X);

}
