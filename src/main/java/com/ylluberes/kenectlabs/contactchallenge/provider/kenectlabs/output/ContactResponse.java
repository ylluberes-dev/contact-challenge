package com.ylluberes.kenectlabs.contactchallenge.provider.kenectlabs.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ContactResponse {
    @JsonProperty("contacts")
    private List<Contact> contacts;
    private PaginationDetails paginationDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Contact {
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("email")
        private String email;
        @JsonProperty("created_at")
        private LocalDateTime createdAt;
        @JsonProperty("updated_at")
        private LocalDateTime updatedAt;

    }

    @Data
    @Builder
    public static class PaginationDetails {
        private int currentPage;
        private int pageItems;
        private int totalPage;
        private int totalCount;
    }
}
