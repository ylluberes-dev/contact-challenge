package com.ylluberes.kenectlabs.contact_challenge.service;

import com.ylluberes.kenectlabs.contact_challenge.controller.dto.ContactDTO;
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

    private List<ContactDTO> aggregatedList = new ArrayList<>();

    @Autowired
    public AggregatorService (ContactService contactService, ModelMapper modelMapper) {
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    public List<ContactDTO> getContacts () {
        aggregateContacts(Provider.KENECT_LABS);
        return aggregatedList;
    }


    public void aggregateContacts (Provider provider) {
        clearContactList();
        ContactResponse response = fetchContacts(1, provider);
        List<ContactDTO> contactDTOS = convertContactResponseToDTO(response,provider);
        aggregateContacts(contactDTOS);

        ContactResponse.PaginationDetails paginationDetails = response.getPaginationDetails();
        for (int page = 2; page <= paginationDetails.getTotalPage(); page++) {
            ContactResponse contactResponse = fetchContacts(page, provider);
            contactDTOS = convertContactResponseToDTO(contactResponse,provider);
            aggregateContacts(contactDTOS);
        }
    }

    private ContactResponse fetchContacts (int pageNumber, Provider provider) {
        ContactRequest contactRequest = ContactRequest.builder().pageNumber(pageNumber).build();
        ContactResponse response = contactService.getContacts(contactRequest,provider);
        return response;
    }

    private void aggregateContacts (List<ContactDTO> contacts) {
        aggregatedList.addAll(contacts);
    }

    private List<ContactDTO> convertContactResponseToDTO (ContactResponse contactResponse, Provider provider) {
        return contactResponse.getContacts()
                .stream()
                .map(contact -> {
                    ContactDTO contactDTO = modelMapper.map(contact, ContactDTO.class);
                    contactDTO.setSource(provider.name());
                    return contactDTO;
                }).collect(Collectors.toList());
    }

    private void clearContactList () {
        if(!aggregatedList.isEmpty()) {
            aggregatedList.clear();
        }
    }
}
