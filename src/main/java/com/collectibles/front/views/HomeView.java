package com.collectibles.front.views;

import com.collectibles.front.data.domain.QuoteLibDto;
import com.collectibles.front.data.service.QuoteLibService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.List;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private final QuoteLibService quoteLibService;

    public HomeView(QuoteLibService quoteLibService) {
        this.quoteLibService = quoteLibService;

        setSpacing(true);

        add(new H2("Welcome to Collectibles!"));
        add(new Paragraph("Here you can store your favourite books and quotes and browse an open library"));

        List<QuoteLibDto> quotes = quoteLibService.fetchRandomQuote();

        for (QuoteLibDto quote : quotes) {
            add(new Paragraph("\"" + quote.getQuote_text() + "\""));
            add(new Paragraph("- " + quote.getAuthor()));
        }

        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
