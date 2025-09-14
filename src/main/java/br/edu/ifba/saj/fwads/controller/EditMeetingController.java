package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ImpossibleTimeTravel;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import br.edu.ifba.saj.fwads.model.Book;
import br.edu.ifba.saj.fwads.model.Meeting;
import br.edu.ifba.saj.fwads.service.BookService;
import br.edu.ifba.saj.fwads.service.MeetingService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

public class EditMeetingController {

    @FXML
    private ChoiceBox<Book> slBook;

    @FXML
    private DatePicker dpDate;

    private Meeting meeting;

    private UserMeetingsController userMeetingsController;

    private MeetingService meetingService = new MeetingService();
    private BookService bookService = new BookService();


    public void setUserMeetingController(UserMeetingsController userMeetingsController) {
        this.userMeetingsController = userMeetingsController;
    }

    public void getMeeting(Meeting meeting) {
        this.meeting = meeting;
        System.out.println("Meeting: " + meeting);
    }

    @FXML
    void save(ActionEvent actionEvent) {
        try{
            meetingService.updateMeeting(dpDate.getValue(), slBook.getValue(), meeting);

            new Alert(Alert.AlertType.CONFIRMATION, "Encontro do livro " + slBook.getSelectionModel().getSelectedItem().getTitle() + " agendado com sucesso para a data " + dpDate.getValue()).showAndWait();
            clear();
        } catch (IncorretFormatException | ImpossibleTimeTravel e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro inesperado, favor entra em contato com a equipe de desenvolvimento").showAndWait();
        }
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
                        .filter(book -> stringBook.equals(book.getTitle() + " | "  + book.getPublisher()))
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
    void newBook(ActionEvent actionEvent) {

    }

    @FXML
    void cancel(ActionEvent actionEvent) {

    }

    @FXML
    private void clear() {
        slBook.setValue(null);
        dpDate.setValue(null);
    }
}
