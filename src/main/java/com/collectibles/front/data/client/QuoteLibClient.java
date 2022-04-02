package com.collectibles.front.data.client;

import com.collectibles.front.data.domain.QuoteLibDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuoteLibClient {

    private final RestTemplate restTemplate;

    public List<QuoteLibDto> getRandomQuote() {
        QuoteLibDto[] response = restTemplate.getForObject(
                "http://localhost:8080/v1/quote",
                QuoteLibDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }
}
