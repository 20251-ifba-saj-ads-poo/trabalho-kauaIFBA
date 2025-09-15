package br.edu.ifba.saj.fwads.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class StartController {

    @FXML
    private GridPane gridPane;
    @FXML
    private StackPane stackPane;

    public static boolean creatingNewAccount = false;

    @FXML
    public void initialize(){
        if(!creatingNewAccount){
            showFXMLFile("Login.fxml");
            return;
        }
        showFXMLFile("NewAccount.fxml");
    }

    public Object showFXMLFile(String resourceName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceName));
            Pane fxmlCarregado = loader.load();
            stackPane.getChildren().add(fxmlCarregado);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao carregar o arquivo " + resourceName).showAndWait();
            e.printStackTrace();
        }
        return null;
    }
}
