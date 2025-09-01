package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Autor;
import br.edu.ifba.saj.fwads.model.Livro;
import br.edu.ifba.saj.fwads.service.Service;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.StringConverter;

public class CadLivroController {
    @FXML
    private TextField txTitulo;
    @FXML
    private TextField txSubTitulo;
    @FXML
    private TextField txISBN;
    @FXML
    private ChoiceBox<Autor> slAutor;

    private ListLivroController listLivroController;

    private Service<Livro> serviceLivro = new Service<>(Livro.class);
    private Service<Autor> serviceAutor = new Service<>(Autor.class);

    public void setListLivroController(ListLivroController listLivroController) {
        this.listLivroController = listLivroController;
    }

    @FXML
    void salvarLivro(ActionEvent event) {
        Livro novoLivro = new Livro(txTitulo.getText(),
        txSubTitulo.getText(), 
        txISBN.getText(),
        slAutor.getSelectionModel().getSelectedItem());
        serviceLivro.create(novoLivro);
        new Alert(AlertType.INFORMATION, 
        "Livro:"+novoLivro.getTitulo()+" cadastrado com sucesso!").showAndWait();
        limparTela();
        if (listLivroController!=null) {
            listLivroController.loadLivroList();
        }
    }

    @FXML 
    private void initialize() {
        slAutor.setConverter(new StringConverter<Autor>() {
            @Override
            public String toString(Autor obj) {
                if (obj != null) {
                    return obj.getNome() + ":" + obj.getEmail();
                }
                return "";
            }

            @Override
            public Autor fromString(String stringAutor) {
                return serviceAutor.findAll()
                    .stream()
                    .filter(autor -> stringAutor.equals(autor.getNome() + ":" + autor.getEmail()))
                    .findAny()
                    .orElse(null);                
            }
        });
        
        carregarListaAutores();
    }

    @FXML
    private void limparTela() {
        txTitulo.setText("");
        txSubTitulo.setText("");
        txISBN.setText("");
        slAutor.setValue(null);
        //new Alert(AlertType.INFORMATION, serviceLivro.findAll().toString()).showAndWait();
    }

    private void carregarListaAutores() {
        slAutor.setItems(FXCollections.observableList(serviceAutor.findAll()));
    }

}
