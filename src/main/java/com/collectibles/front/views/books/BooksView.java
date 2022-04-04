package com.collectibles.front.views.books;

import com.collectibles.front.data.domain.BookDto;
import com.collectibles.front.data.domain.CollectionDto;
import com.collectibles.front.data.service.BookService;
import com.collectibles.front.data.service.CollectionService;
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

import java.util.List;

@PageTitle("Books")
@Route(value = "books", layout = MainLayout.class)
public class BooksView extends VerticalLayout {

    private TextField id = new TextField("id");
    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");
    private TextField year = new TextField("Year");
    private TextField note = new TextField("Note");
    private ComboBox<CollectionDto> collection = new ComboBox<>("Collection");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Tabs tabs = new Tabs();
    private Grid<BookDto> grid = new Grid<>(BookDto.class);
    private FormLayout form = new FormLayout();
    private HorizontalLayout topLayout = new HorizontalLayout();
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private final BookService bookService;
    private final CollectionService collectionService;

    @Autowired
    public BooksView(BookService bookService, CollectionService collectionService) {
        this.bookService = bookService;
        this.collectionService = collectionService;

        mainLayout.setSizeFull();

        Tab allBooksTab = new Tab("All books");
        tabs.add(allBooksTab);

        List<CollectionDto> collections = collectionService.fetchCollections();
        for (CollectionDto collectionDto : collections) {
            Tab tab = new Tab(collectionDto.getName());
            tab.setId(String.valueOf(collectionDto.getId()));
            tabs.add(tab);
        }

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addSelectedChangeListener(e -> refresh());

        collection.setItems(collectionService.fetchCollections());
        collection.setItemLabelGenerator(CollectionDto::getName);
        collection.setRequired(true);
        collection.setErrorMessage("This field is required");

        grid.setColumns("title", "author", "year", "note");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            grid.asSingleSelect().clear();
            save();
        });

        delete.addClickListener(e -> {
            delete(grid.asSingleSelect().getValue().getId());
            grid.asSingleSelect().clear();
        });

        buttonsLayout.add(save, delete);
        form.add(title, author, year, note, collection, buttonsLayout);
        form.setVisible(false);

        Button addBook = new Button("Add book");
        addBook.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setVisible(true);
        });
        Button browseLibrary = new Button("Browse library");
        browseLibrary.addClickListener(e -> {
            UI.getCurrent().navigate("search");
        });

        topLayout.add(addBook, browseLibrary);
        mainLayout.add(tabs, grid, form);
        add(topLayout, mainLayout);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> setBook(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        if (tabs.getSelectedTab().getId().isEmpty()) {
            grid.setItems(bookService.fetchBooks());
        } else {
            grid.setItems(collectionService.fetchBooksInCollection(Long.parseLong(tabs.getSelectedTab().getId().get())));
        }
    }

    public void save() {
        if (grid.asSingleSelect().isEmpty()) {
            collectionService.addBookToCollection(collection.getValue().getId(), new BookDto(title.getValue(), author.getValue(), year.getValue(), note.getValue()));
        } else {
            bookService.updateBook(new BookDto(grid.asSingleSelect().getValue().getId(), title.getValue(), author.getValue(), year.getValue(), note.getValue()));
        }
        refresh();
        setBook(null);
    }

    public void delete(Long id)  {
        if (id != 0) {
            bookService.deleteBook(id);
        }
        refresh();
        setBook(null);
    }

    public void setBook(BookDto bookDto) {
        if (bookDto == null) {
            id.clear();
            title.clear();
            author.clear();
            year.clear();
            note.clear();
            collection.clear();
            form.setVisible(false);
        } else {
            id.setValue(String.valueOf(bookDto.getId()));
            title.setValue(bookDto.getTitle());
            author.setValue(bookDto.getAuthor());
            year.setValue(bookDto.getYear());
            note.setValue(bookDto.getNote());
            form.setVisible(true);
            title.focus();
        }
    }
}
