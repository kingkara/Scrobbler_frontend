package com.crud.scrobbler.view.tracks;

import com.crud.scrobbler.domain.CommentDto;
import com.crud.scrobbler.domain.TrackDto;
import com.crud.scrobbler.service.CommentsService;
import com.crud.scrobbler.service.TracksService;
import com.crud.scrobbler.service.UsersService;
import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

@Route(value = "tracksSearch", layout = MainView.class)
@PageTitle("Find track")
public class TracksSearchView extends Div {
    private TextField filter = new TextField();
    private Grid<TrackDto> trackDtoGrid = new Grid<>();

    private final static Logger LOGGER = LoggerFactory.getLogger(TracksSearchView.class);

    public TracksSearchView(@Autowired TracksService tracksService, @Autowired CommentsService commentsService, @Autowired UsersService usersService) {
        SplitLayout globalLayot = new SplitLayout(new Label("Tracks List"),
                new Label("Tracks comments - click track to see comments"));

        //TRACKS LIST SEARCH
        filter.setPlaceholder("Filter by title");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);

        //TRACKS LIST GRID
        trackDtoGrid.addColumn(TrackDto::getTitle).setHeader("Title").setTextAlign(ColumnTextAlign.CENTER);
        trackDtoGrid.addColumn(TrackDto::getArtistName).setHeader("Artist").setTextAlign(ColumnTextAlign.CENTER);
        trackDtoGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        try {
            List<TrackDto> trackDtos = tracksService.getTracks();
            trackDtoGrid.setItems(trackDtos);
            filter.addValueChangeListener(e -> trackDtoGrid.setItems(tracksService.findByTitle(filter.getValue())));
        } catch (ResourceAccessException e) {
            LOGGER.warn("Couldn't get data from database. Check your backend connection.");
        } catch (NullPointerException e) {
            LOGGER.warn("There's no such song.");
        }

        //COMMENTS LIST
        trackDtoGrid.asSingleSelect().addValueChangeListener(event -> {
            globalLayot.removeAll();
            globalLayot.addToPrimary(filter, trackDtoGrid);
            TextField content = new TextField("Add new comment");
            Grid<CommentDto> commentDtoGrid = new Grid<>();
            Binder<CommentDto> binder = new Binder<>(CommentDto.class);
            Editor<CommentDto> editor = commentDtoGrid.getEditor();
            editor.setBinder(binder);
            editor.setBuffered(true);

            Button cancel = new Button("Cancel");
            Button save = new Button("Save");

            Collection<Button> editButtons = Collections
                    .newSetFromMap(new WeakHashMap<>());

            commentDtoGrid.addColumn(CommentDto::getUserName).setHeader("Username").setTextAlign(ColumnTextAlign.CENTER);
            Grid.Column<CommentDto> contentColumn = commentDtoGrid.addColumn(CommentDto::getText).setHeader("Content").
                    setTextAlign(ColumnTextAlign.CENTER);
            TextField contentField = new TextField();
            binder.forField(contentField).asRequired("Your comment must have body").bind("text");
            contentColumn.setEditorComponent(contentField);
            Grid.Column<CommentDto> editorColumn = commentDtoGrid.addComponentColumn(comment -> {
                Button edit = new Button("Edit");
                edit.addClickListener(e -> {
                    editor.editItem(comment);
                    content.focus();
                });
                edit.setEnabled(!editor.isOpen());
                editButtons.add(edit);
                return edit;
            });

            commentDtoGrid.addComponentColumn(commentDto -> {
                Button button = new Button("Remove");
                button.addClickListener(click -> {
                    try {
                        commentsService.deleteComment(commentDto.getId());
                        List<CommentDto> update = commentsService.getComments(tracksService.getTrack(event.getValue().getTitle()).getId());
                        commentDtoGrid.setItems(update);
                    } catch (HttpClientErrorException e) {
                        LOGGER.warn("Couldn't delete comment.");
                    }

                });
                button.setIcon(VaadinIcon.TRASH.create());
                return button;
            });

            editor.addOpenListener(e -> editButtons
                    .forEach(button -> button.setEnabled(!editor.isOpen())));
            editor.addCloseListener(e -> editButtons
                    .forEach(button -> button.setEnabled(!editor.isOpen())));

            Button saveEdit = new Button("Save", e -> editor.save());
            save.addClassName("save");

            Button cancelEdit = new Button("Cancel", e -> editor.cancel());
            cancel.addClassName("cancel");

            commentDtoGrid.getElement().addEventListener("keyup", event2 -> editor.cancel())
                    .setFilter("event.key === 'Escape' || event.key === 'Esc'");

            Div buttons = new Div(saveEdit, cancelEdit);
            editorColumn.setEditorComponent(buttons);

            editor.addSaveListener(
                    event3 -> {
                        CommentDto commentToUpdate = new CommentDto(event3.getItem().getId(), event3.getItem().getText(),
                                event3.getItem().getUserName(), event3.getItem().getTrackTitle());
                        commentsService.editComment(commentToUpdate);
                    });

            try {
                commentDtoGrid.setItems(commentsService.getComments(event.getValue().getId()));
            } catch (NullPointerException e) {
                LOGGER.warn("There's no such song song.");
            }

            Binder<CommentDto> binderValidate = new BeanValidationBinder<>(CommentDto.class);
            binderValidate.forField(content).asRequired("Your comment must have body").bind("text");
            binderValidate.setBean(new CommentDto());

            cancel.addClickListener(e -> binderValidate.readBean(null));
            save.addClickListener(e -> {
                binderValidate.validate();
                if (binderValidate.isValid()) {
                    try {
                        CommentDto commentToAdd = new CommentDto(binderValidate.getBean().getText(), usersService.getUser(123123).getUsername(),
                                event.getValue().getTitle());
                        Notification.show("Dodano komenatrz!");
                        commentsService.addComment(commentToAdd);
                        commentDtoGrid.setItems(commentsService.getComments(event.getValue().getId()));
                    } catch (ResourceAccessException ex) {
                        LOGGER.warn("The comment could not be posted.");
                    }
                } else {
                    Notification.show("Nieprawidłowa zawartość");
                }
            });
            globalLayot.addToSecondary(content, cancel, save, commentDtoGrid);
        });

        setSizeFull();
        globalLayot.addToPrimary(filter, trackDtoGrid);
        add(globalLayot);
    }
}
