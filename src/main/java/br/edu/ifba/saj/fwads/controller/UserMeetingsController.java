package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.App;
import br.edu.ifba.saj.fwads.exception.UserIsNull;
import br.edu.ifba.saj.fwads.model.Meeting;
import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.MeetingService;
import br.edu.ifba.saj.fwads.service.MemberService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserMeetingsController {
    @FXML
    private TableColumn<Meeting, String> clnBook;

    @FXML
    private TableColumn<Meeting, String> clnHost;

    @FXML
    private TableColumn<Meeting, String> clnDate;

    @FXML
    private TableColumn<Meeting, String> clnSubs;

    @FXML
    private TableView<Meeting> tblMeetings;

    private MenuController menuController;

    private MeetingService meetingService = new MeetingService();
    private MemberService memberService = new MemberService();

    private Member currentUser;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setCurrentUser(Member user) {
        currentUser = user;
        loadMeetingList();
    }

    @FXML
    public void initialize() {
        clnBook.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getTitle()));
        clnHost.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHost().getName()));

        clnSubs.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubscribedMembers().stream().map(Member::getName).collect(Collectors.joining(", "))));

        clnDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateAndTime().toString()));
    }

    @FXML
    void showEdit(ActionEvent actionEvent) {

        Stage stage = new Stage();
        Scene scene = new Scene(App.loadFXML("controller/EditMeeting.fxml"), 715, 385);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        EditMeetingController controller = (EditMeetingController) App.getController();
        controller.setUserMeetingController(this);
        controller.getMeeting(tblMeetings.getSelectionModel().getSelectedItem());

        stage.showAndWait();

    }

    public void loadMeetingList(){
        try {
            tblMeetings.setItems(FXCollections.observableList(memberService.returnUserMeetings(currentUser)));
        } catch (UserIsNull e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
}
