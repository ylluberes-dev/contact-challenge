package com.ylluberes.kenectlabs.contactchallenge.service;

import com.ylluberes.kenectlabs.contactchallenge.provider.ContactProvider;
import com.ylluberes.kenectlabs.contactchallenge.provider.Provider;
import com.ylluberes.kenectlabs.contactchallenge.provider.ProviderFactory;
import com.ylluberes.kenectlabs.contactchallenge.provider.kenectlabs.output.ContactResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    @Mock
    private ProviderFactory providerFactory;

    @Mock
    private ContactProvider contactProvider;

    @InjectMocks
    private ContactService contactService;


    @Test
    public void testGetContacts() {
        Mockito.when(providerFactory.getContactProvider(Mockito.any())).thenReturn(contactProvider);
        Mockito.when(contactProvider.getContacts(Mockito.any())).thenReturn(getContactResponse());
        ContactResponse response = contactService.getContacts(Mockito.any(), Provider.KENECT_LABS);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getContacts()).isNotEmpty();
        Assertions.assertThat(response.getContacts().size()).isEqualTo(1);
        Assertions.assertThat(response.getContacts().get(0).getName()).isEqualTo("J Anderson");
    }

    @Test
    public void testGetContactsNullProvider() {
        Mockito.when(providerFactory.getContactProvider(Mockito.any())).thenReturn(null);
        ContactResponse response = contactService.getContacts(Mockito.any(), Provider.KENECT_LABS);
        Assertions.assertThat(response).isNull();
    }


    private ContactResponse getContactResponse() {
        List<ContactResponse.Contact> dummyContacts = getDummyContacts();
        return ContactResponse.builder().contacts(dummyContacts).build();
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
}
