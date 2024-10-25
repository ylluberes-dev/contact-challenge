package com.ylluberes.kenectlabs.contact_challenge.controller;

import com.ylluberes.kenectlabs.contact_challenge.controller.dto.ContactDTO;
import com.ylluberes.kenectlabs.contact_challenge.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ContactController {

    private final AggregatorService aggregatorService;

    @Autowired
    public ContactController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/contacts/{provider}")
    public ResponseEntity<List<ContactDTO>> getContacts(@PathVariable String provider) {
        return ResponseEntity.ok(aggregatorService.getAggregatedContacts(provider));
    }
}
