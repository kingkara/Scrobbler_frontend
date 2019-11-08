package com.crud.scrobbler.view.usersLibrary;

import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route(value = "usersLibrary", layout = MainView.class)
public class UsersLibraryView extends Div {
    private Tabs tabs;
    private static final String SHOW_TABS = "show-tabs";


    public UsersLibraryView() {

    }
}
