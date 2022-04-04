package com.collectibles.front.views.books;

import com.collectibles.front.data.domain.BookDto;
import com.collectibles.front.data.domain.CollectionDto;
import com.collectibles.front.data.service.BookService;
import com.collectibles.front.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Books")
@Route(value = "books", layout = MainLayout.class)
public class BooksView extends VerticalLayout {

    private TextField id = new TextField("id");
    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");
    private TextField year = new TextField("Year");
    private TextField note = new TextField("Note");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Grid<BookDto> grid = new Grid<>(BookDto.class);
    private FormLayout bookForm = new FormLayout();
    private HorizontalLayout topLayout = new HorizontalLayout();
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private final BookService bookService;

    @Autowired
    public BooksView(BookService bookService) {
        this.bookService = bookService;

        mainLayout.setSizeFull();

        Tab allBooksTab = new Tab("All books");
        Tab collectionTab = new Tab("Collection");
        Tabs tabs = new Tabs(allBooksTab, collectionTab);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        ComboBox<CollectionDto> comboBox = new ComboBox<>("Collection");

        grid.setColumns("title", "author", "year", "note");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            grid.asSingleSelect().clear();
            save();
        });

        delete.addClickListener(e -> {
            grid.asSingleSelect().clear();
            delete();
        });

        buttonsLayout.add(save, delete);
        bookForm.add(title, author, year, note, buttonsLayout);
        bookForm.setVisible(false);

        Button addBook = new Button("Add book");
        addBook.addClickListener(e -> {
            grid.asSingleSelect().clear();
            bookForm.setVisible(true);
        });
        Button browseLibrary = new Button("Browse library");
        browseLibrary.addClickListener(e -> {
            UI.getCurrent().navigate("search");
        });

        topLayout.add(addBook, browseLibrary);
        mainLayout.add(tabs, grid, bookForm);
        add(topLayout, mainLayout);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        grid.setItems(bookService.fetchBooks());
    }

    public void updateGrid() {

    }

    public void save() {
        bookForm.setVisible(false);
    }

    public void delete() {
        bookForm.setVisible(false);
    }

    public void setBook(BookDto bookDto) {

    }
}
