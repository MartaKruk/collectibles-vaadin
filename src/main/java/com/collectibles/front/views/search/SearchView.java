package com.collectibles.front.views.search;

import com.collectibles.front.data.client.OpenLibraryClient;
import com.collectibles.front.data.domain.ResultBookDto;
import com.collectibles.front.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
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

    Grid<ResultBookDto> grid = new Grid<>();

    public SearchView(OpenLibraryClient openLibraryClient) {
        TextField searchTextField = new TextField();
        searchTextField.setPlaceholder("Search by");
        searchTextField.setClearButtonVisible(true);
        searchTextField.focus();

        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setItems(SearchType.TITLE.getText(), SearchType.AUTHOR.getText());
        radioGroup.setValue(SearchType.TITLE.getText());

        Button searchButton = new Button();
        searchButton.setText("Search");
        searchButton.addClickListener(e -> updateSearchResultBy(searchTextField.getValue(), openLibraryClient, radioGroup.getValue()));

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

        Span name = new Span(resultBookDto.getTitle());
        name.addClassName("name");
        StringBuilder builder = new StringBuilder();
        for (String author : resultBookDto.getAuthors()) {
            builder.append(author);
            builder.append(", ");
        }

        Span author = new Span(builder.deleteCharAt(builder.lastIndexOf(", ")).toString());
        author.addClassName("author");
        header.add(name, author);

        Button addButton = new Button("Add to collection", new Icon(VaadinIcon.PLUS));

        description.add(header, addButton);
        card.add(description);
        return card;
    }

    private void updateSearchResultBy(String keyword, OpenLibraryClient openLibraryClient, String type) {
        List<ResultBookDto> books = new ArrayList<>();

        if (Objects.equals(type, SearchType.AUTHOR.getText())) {
            books = openLibraryClient.getBooksByAuthor(keyword);
        } else if (Objects.equals(type, SearchType.TITLE.getText())) {
            books = openLibraryClient.getBooksByTitle(keyword);
        }

        grid.setItems(Objects.requireNonNullElseGet(books, ArrayList::new));
    }

}
