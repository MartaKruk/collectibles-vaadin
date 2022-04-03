package com.collectibles.front.views.quotes;

import com.collectibles.front.data.domain.QuoteDto;
import com.collectibles.front.data.service.QuoteService;
import com.collectibles.front.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Quotes")
@Route(value = "quotes", layout = MainLayout.class)
public class QuotesView extends VerticalLayout {

    private Grid<QuoteDto> grid = new Grid<>(QuoteDto.class);
    private QuoteService quoteService;

    @Autowired
    public QuotesView(QuoteService quoteService) {
        this.quoteService = quoteService;

        grid.setColumns("content", "author");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        QuoteForm form = new QuoteForm(this, quoteService);

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();

        Button addQuoteButton = new Button("Add new quote");
        addQuoteButton.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setVisible(true);
        });

        add(addQuoteButton, mainContent);
        form.setQuote(null);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> form.setQuote(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(quoteService.fetchQuotes());
    }

}
