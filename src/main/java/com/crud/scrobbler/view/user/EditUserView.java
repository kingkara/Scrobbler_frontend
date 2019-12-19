package com.crud.scrobbler.view.user;

import com.crud.scrobbler.domain.UserDto;
import com.crud.scrobbler.service.UsersService;
import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;

@Route(value = "editUser", layout = MainView.class)
@PageTitle("Editing User")
public class EditUserView extends VerticalLayout {
    private TextField username = new TextField();
    private TextField email = new TextField();

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private static final Logger LOGGER = LoggerFactory.getLogger(EditUserView.class);

    public EditUserView(@Autowired UsersService usersService) {
        VerticalLayout wrapper = new VerticalLayout();
        setSizeFull();
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        createTitle(wrapper);
        createFormLayout(wrapper);
        createButtonLayout(wrapper);

        Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
        binder.bindInstanceFields(this);
        binder.setBean(new UserDto());

        binder.forField(username).asRequired("User need to have a name").bind("username");

        binder.forField(email).withValidator(
                new EmailValidator("Wrong email typed")).bind("email");

        UserDto user = binder.getBean();
        cancel.addClickListener(e -> binder.readBean(null));
        save.addClickListener(e -> {
            binder.validate();
            if (binder.isValid()) {
                try {
                    UserDto userDto = usersService.getUser(123123);
                    userDto.setUsername(user.getUsername());
                    userDto.setEmail(user.getEmail());
                    Notification.show("Dane zmieniono pomyślnie");
                    usersService.updateUser(userDto);
                } catch (ResourceAccessException ex) {
                    LOGGER.warn("No user to edit data.");
                }
            } else {
                Notification.show("Popraw dane");
            }
        });

        add(wrapper);
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Edit user");
        wrapper.add(h1);
    }

    private void createFormLayout(VerticalLayout wrapper) {
        FormLayout formLayout = new FormLayout();
        setSizeFull();
        addFormItem(wrapper, formLayout, username, "User name");
        addFormItem(wrapper, formLayout, email, "Email");
    }

    private void addFormItem(VerticalLayout wrapper,
                             FormLayout formLayout, Component field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    public void createButtonLayout(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(cancel);
        buttonLayout.add(save);
        wrapper.add(buttonLayout);
    }
}
