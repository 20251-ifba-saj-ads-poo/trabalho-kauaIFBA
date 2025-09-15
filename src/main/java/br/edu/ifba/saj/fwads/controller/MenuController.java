package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Member;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class MenuController {

    @FXML
    private BorderPane masterPane;
    @FXML
    private VBox menu;

    // Usuário
    private Member currentUser;

    public void setCurrentUser(Member user) {
        this.currentUser = user;
    }

    @FXML
    void showHome(ActionEvent actionEvent) {
        limparBotoes(actionEvent.getSource());
        HomeController controller = (HomeController) showFXMLFile("Home.fxml"); // ESSA LINHA DE CÓDIGO PASSA O CONTROLLER DAQUI PARA LÁ, PRECISO PARA ACESSAR O SETCURRENTUSER
        controller.setMenuController(this);
        controller.setCurrentUser(currentUser);
    }

    @FXML
    void showOpenMeetings(ActionEvent actionEvent) {
        limparBotoes(actionEvent.getSource());
        OpenMeetingsController controller = (OpenMeetingsController) showFXMLFile("OpenMeetings.fxml"); // ESSA LINHA DE CÓDIGO PASSA O CONTROLLER DAQUI PARA LÁ, PRECISO PARA ACESSAR O SETCURRENTUSER
        controller.setMenuController(this);
        controller.setCurrentUser(currentUser);
    }

    @FXML
    void createMeeting(ActionEvent actionEvent) {
        limparBotoes(actionEvent.getSource());
        CreateMeetingController controller = (CreateMeetingController) showFXMLFile("CreateMeeting.fxml"); // ESSA LINHA DE CÓDIGO PASSA O CONTROLLER DAQUI PARA LÁ, PRECISO PARA ACESSAR O SETCURRENTUSER
        controller.setMenuController(this);
        controller.setCurrentUser(currentUser);
    }

    @FXML
    void createBook(ActionEvent actionEvent) {
        limparBotoes(actionEvent.getSource());
        showFXMLFile("CreateBook.fxml");
    }

    @FXML
    void showUserMeetings(ActionEvent actionEvent) {
        limparBotoes(actionEvent.getSource());
        UserMeetingsController controller = (UserMeetingsController) showFXMLFile("UserMeetings.fxml"); // ESSA LINHA DE CÓDIGO PASSA O CONTROLLER DAQUI PARA LÁ, PRECISO PARA ACESSAR O SETCURRENTUSER
        controller.setMenuController(this);
        controller.setCurrentUser(currentUser);
    }

    @FXML
    void logOff(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Deseja realmente sair??", ButtonType.YES, ButtonType.NO);
        alert.showAndWait()
                .filter(response -> response == ButtonType.YES)
                .ifPresent(response -> {
                    App.setRoot("controller/Start.fxml");
                });
    }

    private void limparBotoes(Object source) {
        menu.getChildren().forEach((node) -> {
                    if (node instanceof Button btn) {
                        node.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
                    }
                }

        );
        if (source instanceof Button btn) {
            btn.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        }
    }

    public Object showFXMLFile(String resourceName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceName));
            Pane fxmlCarregado = loader.load();
            masterPane.setCenter(fxmlCarregado);
            return loader.getController();

        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Erro ao carregar o arquivo " + resourceName).showAndWait();
            e.printStackTrace();
        }
        return null;
    }
}
