package com.collectibles.front.views.quotes;

import com.collectibles.front.data.domain.QuoteDto;
import com.collectibles.front.data.service.QuoteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class QuoteForm extends FormLayout {

    private TextField id = new TextField("id");
    private TextField content = new TextField("Content");
    private TextField author = new TextField("Author");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private QuotesView quotesView;

    public QuoteForm(QuotesView quotesView, QuoteService quoteService) {
        this.quotesView = quotesView;

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(content, author, buttons);

        save.addClickListener(event -> save(quoteService));
        delete.addClickListener(event -> delete(quoteService));
    }

    private void save(QuoteService quoteService) {
        QuoteDto quoteDto;
        if (id.getValue().isEmpty()) {
            quoteDto = new QuoteDto(author.getValue(), content.getValue());
            quoteService.createQuote(quoteDto);
        } else {
            quoteDto = new QuoteDto(Long.parseLong(id.getValue()), author.getValue(), content.getValue());
            quoteService.updateQuote(quoteDto);
        }

        quotesView.refresh();
        setQuote(null);
    }

    private void delete(QuoteService quoteService) {
        if (id.getValue() != null) {
            quoteService.deleteQuote(Long.parseLong(id.getValue()));
        } else {
            Notification.show("Nothing to delete");
        }

        quotesView.refresh();
        setQuote(null);
    }

    public void setQuote(QuoteDto quoteDto) {
        if (quoteDto == null) {
            setVisible(false);
        } else {
            id.setValue(String.valueOf(quoteDto.getId()));
            content.setValue(String.valueOf(quoteDto.getContent()));
            author.setValue(String.valueOf(quoteDto.getAuthor()));
            setVisible(true);
            content.focus();
        }
    }
}
