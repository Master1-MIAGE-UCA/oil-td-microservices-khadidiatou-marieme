package com.example.player;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Dice", url = "http://localhost:8081")
public interface DiceClient {

    @GetMapping("/dice/rollDice")
    List<Integer> rollDice();

    @GetMapping ("dice/rollDices/{X}")
    int rollDices(@PathVariable("X") int X);

}
