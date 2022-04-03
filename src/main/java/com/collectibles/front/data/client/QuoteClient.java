package com.collectibles.front.data.client;

import com.collectibles.front.data.domain.QuoteDto;
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
public class QuoteClient {

    private final RestTemplate restTemplate;

    public List<QuoteDto> getQuotes() {
        QuoteDto[] response = restTemplate.getForObject(
                "http://localhost:8080/v1/quotes",
                QuoteDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public QuoteDto getQuote(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/quotes/" + id).build().encode().toUri();

        QuoteDto response = restTemplate.getForObject(
                url,
                QuoteDto.class
        );

        return Optional.ofNullable(response)
                .orElse(new QuoteDto());
    }

    public void deleteQuote(Long id) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/quotes/" + id).build().encode().toUri();

        restTemplate.delete(url);
    }

    public void updateQuote(QuoteDto quoteDto) {
        restTemplate.put(
                "http://localhost:8080/v1/quotes",
                quoteDto
        );
    }

    public void createQuote(QuoteDto quoteDto) {
        restTemplate.postForObject(
                "http://localhost:8080/v1/quotes",
                quoteDto,
                QuoteDto.class
        );
    }
}
