package com.collectibles.front.views;

import com.collectibles.front.data.client.OpenLibraryClient;
import com.collectibles.front.data.domain.ResultBookDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PageTitle("Search")
@Route(value = "search", layout = MainLayout.class)
public class SearchView extends VerticalLayout {

    private final TextField searchTextField = new TextField();
    private final RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    private final Grid<ResultBookDto> grid = new Grid<>();
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private final OpenLibraryClient openLibraryClient;

    public SearchView(OpenLibraryClient openLibraryClient) {
        this.openLibraryClient = openLibraryClient;

        searchTextField.setPlaceholder("Search by");
        searchTextField.setClearButtonVisible(true);
        searchTextField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchTextField.focus();

        radioGroup.setItems(TITLE, AUTHOR);
        radioGroup.setValue(TITLE);

        Button searchButton = new Button();
        searchButton.setText("Search");
        searchButton.addClickListener(e -> updateSearchResultBy(searchTextField.getValue(), radioGroup.getValue()));

        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.setPadding(true);
        searchLayout.add(searchTextField, radioGroup, searchButton);

        addClassName("search-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(this::createCard);
        add(searchLayout, grid);
    }

    private HorizontalLayout createCard(ResultBookDto resultBookDto) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        HorizontalLayout body = new HorizontalLayout();
        body.addClassName("body");
        body.setSpacing(false);
        body.getThemeList().add("spacing-s");

        Span title = new Span(resultBookDto.getTitle());
        title.addClassName("title");
        StringBuilder builder = new StringBuilder();
        for (String author : resultBookDto.getAuthors()) {
            builder.append(author);
            builder.append(", ");
        }

        Span author = new Span(builder.toString());
        author.addClassName("author");

        Span year = new Span(String.valueOf(resultBookDto.getYear()));
        author.addClassName("year");

        header.add(title);
        body.add(author, year);
        description.add(header, body);
        card.add(description);
        return card;
    }

    private void updateSearchResultBy(String keyword, String type) {
        List<ResultBookDto> books = new ArrayList<>();

        if (Objects.equals(type, AUTHOR)) {
            books = openLibraryClient.getBooksByAuthor(keyword);
        } else if (Objects.equals(type, TITLE)) {
            books = openLibraryClient.getBooksByTitle(keyword);
        }

        grid.setItems(Objects.requireNonNullElseGet(books, ArrayList::new));
    }

}
