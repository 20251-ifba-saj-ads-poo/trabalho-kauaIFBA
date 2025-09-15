package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.MemberService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Map;

public class LoginController {

    @FXML
    private PasswordField txSenha;
    @FXML
    private TextField txUsuario;

    // Serviço
    private MemberService memberService = new MemberService();

    @FXML
    void login(ActionEvent actionEvent) {
        try {
            Member user = memberService.validaLogin(txUsuario.getText(), txSenha.getText());

            new Alert(AlertType.CONFIRMATION, "Acessando como usuário " + user.getName()).showAndWait();

            App.setRoot("controller/Menu.fxml");
            MenuController controller = (MenuController) App.getController();
            controller.setCurrentUser(user);

        } catch (LoginInvalidoException e) {
            new Alert(AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (Exception e){
            e.printStackTrace();
            new Alert(AlertType.ERROR, "Erro inesperado, favor entra em contato com a equipe de desenvolvimento SUS").showAndWait();
        }
    }

    @FXML
    void newAccount(ActionEvent actionEvent) {
        StartController.creatingNewAccount = true;
        App.setRoot("controller/Start.fxml");
    }
}
