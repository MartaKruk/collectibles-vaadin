package com.collectibles.front.views;

import com.collectibles.front.data.domain.BookDto;
import com.collectibles.front.data.domain.CollectionDto;
import com.collectibles.front.data.service.CollectionService;
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

    private final TextField id = new TextField("id");
    private final TextField name = new TextField("Name");
    private final FormLayout form = new FormLayout();
    private final Grid<CollectionDto> grid = new Grid<>(CollectionDto.class);
    private final CollectionService collectionService;

    public CollectionView(CollectionService collectionService) {
        this.collectionService = collectionService;

        grid.setColumns("name");
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

        Button addCollection = new Button("Add collection");
        addCollection.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setVisible(true);
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(save, delete);
        form.add(name, buttonsLayout);
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.add(addCollection);
        HorizontalLayout mainLayout = new HorizontalLayout();
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
        if (grid.asSingleSelect().isEmpty()) {
            collectionService.createCollection(new CollectionDto(name.getValue()));
        } else {
            collectionService.updateCollection(new CollectionDto(grid.asSingleSelect().getValue().getId(), name.getValue()));
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
