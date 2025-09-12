package br.edu.ifba.saj.fwads.controller;

import br.edu.ifba.saj.fwads.model.Member;
import br.edu.ifba.saj.fwads.service.MeetingService;
import br.edu.ifba.saj.fwads.service.MemberService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
    private MemberService memberService = new MemberService();

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
        setUserSubs(); // FIZ A CHAMADA AQUI E NÃO NO INITIALIZE, POIS ELE EXECUTA ANTES DO CONTROLLER DEFINIR UM USUÁRIO
        setUserMeetings();
    }

    private void setUserMeetings(){
        userMeetings.setText(memberService.howManyUserMeetings(currentUser));
    }

    private void setUserSubs(){
        userSubs.setText(memberService.howManySubMeetings(currentUser));
    }

    private void setOpenMeetings() {
        openMeetings.setText(meetingService.howManyOpenMeetings());
    }
}
