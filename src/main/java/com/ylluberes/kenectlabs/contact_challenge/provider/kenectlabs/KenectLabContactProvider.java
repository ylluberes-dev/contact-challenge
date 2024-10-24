package com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs;

import com.ylluberes.kenectlabs.contact_challenge.exception.ProviderException;
import com.ylluberes.kenectlabs.contact_challenge.provider.ContactProvider;
import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.input.ContactRequest;
import com.ylluberes.kenectlabs.contact_challenge.provider.kenectlabs.output.ContactResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KenectLabContactProvider implements ContactProvider<ContactResponse, ContactRequest> {
    private static final Logger logger = LoggerFactory.getLogger(KenectLabContactProvider.class);
    private static final String GET_CONTACT_ENDPOINT = "/api/v1/contacts";
    private final RestClient restClient;

    public KenectLabContactProvider(@Qualifier("kenectLabsRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ContactResponse getContacts(ContactRequest contactRequest) {
        logger.info("Trying to fetch all kenectLabs contacts");
        ResponseEntity<ContactResponse> contactResponse = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_CONTACT_ENDPOINT)
                        .queryParam("page", contactRequest.getPageNumber()).build())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    final String errorMessage =
                            String.format("kenectLabs api returned server error response, errorCode  %s", response.getStatusCode().value());
                    logger.error(errorMessage);
                    throw new ProviderException(errorMessage);
                }).toEntity(ContactResponse.class);

        if (contactResponse.getStatusCode() == HttpStatusCode.valueOf(200)) {
            ContactResponse response = contactResponse.getBody();
            response.setPaginationDetails(extractPaginationDetails(contactResponse));
            return response;
        }
        return null;
    }


    private ContactResponse.PaginationDetails extractPaginationDetails(ResponseEntity<ContactResponse> response) {
        final HttpHeaders headers = response.getHeaders();
        int currentPage = 0, pageItems = 0, totalPages = 0, totalCount = 0;
        if (headers != null) {
             currentPage = Integer.parseInt(headers.getFirst("Current-Page"));
             pageItems = Integer.parseInt(headers.getFirst("Page-Items"));
             totalPages = Integer.parseInt(headers.getFirst("Total-Pages"));
             totalCount = Integer.parseInt(headers.getFirst("Total-Count"));
        }
        ContactResponse.PaginationDetails paginationDetails =
                ContactResponse.PaginationDetails.builder()
                        .currentPage(currentPage)
                        .totalPage(totalPages)
                        .totalCount(totalCount)
                        .pageItems(pageItems)
                        .build();

        return paginationDetails;
    }
}

