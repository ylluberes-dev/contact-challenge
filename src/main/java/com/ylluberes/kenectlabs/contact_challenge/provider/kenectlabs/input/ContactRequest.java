package com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.input;

import com.ylluberes.kenectlabs.contact_challenge.provider.Provider;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactRequest {

    public static final String PAGE = "page";
    private int pageNumber;
}
