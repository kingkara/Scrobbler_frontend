package com.crud.scrobbler.view.user;

import com.crud.scrobbler.domain.UserDto;
import com.crud.scrobbler.service.UsersService;
import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "deleteUser", layout = MainView.class)
@PageTitle("Delete User")
public class DeleteUserView extends VerticalLayout {

    public DeleteUserView(@Autowired UsersService usersService) {
        UserDto userDto = usersService.getUser(123123);

        Label title = new Label("Are you sure you want to delete your user?");
        title.getStyle().set("font-size", "30px")
                .set("font-weight", "600");

        Button delete = new Button("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        delete.addClickListener(e -> usersService.deleteUser(userDto.getId()));
        add(title, delete);
    }
}
