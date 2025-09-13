package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.exception.UserAlreadySubscribed;
import br.edu.ifba.saj.fwads.model.Book;
import br.edu.ifba.saj.fwads.model.Meeting;
import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.BookService;
import br.edu.ifba.saj.fwads.service.MeetingService;
import br.edu.ifba.saj.fwads.service.MemberService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

public class OpenMeetingsController {

    @FXML
    private ChoiceBox<Meeting>  slOpenMeetings;

    private MenuController menuController;

    private BookService bookService = new BookService();
    private MeetingService meetingService = new MeetingService();
    private MemberService memberService = new MemberService();

    private Member currentUser;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setCurrentUser(Member user) {
        currentUser = user;
    }

    @FXML
    private void initialize(){
        slOpenMeetings.setConverter(new StringConverter<Meeting>() {
            @Override
            public String toString(Meeting obj) {
                if (obj != null) {
                    return "Encontro do Livro: " + obj.getBook().getTitle() + "\nMinistrada por: "  + obj.getHost().getName() + "\nIrá ocorrer em: " + obj.getDateAndTime().toString();
                }
                return "";
            }

            @Override
            public Meeting fromString(String stringMeeting) {
                return meetingService.findAll()
                        .stream()
                        .filter(obj -> slOpenMeetings.equals("Encontro do Livro: " + obj.getBook().getTitle() + "\nMinistrada por: "  + obj.getHost().getName() + "\nIrá ocorrer em: " + obj.getDateAndTime().toString()))
                        .findAny()
                        .orElse(null);
            }
        });

        loadMeetingList();
    }

    @FXML
    void subscribe(ActionEvent event) {
        try{
            memberService.subscribe(slOpenMeetings.getSelectionModel().getSelectedItem(), currentUser);
            Meeting subMeeting = slOpenMeetings.getSelectionModel().getSelectedItem();
            //memberService.update(currentUser).addSubscribedMeeting(subMeeting);

            new Alert(Alert.AlertType.CONFIRMATION, "Você se inscreveu no encontro do livro " + subMeeting.getBook().getTitle() + " ministrada por:  " + subMeeting.getHost().getName() + " que irá ocorrer em: " + subMeeting.getDateAndTime()).showAndWait();
            //clear();
        } catch (UserAlreadySubscribed e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro inesperado, favor entra em contato com a equipe de desenvolvimento").showAndWait();
        }
    }

    @FXML
    void seeMore(ActionEvent event) {

    }

    private void loadMeetingList() {
        slOpenMeetings.setItems(FXCollections.observableList(meetingService.findAll()));
    }

    private void clear() {
        slOpenMeetings.getSelectionModel().clearSelection();
    }
}
