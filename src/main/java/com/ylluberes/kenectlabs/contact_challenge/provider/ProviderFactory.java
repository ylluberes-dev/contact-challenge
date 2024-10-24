package com.ylluberes.kenectlabs.contact_challenge.provider;

import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.KenectLabContactProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProviderFactory {

    private final KenectLabContactProvider kenectLabContactProvider;

    @Autowired
    public ProviderFactory (KenectLabContactProvider kenectLabContactProvider) {
        this.kenectLabContactProvider = kenectLabContactProvider;
    }

    public ContactProvider getContactProvider (Provider provider) {
        if(Provider.KENECT_LABS.equals(provider)) {
            return kenectLabContactProvider;
        }
        throw new IllegalArgumentException("Provider not allowed");
    }
}
