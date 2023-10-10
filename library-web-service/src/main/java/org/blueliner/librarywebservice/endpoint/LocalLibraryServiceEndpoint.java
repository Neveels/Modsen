package org.blueliner.librarywebservice.endpoint;

import lombok.RequiredArgsConstructor;
import org.blueliner.librarywebservice.dto.request.PutBookInTheStorageDto;
import org.blueliner.librarywebservice.dto.response.BookLogResponse;
import org.blueliner.librarywebservice.dto.response.GetFreeBookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LocalLibraryServiceEndpoint {
    @Value("${url.library}")
    private String LOCAL_LIBRARY_SERVICE_URL;
    private final RestTemplate restTemplate;

    public ResponseEntity<List<GetFreeBookResponse>> getIdsOfBusyBooks() {
        return restTemplate.exchange(
                LOCAL_LIBRARY_SERVICE_URL + "/free",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    }

    public ResponseEntity<BookLogResponse> changeBookStatus(Long id, PutBookInTheStorageDto putBookInTheStorageDto) {
        return restTemplate.exchange(
                LOCAL_LIBRARY_SERVICE_URL + "/status/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(putBookInTheStorageDto),
                BookLogResponse.class
        );
    }

}
