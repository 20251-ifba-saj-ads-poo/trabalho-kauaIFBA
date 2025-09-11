package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ImpossibleTimeTravel;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import br.edu.ifba.saj.fwads.model.Autor;
import br.edu.ifba.saj.fwads.model.Book;
import br.edu.ifba.saj.fwads.model.Meeting;
import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.BookService;
import br.edu.ifba.saj.fwads.service.MeetingService;
import br.edu.ifba.saj.fwads.service.Service;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

public class CreateMeetingController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ChoiceBox<Book> slBook;
    @FXML
    private DatePicker dpDate;

    private BookService bookService = new BookService();
    private MeetingService meetingService = new MeetingService();

    private Member currentUser;

    public void setCurrentUser(Member currentUser) {
        this.currentUser = currentUser;
    }

    @FXML
    private void initialize(){
        slBook.setConverter(new StringConverter<Book>() {
            @Override
            public String toString(Book obj) {
                if (obj != null) {
                    return obj.getTitle() + " | "  + obj.getPublisher();
                }
                return "";
            }

            @Override
            public Book fromString(String stringBook) {
                return bookService.findAll()
                        .stream()
                        .filter(obj -> stringBook.equals(obj.getTitle() + " | "  + obj.getPublisher()))
                        .findAny()
                        .orElse(null);
            }
        });

        loadBookList();
    }

    private void loadBookList() {
        slBook.setItems(FXCollections.observableList(bookService.findAll()));
    }

    @FXML
    void newMeeting(ActionEvent event) {

        try{
            Meeting newMeeting = meetingService.create(dpDate.getValue(), slBook.getSelectionModel().getSelectedItem(), currentUser);
        } catch (IncorretFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (ImpossibleTimeTravel e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro inesperado, favor entra em contato com a equipe de desenvolvimento").showAndWait();
        }
    }

    @FXML
    void newBook(ActionEvent actionEvent) {

    }

    @FXML
    void cancel(ActionEvent event) {
        App.setRoot("controller/Menu.fxml");
    }
}
