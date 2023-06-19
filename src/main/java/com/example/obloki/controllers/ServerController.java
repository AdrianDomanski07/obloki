package com.example.obloki.controllers;

import com.example.obloki.services.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {


    @GetMapping("/")
    public String getInfo() {
        ClientService clientService = new ClientService();
        return "IP Address: " + clientService.getIpAddress() + "<br>" + clientService.getClientTime();

    }

    @GetMapping("/health")
    public String checkHealth() {
        return "OK";
    }
}
