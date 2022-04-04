package com.collectibles.front.views.newcollection;

import com.collectibles.front.data.domain.BookDto;
import com.collectibles.front.data.domain.CollectionDto;
import com.collectibles.front.data.service.BookService;
import com.collectibles.front.data.service.CollectionService;
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

import java.util.List;

@PageTitle("Collections")
@Route(value = "collection", layout = MainLayout.class)
public class CollectionView extends VerticalLayout {

    private TextField id = new TextField("id");
    private TextField name = new TextField("Name");

    private Button addCollection = new Button("Add collection");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private FormLayout form = new FormLayout();
    private HorizontalLayout topLayout = new HorizontalLayout();
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private Grid<CollectionDto> grid = new Grid<>(CollectionDto.class);

    private final CollectionService collectionService;
    private final BookService bookService;

    public CollectionView(CollectionService collectionService, BookService bookService) {
        this.collectionService = collectionService;
        this.bookService = bookService;

        grid.setColumns("name");
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

        addCollection.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setVisible(true);
        });

        buttonsLayout.add(save, delete);
        form.add(name, buttonsLayout);
        topLayout.add(addCollection);
        mainLayout.add(grid, form);
        mainLayout.setSizeFull();
        add(topLayout, mainLayout);
        form.setVisible(false);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> setCollection(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(collectionService.fetchCollections());
    }

    public void save() {
        CollectionDto collectionDto;
        if (grid.asSingleSelect().isEmpty()) {
            collectionDto = new CollectionDto(name.getValue());
            collectionService.createCollection(collectionDto);
        } else {
            collectionDto = new CollectionDto(grid.asSingleSelect().getValue().getId(), name.getValue());
            collectionService.updateCollection(collectionDto);
        }

        refresh();
        setCollection(null);
    }

    public void delete(Long id) {
        if (id != 0) {
            List<BookDto> books = collectionService.fetchBooksInCollection(id);
            for (BookDto book : books) {
                collectionService.deleteBookFromCollection(id, book.getId());
            }
            collectionService.deleteCollection(id);
        }

        refresh();
        setCollection(null);
    }

    public void setCollection(CollectionDto collectionDto) {
        if (collectionDto == null) {
            id.clear();
            name.clear();
            form.setVisible(false);
        } else {
            id.setValue(String.valueOf(collectionDto.getId()));
            name.setValue(String.valueOf(collectionDto.getName()));
            form.setVisible(true);
            name.focus();
        }
    }
}
