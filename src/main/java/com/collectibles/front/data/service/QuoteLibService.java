package com.collectibles.front.data.service;

import com.collectibles.front.data.client.QuoteLibClient;
import com.collectibles.front.data.domain.QuoteLibDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteLibService {

    private final QuoteLibClient quoteLibClient;

    public List<QuoteLibDto> fetchRandomQuote() {
        return quoteLibClient.getRandomQuote();
    }
}
