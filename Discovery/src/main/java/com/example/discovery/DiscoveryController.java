package com.example.discovery;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/discovery")
public class DiscoveryController {

    // Map pour stocker les services enregistrés, avec leur nom et URL/port
    private final Map<String, ServiceInfo> services = new ConcurrentHashMap<>();

    // Classe interne pour contenir les informations d'un service
     static class ServiceInfo {
        String url;
        LocalDateTime lastRegisteredTime;

        ServiceInfo(String url) {
            this.url = url;
            this.lastRegisteredTime = LocalDateTime.now();
        }

        void refresh() {
            this.lastRegisteredTime = LocalDateTime.now();
        }
    }

    // Endpoint pour enregistrer un service
    @PostMapping("/register")
    public ResponseEntity<String> registerService(@RequestBody Map<String, String> serviceInfo) {
        String name = serviceInfo.get("name");
        String url = serviceInfo.get("url");

        // Enregistrer ou mettre à jour l'info du service
        services.put(name, new ServiceInfo(url));
        return ResponseEntity.ok("Service " + name + " registered at " + url);
    }

    // Endpoint pour récupérer la liste des services enregistrés
    @GetMapping("/services")
    public Map<String, String> getServices() {
        // Récupérer les services et renvoyer leur nom et URL
        Map<String, String> result = new ConcurrentHashMap<>();
        services.forEach((name, info) -> result.put(name, info.url));
        return result;
    }

    // Mécanisme de nettoyage pour supprimer les services qui ne se sont pas réenregistrés après 1 heure
    @Scheduled(fixedRate = 60000)  // Exécution toutes les minutes
    public void cleanUpOldServices() {
        LocalDateTime now = LocalDateTime.now();
        services.entrySet().removeIf(entry -> {
            // Si le service n'a pas été réenregistré dans les dernières 60 minutes (1 heure), il est supprimé
            return entry.getValue().lastRegisteredTime.isBefore(now.minusMinutes(60));

        });
    }





}
