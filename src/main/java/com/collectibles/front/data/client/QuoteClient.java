package com.collectibles.front.data.client;

import com.collectibles.front.data.config.CollectiblesConfig;
import com.collectibles.front.data.domain.QuoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuoteClient {

    private final CollectiblesConfig config;
    private final RestTemplate restTemplate;

    public List<QuoteDto> getQuotes() {
        QuoteDto[] response = restTemplate.getForObject(
                config.getEndpoint() + config.getQuotesPath(),
                QuoteDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public QuoteDto getQuote(Long id) {
        QuoteDto response = restTemplate.getForObject(
                config.getEndpoint() + config.getQuotesPath() + "/" + id,
                QuoteDto.class
        );

        return Optional.ofNullable(response)
                .orElse(new QuoteDto());
    }

    public void deleteQuote(Long id) {
        restTemplate.delete(config.getEndpoint() + config.getQuotesPath() + "/" + id);
    }

    public void updateQuote(QuoteDto quoteDto) {
        restTemplate.put(
                config.getEndpoint() + config.getQuotesPath(),
                quoteDto
        );
    }

    public void createQuote(QuoteDto quoteDto) {
        restTemplate.postForObject(
                config.getEndpoint() + config.getQuotesPath(),
                quoteDto,
                QuoteDto.class
        );
    }
}
