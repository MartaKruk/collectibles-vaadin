package com.collectibles.front.views.quotes;

import com.collectibles.front.data.domain.QuoteDto;
import com.collectibles.front.data.service.QuoteService;
import com.collectibles.front.views.MainLayout;
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
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Quotes")
@Route(value = "quotes", layout = MainLayout.class)
public class QuotesView extends VerticalLayout {

    private TextField id = new TextField("id");
    private TextField content = new TextField("Content");
    private TextField author = new TextField("Author");

    private Button addQuote = new Button("Add quote");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private FormLayout form = new FormLayout();

    private HorizontalLayout topLayout = new HorizontalLayout();
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private Grid<QuoteDto> grid = new Grid<>(QuoteDto.class);

    private final QuoteService quoteService;

    @Autowired
    public QuotesView(QuoteService quoteService) {
        this.quoteService = quoteService;

        grid.setColumns("content", "author");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            save();
        });

        delete.addClickListener(e -> {
            delete(grid.asSingleSelect().getValue().getId());
            grid.asSingleSelect().clear();
        });

        addQuote.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setVisible(true);
        });

        buttonsLayout.add(save, delete);
        form.add(content, author, buttonsLayout);
        topLayout.add(addQuote);
        mainLayout.add(grid, form);
        mainLayout.setSizeFull();
        add(topLayout, mainLayout);
        form.setVisible(false);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> setQuote(grid.asSingleSelect().getValue()));

//        grid.setColumns("content", "author");
//        grid.setSizeFull();
//        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
//
//        QuoteForm form = new QuoteForm(this, quoteService);
//
//        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
//        mainContent.setSizeFull();
//
//        Button addQuoteButton = new Button("Add new quote");
//        addQuoteButton.addClickListener(e -> {
//            grid.asSingleSelect().clear();
//            form.setVisible(true);
//        });
//
//        add(addQuoteButton, mainContent);
//        form.setQuote(null);
//        setSizeFull();
//        refresh();
//
//        grid.asSingleSelect().addValueChangeListener(event -> form.setQuote(grid.asSingleSelect().getValue()));
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
