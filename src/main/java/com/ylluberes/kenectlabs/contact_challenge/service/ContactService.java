package com.ylluberes.kenectlabs.contact_challenge.service;

import com.ylluberes.kenectlabs.contact_challenge.provider.ContactProvider;
import com.ylluberes.kenectlabs.contact_challenge.provider.Provider;
import com.ylluberes.kenectlabs.contact_challenge.provider.ProviderFactory;
import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.input.ContactRequest;
import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.output.ContactResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    private final ProviderFactory providerFactory;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactService (ModelMapper modelMapper, ProviderFactory providerFactory) {
        this.modelMapper = modelMapper;
        this.providerFactory = providerFactory;
    }


    public ContactResponse getContacts (ContactRequest contactRequest, Provider provider) {
        if(provider.equals(Provider.KENECT_LABS)) {
            return getKenectLabsContacts(contactRequest, provider);
        }
        return null;
    }

    private ContactResponse getKenectLabsContacts (ContactRequest contactRequest, Provider provider) {
        ContactProvider contactProvider = getContactProvider(provider);
        ContactResponse response = (ContactResponse) contactProvider.getContacts(contactRequest);
        return response;
    }

    private ContactProvider getContactProvider (Provider provider) {
        return providerFactory.getContactProvider(provider);
    }
}
