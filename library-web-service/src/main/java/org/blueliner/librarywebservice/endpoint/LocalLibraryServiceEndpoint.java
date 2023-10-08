package org.blueliner.librarywebservice.endpoint;

import lombok.RequiredArgsConstructor;
import org.blueliner.librarywebservice.dto.GetFreeBookResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LocalLibraryServiceEndpoint {
    private final String LOCAL_LIBRARY_SERVICE_URL = "http://localhost:8888/storage";
    private final RestTemplate restTemplate;

    public ResponseEntity<List<GetFreeBookResponse>> getIdsOfBusyBooks() {
        return restTemplate.exchange(
                LOCAL_LIBRARY_SERVICE_URL + "/free",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

}
