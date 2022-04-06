package com.collectibles.front.data.client;

import com.collectibles.front.data.config.CollectiblesConfig;
import com.collectibles.front.data.domain.ResultBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OpenLibraryClient {

    private final CollectiblesConfig config;
    private final RestTemplate restTemplate;

    public List<ResultBookDto> getBooksByAuthor(String keyword) {
        ResultBookDto[] response = restTemplate.getForObject(
                config.getEndpoint() + config.getOpenLibraryPath() + "/author/" + keyword,
                ResultBookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public List<ResultBookDto> getBooksByTitle(String keyword) {
        ResultBookDto[] response = restTemplate.getForObject(
                config.getEndpoint() + config.getOpenLibraryPath() + "/title/" + keyword,
                ResultBookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }
}
