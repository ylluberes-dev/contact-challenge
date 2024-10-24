package com.ylluberes.kenectlabs.contact_challenge.controller.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ContactDTO {

    private int id;
    private String name;
    private String email;
    private String source;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
