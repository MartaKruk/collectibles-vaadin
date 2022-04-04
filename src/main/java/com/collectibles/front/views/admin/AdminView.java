package com.collectibles.front.views.admin;

import com.collectibles.front.data.domain.UserDto;
import com.collectibles.front.data.service.UserService;
import com.collectibles.front.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
public class AdminView extends VerticalLayout {

    private Grid<UserDto> grid = new Grid<>(UserDto.class);
    private final UserService userService;

    @Autowired
    public AdminView(UserService userService) {
        this.userService = userService;

        grid.setColumns("name", "role");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        add(grid);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        grid.setItems(userService.fetchUsers());
    }

}
