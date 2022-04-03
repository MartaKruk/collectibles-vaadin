package com.collectibles.front.data.client;

import com.collectibles.front.data.domain.QuoteDto;
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
public class QuoteClient {

    @Value("${collectibles.app.quotes.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public List<QuoteDto> getQuotes() {
        QuoteDto[] response = restTemplate.getForObject(
                endpoint,
                QuoteDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public QuoteDto getQuote(Long id) {
        QuoteDto response = restTemplate.getForObject(
                endpoint + "/" + id,
                QuoteDto.class
        );

        return Optional.ofNullable(response)
                .orElse(new QuoteDto());
    }

    public void deleteQuote(Long id) {
        restTemplate.delete(endpoint + "/" + id);
    }

    public void updateQuote(QuoteDto quoteDto) {
        restTemplate.put(
                endpoint,
                quoteDto
        );
    }

    public void createQuote(QuoteDto quoteDto) {
        restTemplate.postForObject(
                endpoint,
                quoteDto,
                QuoteDto.class
        );
    }
}
