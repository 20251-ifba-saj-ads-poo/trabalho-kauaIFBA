package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.CpfUniquenessException;
import br.edu.ifba.saj.fwads.exception.EmptyFieldException;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import br.edu.ifba.saj.fwads.exception.LoginInvalidoException;
import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.MemberService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

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
            memberService.validaEmptyField(txName.getText(), txCpf.getText(), txPassword.getText());
            memberService.validaFieldFormat(txName.getText(), txCpf.getText(), txPassword.getText());
            memberService.validaUniquenessCpf(txCpf.getText());

            Member newUser = new Member(txName.getText(), txCpf.getText(), txPassword.getText());
            memberService.create(newUser);

            new Alert(Alert.AlertType.CONFIRMATION, "Conta criada com sucesso! Acessando como usuário " + newUser.getName()).showAndWait();

            App.setRoot("controller/Master.fxml");
            MasterController controller = (MasterController) App.getController();
            controller.setCurrentUser(newUser);

        } catch (EmptyFieldException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
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
