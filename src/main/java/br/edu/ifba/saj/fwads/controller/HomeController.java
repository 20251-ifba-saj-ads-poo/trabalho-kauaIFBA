package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.MeetingService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.h2.engine.User;

public class HomeController {

    @FXML
    private Label userName;
    @FXML
    private Label userMeetings;
    @FXML
    private Label userSubs;
    @FXML
    private Label openMeetings;

    private MenuController menuController;

    private Member currentUser;
    private MeetingService meetingService = new MeetingService();

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }


    @FXML
    private void initialize() {
        setOpenMeetings();
    }

    public void setCurrentUser(Member user) {
        currentUser = user;
        userName.setText(currentUser.getName());
    }

    private void setOpenMeetings() {
        openMeetings.setText(meetingService.howManyOpenMeetings());
    }
}
