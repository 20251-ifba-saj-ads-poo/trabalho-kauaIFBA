package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.ImpossibleTimeTravel;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import br.edu.ifba.saj.fwads.model.Book;
import br.edu.ifba.saj.fwads.model.Meeting;
import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.BookService;
import br.edu.ifba.saj.fwads.service.MeetingService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EditMeetingController {

    @FXML
    private ChoiceBox<Book> slBook;

    @FXML
    private DatePicker dpDate;

    @FXML
    private ListView<Member> lvSubs;

    private Meeting meeting;

    private UserMeetingsController userMeetingsController;

    private MeetingService meetingService = new MeetingService();
    private BookService bookService = new BookService();

    public void setUserMeetingController(UserMeetingsController userMeetingsController) {
        this.userMeetingsController = userMeetingsController;
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

        lvSubs.setCellFactory(param -> new ListCell<Member>() {
            @Override
            protected void updateItem(Member member, boolean empty) {
                super.updateItem(member, empty);
                if (empty || member == null) {
                    setText(null);
                } else {
                    setText(member.getName());
                }
            }
        });
    }

    private void loadBookList() {
        slBook.setItems(FXCollections.observableList(bookService.findAll()));
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
    void newBook(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("controller/CreateBook.fxml"), 450, 750);
        stage.setScene(scene);        stage.initModality(Modality.APPLICATION_MODAL);
        CreateBookController controller = (CreateBookController) App.getController();
        controller.setEditMeetingController(this);

        stage.showAndWait();
    }

    @FXML
    private void clear() {
        slBook.setValue(null);
        dpDate.setValue(null);
    }

    public void getMeeting(Meeting meeting) {
        this.meeting = meeting;
        System.out.println("Meeting: " + meeting);
    }
}
