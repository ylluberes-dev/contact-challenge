package com.ylluberes.kenectlabs.contact_challenge.service;

import com.ylluberes.kenectlabs.contact_challenge.controller.dto.ContactDTO;
import com.ylluberes.kenectlabs.contact_challenge.exception.NotSupportedProviderException;
import com.ylluberes.kenectlabs.contact_challenge.provider.Provider;
import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.input.ContactRequest;
import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.output.ContactResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AggregatorService {

    private final ContactService contactService;

    private final ModelMapper modelMapper;

    @Autowired
    public AggregatorService(ContactService contactService, ModelMapper modelMapper) {
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    public List<ContactDTO> getAggregatedContacts(String provider) throws NotSupportedProviderException {
        Provider contactProvider = Provider.fromValue(provider);
        if (contactProvider != null) {
            return aggregateContacts(contactProvider);
        }
        String errorMessage = String.format("Provider %s is not supported", provider);
        throw new NotSupportedProviderException(errorMessage);
    }


    private List<ContactDTO> aggregateContacts(Provider provider) {
        List<ContactDTO> contactList = new ArrayList<>();
        int pageNumber = 1;
        ContactResponse response;
        do {
            response = fetchContacts(pageNumber, provider);
            if (response == null || response.getContacts() == null || response.getContacts().isEmpty()) {
                break;
            }
            List<ContactDTO> contactDTOS = convertContactResponseToDTO(response, provider);
            contactList.addAll(contactDTOS);
            pageNumber++;
        } while (pageNumber <= response.getPaginationDetails().getTotalPage());

        return contactList;
    }

    private ContactResponse fetchContacts(int pageNumber, Provider provider) {
        ContactRequest contactRequest = ContactRequest.builder().pageNumber(pageNumber).build();
        ContactResponse response = contactService.getContacts(contactRequest, provider);
        return response;
    }

    private List<ContactDTO> convertContactResponseToDTO(ContactResponse contactResponse, Provider provider) {
        return contactResponse.getContacts()
                .stream()
                .map(contact -> {
                    ContactDTO contactDTO = modelMapper.map(contact, ContactDTO.class);
                    contactDTO.setSource(provider.name());
                    return contactDTO;
                }).collect(Collectors.toList());
    }
}
