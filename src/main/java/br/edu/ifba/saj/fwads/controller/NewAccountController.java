package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CpfUniquenessException;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.MemberService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.Map;

public class NewAccountController {
    @FXML
    private TextField txName;

    @FXML
    private TextField txCpf;

    @FXML
    private TextField txPassword;

    private MemberService memberService = new MemberService();

    @FXML
    void newAccount(ActionEvent event) {
        try {

            memberService.create(txName.getText(),txCpf.getText(), txPassword.getText());
            Member user = memberService.validaLogin(txCpf.getText(), txPassword.getText());

            new Alert(Alert.AlertType.CONFIRMATION, "Conta criada com sucesso! Acessando como usuário " + user.getName()).showAndWait();

            App.setRoot("controller/Menu.fxml");
            MenuController controller = (MenuController) App.getController();
            controller.setCurrentUser(user);
        } catch (IncorretFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (CpfUniquenessException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro inesperado, favor entra em contato com a equipe de desenvolvimento").showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        StartController.creatingNewAccount = false;
        App.setRoot("controller/Start.fxml");
    }
}
