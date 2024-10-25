package com.ylluberes.kenectlabs.contactchallenge.provider;

import com.ylluberes.kenectlabs.contactchallenge.exception.NotSupportedProviderException;
import com.ylluberes.kenectlabs.contactchallenge.provider.kenectlabs.KenectLabContactProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProviderFactory {

    private final KenectLabContactProvider kenectLabContactProvider;

    @Autowired
    public ProviderFactory(KenectLabContactProvider kenectLabContactProvider) {
        this.kenectLabContactProvider = kenectLabContactProvider;
    }

    public ContactProvider getContactProvider(Provider provider) throws NotSupportedProviderException {
        if (Provider.KENECT_LABS.equals(provider)) {
            return kenectLabContactProvider;
        }
        final String errorMessage = String.format("Provider %s is not allowed", provider.name());
        throw new NotSupportedProviderException(errorMessage);
    }
}
