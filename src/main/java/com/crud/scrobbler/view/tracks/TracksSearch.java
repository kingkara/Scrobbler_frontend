package com.crud.scrobbler.view.tracks;

import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "tracksSearch", layout = MainView.class)
@PageTitle("Find track")
public class TracksSearch extends Div {
    public TracksSearch() {

    }
}
