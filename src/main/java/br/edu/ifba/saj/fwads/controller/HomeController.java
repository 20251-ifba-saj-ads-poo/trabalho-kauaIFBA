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

    // Serviço e usuário
    private MeetingService meetingService = new MeetingService();
    private MemberService memberService = new MemberService();
    private Member currentUser;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public void setCurrentUser(Member user) {
        currentUser = user;
        userName.setText(currentUser.getName());
        /*
            Diferente dos encontros em aberto (que está no initialize), a chamada dos encontros que envolvem o usuário
            precisam ser chamados depois do controller receber o atual usuário, como o initialize executa antes de todos
            métodos, movi estas chamadas para cá.
         */
        setUserSubs();
        setUserMeetings();
    }

    @FXML
    private void initialize() {
        setOpenMeetings();
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
