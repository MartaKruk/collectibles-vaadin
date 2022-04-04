package com.collectibles.front.views;

import com.collectibles.front.data.domain.QuoteDto;
import com.collectibles.front.data.service.QuoteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Quotes")
@Route(value = "quotes", layout = MainLayout.class)
public class QuotesView extends VerticalLayout {

    private final TextField id = new TextField("id");
    private final TextField content = new TextField("Content");
    private final TextField author = new TextField("Author");
    private final FormLayout form = new FormLayout();
    private final Grid<QuoteDto> grid = new Grid<>(QuoteDto.class);
    private final QuoteService quoteService;

    public QuotesView(QuoteService quoteService) {
        this.quoteService = quoteService;

        grid.setColumns("content", "author");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        Button save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> save());

        Button delete = new Button("Delete");
        delete.addClickListener(e -> {
            delete(grid.asSingleSelect().getValue().getId());
            grid.asSingleSelect().clear();
        });

        Button addQuote = new Button("Add quote");
        addQuote.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setVisible(true);
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(save, delete);
        form.add(content, author, buttonsLayout);
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.add(addQuote);
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.add(grid, form);
        mainLayout.setSizeFull();
        add(topLayout, mainLayout);
        form.setVisible(false);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> setQuote(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(quoteService.fetchQuotes());
    }

    public void save() {
        if (grid.asSingleSelect().isEmpty()) {
            quoteService.createQuote(new QuoteDto(author.getValue(), content.getValue()));
        } else {
            quoteService.updateQuote(new QuoteDto(grid.asSingleSelect().getValue().getId(), author.getValue(), content.getValue()));
        }
        refresh();
        setQuote(null);
    }

    public void delete(Long id) {
        quoteService.deleteQuote(id);
        refresh();
        setQuote(null);
    }

    public void setQuote(QuoteDto quoteDto) {
        if (quoteDto == null) {
            id.clear();
            content.clear();
            author.clear();
            form.setVisible(false);
        } else {
            id.setValue(String.valueOf(quoteDto.getId()));
            content.setValue(quoteDto.getContent());
            author.setValue(quoteDto.getAuthor());
            form.setVisible(true);
            content.focus();
        }
    }
}
