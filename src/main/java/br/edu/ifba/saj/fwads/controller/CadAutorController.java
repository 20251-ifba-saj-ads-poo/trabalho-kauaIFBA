package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Autor;
import br.edu.ifba.saj.fwads.service.Service;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadAutorController {
    @FXML
    private TextField txNome;
    @FXML
    private TextField txEmail;
    @FXML
    private TextField txCPF;

    private MasterController masterController;
    private ListAutorController listAutorController;

    private Service<Autor> serviceAutor = new Service<>(Autor.class);
    
    public void setMasterController(MasterController masterController) {
        this.masterController = masterController;
    }

    public void setListAutorController(ListAutorController listAutorController) {
        this.listAutorController = listAutorController;
    }

    @FXML
    private void salvarAutor() {
        Autor novoAutor = new Autor(txNome.getText(),
                    txEmail.getText(), 
                    txCPF.getText());
        serviceAutor.create(novoAutor);
        new Alert(AlertType.INFORMATION, 
        "Autor:"+novoAutor.getNome()+" cadastrado com sucesso").showAndWait();
        limparTela();
        if (listAutorController!= null) {
            listAutorController.loadAutorList();
        }
    }
    @FXML
    private void limparTela() {
        txNome.setText("");
        txEmail.setText("");
        txCPF.setText("");
        //masterController.showFXMLFile("ListAutor.fxml");
        //new Alert(AlertType.INFORMATION, serviceAutor.findAll().toString()).showAndWait();
    }

}
