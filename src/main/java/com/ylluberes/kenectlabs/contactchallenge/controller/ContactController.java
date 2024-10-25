package com.ylluberes.kenectlabs.contactchallenge.controller;

import com.ylluberes.kenectlabs.contactchallenge.controller.dto.ContactDTO;
import com.ylluberes.kenectlabs.contactchallenge.service.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final AggregatorService aggregatorService;

    @Autowired
    public ContactController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getContacts() {
        return ResponseEntity.ok(aggregatorService.getAggregatedContacts());
    }
}
