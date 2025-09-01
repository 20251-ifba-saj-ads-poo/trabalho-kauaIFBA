package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.model.Autor;
import br.edu.ifba.saj.fwads.service.Service;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListAutorController {
    @FXML
    private TableView<Autor> tblAutor;

    @FXML
    private TableColumn<Autor, String> columnNome;
    @FXML
    private TableColumn<Autor, String> columnEmail;
    @FXML
    private TableColumn<Autor, String> columnCPF;


    @FXML
    public void initialize() {
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<>("CPF"));
        loadAutorList();
    }
    
    public void loadAutorList() {
        tblAutor.setItems(FXCollections.observableList(new Service(Autor.class).findAll()));
    }

    @FXML
    public void showNovoAutor() {
        
        Stage stage = new Stage();            
        Scene scene = new Scene(App.loadFXML("controller/CadAutor.fxml"), 800, 600);            
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL); 
        CadAutorController controller = (CadAutorController) App.getController();
        controller.setListAutorController(this);
        stage.showAndWait();            
    }

}
