package com.ylluberes.kenectlabs.contactchallenge.provider.kenectlabs.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactRequest {

    public static final String PAGE = "page";
    private int pageNumber;
}
