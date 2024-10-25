package com.ylluberes.kenectlabs.contactchallenge.controller;

import com.ylluberes.kenectlabs.contactchallenge.controller.dto.ContactDTO;
import com.ylluberes.kenectlabs.contactchallenge.exception.NotSupportedProviderException;
import com.ylluberes.kenectlabs.contactchallenge.service.AggregatorService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class ContactControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AggregatorService aggregatorService;

    @Test
    public void testGetContacts() throws Exception {
        when(aggregatorService.getAggregatedContacts("KENECT_LABS")).thenReturn(getDummyContacts());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contacts/KENECT_LABS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("API-Key", "146")
                        .header("API-Secret", "acef"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetContactsNotValidProvider() throws Exception {
        doThrow(NotSupportedProviderException.class).when(aggregatorService).getAggregatedContacts("CLARO");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contacts/CLARO")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("API-Key", "123")
                        .header("API-Secret", "adef"))
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
