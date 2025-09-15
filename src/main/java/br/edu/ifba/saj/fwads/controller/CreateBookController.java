package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.BookUniquinessException;
import br.edu.ifba.saj.fwads.model.Book;
import br.edu.ifba.saj.fwads.service.BookService;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CreateBookController {

    @FXML
    private TextField txTitle;
    @FXML
    private TextField txAuthor;
    @FXML
    private TextField txGenre;
    @FXML
    private TextField txPublisher;
    @FXML
    private DatePicker dpPublishDate;
    @FXML
    private TextField txSynopsis;

    private BookService bookService = new BookService();

    private CreateMeetingController createMeetingController;
    private EditMeetingController editMeetingController;

    public void setCreateMeetingController(CreateMeetingController createMeetingController) {
        this.createMeetingController = createMeetingController;
    }

    public void setEditMeetingController(EditMeetingController editMeetingController) {
        this.editMeetingController = editMeetingController;
    }

    @FXML
    void newBook(ActionEvent event) {

        try {
            Book newBook = bookService.create(txTitle.getText(), txAuthor.getText(), txGenre.getText(), txSynopsis.getText(), txPublisher.getText(), dpPublishDate.getValue());

            new Alert(Alert.AlertType.CONFIRMATION, "Livro " + newBook.getTitle() + " cadastrado com sucesso!").showAndWait();
            clear();
        } catch(IncorretFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch(BookUniquinessException e ){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro inesperado, favor entra em contato com a equipe de desenvolvimento").showAndWait();
        }

    }

    @FXML
    void cancel(ActionEvent event) {
        StartController.creatingNewAccount = false;
        App.setRoot("controller/Menu.fxml");
    }

    @FXML
    private void clear() {
        txTitle.setText("");
        txAuthor.setText("");
        txGenre.setText("");
        txPublisher.setText("");
        txSynopsis.setText("");
        dpPublishDate.setValue(null);
    }
}
