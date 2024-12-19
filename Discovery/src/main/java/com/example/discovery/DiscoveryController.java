package com.example.discovery;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/discovery")
public class DiscoveryController {

    private final Map<String, String> services = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public ResponseEntity<String> registerService(@RequestBody Map<String, String> serviceInfo) {
        String name = serviceInfo.get("name");
        String url = serviceInfo.get("url");
        services.put(name, url);
        return ResponseEntity.ok("Service " + name + " registered at " + url);
    }

    @GetMapping("/services")
    public Map<String, String> getServices() {
        return services;
    }




}
