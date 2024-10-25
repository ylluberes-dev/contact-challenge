package com.ylluberes.kenectlabs.contactchallenge.service;

import com.ylluberes.kenectlabs.contactchallenge.controller.dto.ContactDTO;
import com.ylluberes.kenectlabs.contactchallenge.exception.NotSupportedProviderException;
import com.ylluberes.kenectlabs.contactchallenge.provider.Provider;
import com.ylluberes.kenectlabs.contactchallenge.provider.kenectlabs.input.ContactRequest;
import com.ylluberes.kenectlabs.contactchallenge.provider.kenectlabs.output.ContactResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AggregatorServiceTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private AggregatorService aggregatorService;

    @Test
    public void testAggregateContacts() {
        when(contactService.getContacts(ContactRequest.builder().pageNumber(1).build(), Provider.KENECT_LABS)).thenReturn(getContactResponse());
        when(contactService.getContacts(ContactRequest.builder().pageNumber(2).build(), Provider.KENECT_LABS)).thenReturn(getContactResponse());
        List<ContactDTO> contactDTOList = aggregatorService.getAggregatedContacts();
        Assertions.assertThat(contactDTOList).isNotNull();
        Assertions.assertThat(contactDTOList).isNotEmpty();
        Assertions.assertThat(contactDTOList.get(0).getName()).isEqualTo("J Anderson");
        Assertions.assertThat(contactDTOList.get(0).getSource()).isEqualTo("KENECT_LABS");
    }

    @Test
    public void testAggregateContactsHundredPages() {
        final int totalPages = 100;
        for (int pageNumber = 1; pageNumber <= totalPages; pageNumber++) {
            when(contactService.getContacts(ContactRequest.builder().pageNumber(pageNumber).build(), Provider.KENECT_LABS)).thenReturn(getContactResponseHundredPages());
        }
        List<ContactDTO> contactDTOList = aggregatorService.getAggregatedContacts();
        verify(contactService, times(100)).getContacts(Mockito.any(ContactRequest.class), Mockito.any(Provider.class));
        verify(modelMapper, times(100)).map(Mockito.any(), Mockito.any());
        Assertions.assertThat(contactDTOList).isNotNull();
    }


    private ContactResponse getContactResponse() {
        List<ContactResponse.Contact> dummyContacts = getDummyContacts();
        return ContactResponse.builder()
                .contacts(dummyContacts)
                .paginationDetails(getDummyPaginationDetails(20, 1, 25, 2)).build();
    }

    private ContactResponse getContactResponseHundredPages() {
        List<ContactResponse.Contact> dummyContacts = getDummyContacts();
        return ContactResponse.builder()
                .contacts(dummyContacts)
                .paginationDetails(getDummyPaginationDetails(20, 1, 25, 100)).build();

    }

    private List<ContactResponse.Contact> getDummyContacts() {
        ContactResponse.Contact contact = ContactResponse.Contact.builder()
                .id(44)
                .name("J Anderson")
                .email("janderson@kenect.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        return List.of(contact);
    }

    private ContactResponse.PaginationDetails getDummyPaginationDetails(int pageItems, int currentPage, int totalCount, int totalPage) {
        return ContactResponse.PaginationDetails.builder()
                .pageItems(pageItems)
                .currentPage(currentPage)
                .totalCount(totalCount)
                .totalPage(totalPage).build();
    }

}
