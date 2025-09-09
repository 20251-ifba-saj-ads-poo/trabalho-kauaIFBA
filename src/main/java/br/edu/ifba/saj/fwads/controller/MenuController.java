package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.model.Usuario;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class MenuController {

    @FXML
    private Button menuItemCadAutor;

    @FXML
    private Button menuItemCadLivro;

    @FXML
    private Button menuItemHome;

    @FXML
    private Button menuItemListAutor;

    @FXML
    private Button menuItemListLivro;

    @FXML
    private BorderPane masterPane;

    @FXML
    private VBox menu;

    @FXML
    private Label userEmail;

    @FXML
    private Circle userPicture;

    private Member currentUser;

    public void setCurrentUser(Member user) {
        this.currentUser = user;
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

    @FXML
    void showHome(ActionEvent event) {
        limparBotoes(event.getSource());
        masterPane.setCenter(new Pane());

    }

    @FXML
    void showUsuarios(ActionEvent event) {
        limparBotoes(event.getSource());
        masterPane.setCenter(new Pane());
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

    @FXML
    void showCadAutor(ActionEvent event) {
        /*
        limparBotoes(event.getSource());
        CadAutorController controller = (CadAutorController) showFXMLFile("CadAutor.fxml");
        controller.setMenuController(this);

         */
    }

    @FXML
    void showListAutor(ActionEvent event) {
        limparBotoes(event.getSource());
        showFXMLFile("ListAutor.fxml");
    }

    @FXML
    void showListLivro(ActionEvent event) {
        limparBotoes(event.getSource());
        showFXMLFile("ListLivro.fxml");
    }

    @FXML
    void showCadLivro(ActionEvent event) {
        limparBotoes(event.getSource());
        showFXMLFile("CadLivro.fxml");
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

    private void setEmail(String email) {
        userEmail.setText(email);
    }
}
