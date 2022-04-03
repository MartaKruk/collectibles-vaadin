package com.collectibles.front.data.service;

import com.collectibles.front.data.client.QuoteClient;
import com.collectibles.front.data.domain.QuoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteClient quoteClient;

    public List<QuoteDto> fetchQuotes() {
        return quoteClient.getQuotes();
    }

    public QuoteDto fetchQuote(Long id) {
        return quoteClient.getQuote(id);
    }

    public void deleteQuote(Long id) {
        quoteClient.deleteQuote(id);
    }

    public void updateQuote(QuoteDto quoteDto) {
        quoteClient.updateQuote(quoteDto);
    }

    public void createQuote(QuoteDto quoteDto) {
        quoteClient.createQuote(quoteDto);
    }
}
