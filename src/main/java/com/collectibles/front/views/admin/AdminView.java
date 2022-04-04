package com.collectibles.front.views.admin;

import com.collectibles.front.data.domain.UserDto;
import com.collectibles.front.data.service.UserService;
import com.collectibles.front.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
public class AdminView extends VerticalLayout {

    private TextField id = new TextField("id");
    private TextField name = new TextField("Name");
    private PasswordField password = new PasswordField("Password");
    private ComboBox<String> role = new ComboBox<>("Role");

    private Button addUser = new Button("Add user");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private FormLayout form = new FormLayout();
    private HorizontalLayout topLayout = new HorizontalLayout();
    private HorizontalLayout mainLayout = new HorizontalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();


    private Grid<UserDto> grid = new Grid<>(UserDto.class);
    private final UserService userService;

    @Autowired
    public AdminView(UserService userService) {
        this.userService = userService;

        grid.setColumns("name", "role");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        role.setItems("USER", "ADMIN");
        role.setValue("USER");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> {
            save();
        });

        delete.addClickListener(e -> {
            delete(grid.asSingleSelect().getValue().getId());
            grid.asSingleSelect().clear();
        });

        addUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setVisible(true);
        });

        buttonsLayout.add(save, delete);
        form.add(name, password, role, buttonsLayout);
        topLayout.add(addUser);
        mainLayout.add(grid, form);
        mainLayout.setSizeFull();
        add(topLayout, mainLayout);
        form.setVisible(false);
        setSizeFull();
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> setUser(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(userService.fetchUsers());
    }

    public void save() {
        if (grid.asSingleSelect().isEmpty()) {
            userService.createUser(new UserDto(name.getValue(), password.getValue(), role.getValue()));
        } else {
            userService.updateUser(new UserDto(grid.asSingleSelect().getValue().getId(), name.getValue(), password.getValue(), role.getValue()));
        }
        refresh();
        setUser(null);
    }

    public void delete(Long id) {
        if (id != 0) {
            userService.deleteUser(id);
        }
        refresh();
        setUser(null);
    }

    public void setUser(UserDto userDto) {
        if (userDto == null) {
            id.clear();
            name.clear();
            password.clear();
            role.clear();
            form.setVisible(false);
        } else {
            id.setValue(String.valueOf(userDto.getId()));
            name.setValue(userDto.getName());
            password.setValue(userDto.getPassword());
            role.setValue(userDto.getRole());
            form.setVisible(true);
            name.focus();
        }
    }
}
