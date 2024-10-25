package com.ylluberes.kenectlabs.contactchallenge.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {

    private int id;
    private String name;
    private String email;
    private String source;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
