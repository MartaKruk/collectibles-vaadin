package com.collectibles.front.data.client;

import com.collectibles.front.data.domain.ResultBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OpenLibraryClient {

    private final RestTemplate restTemplate;

    public List<ResultBookDto> getBooksByAuthor(String keyword) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/search/author/" + keyword).build().encode().toUri();
        ResultBookDto[] response = restTemplate.getForObject(
                url,
                ResultBookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public List<ResultBookDto> getBooksByTitle(String keyword) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/search/title/" + keyword).build().encode().toUri();
        ResultBookDto[] response = restTemplate.getForObject(
                url,
                ResultBookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }
}
