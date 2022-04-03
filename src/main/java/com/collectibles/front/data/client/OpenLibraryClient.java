package com.collectibles.front.data.client;

import com.collectibles.front.data.domain.ResultBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OpenLibraryClient {

    @Value("${collectibles.app.openlibrary.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public List<ResultBookDto> getBooksByAuthor(String keyword) {
        ResultBookDto[] response = restTemplate.getForObject(
                endpoint + "/author/" + keyword,
                ResultBookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public List<ResultBookDto> getBooksByTitle(String keyword) {
        ResultBookDto[] response = restTemplate.getForObject(
                endpoint + "/title/" + keyword,
                ResultBookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }
}
