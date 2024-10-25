package com.ylluberes.kenectlabs.contact_challenge.controller;

import com.ylluberes.kenectlabs.contact_challenge.controller.dto.ContactDTO;
import com.ylluberes.kenectlabs.contact_challenge.exception.NotSupportedProviderException;
import com.ylluberes.kenectlabs.contact_challenge.provider.Provider;
import com.ylluberes.kenectlabs.contact_challenge.service.AggregatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AggregatorService aggregatorService;

    @Test
    public void testGetContacts() throws Exception {
        when(aggregatorService.getAggregatedContacts("KENECT_LABS")).thenReturn(getDummyContacts());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contacts/KENECT_LABS").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Yasser Lluberes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("ylluberes19@gmail.com"));
    }

    @Test
    public void testGetContactsNotValidProvider () throws Exception {
        doThrow(NotSupportedProviderException.class).when(aggregatorService).getAggregatedContacts("CLARO");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contacts/CLARO").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private List<ContactDTO> getDummyContacts() {
        return List.of(ContactDTO.builder()
                .id(10).email("ylluberes19@gmail.com")
                .name("Yasser Lluberes")
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now()).build());
    }
}
