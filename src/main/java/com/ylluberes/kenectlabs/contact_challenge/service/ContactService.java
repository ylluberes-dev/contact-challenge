package com.ylluberes.kenectlabs.contact_challenge.service;

import com.ylluberes.kenectlabs.contact_challenge.provider.ContactProvider;
import com.ylluberes.kenectlabs.contact_challenge.provider.Provider;
import com.ylluberes.kenectlabs.contact_challenge.provider.ProviderFactory;
import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.input.ContactRequest;
import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.output.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ProviderFactory providerFactory;

    @Autowired
    public ContactService (ProviderFactory providerFactory) {
        this.providerFactory = providerFactory;
    }

    public ContactResponse getContacts (ContactRequest contactRequest, Provider provider) {
        ContactProvider contactProvider = getContactProvider(provider);
        if(contactProvider != null) {
            ContactResponse response = (ContactResponse) contactProvider.getContacts(contactRequest);
            return response;
        }
        return null;
    }

    private ContactProvider getContactProvider (Provider provider) {
        return providerFactory.getContactProvider(provider);
    }
}
