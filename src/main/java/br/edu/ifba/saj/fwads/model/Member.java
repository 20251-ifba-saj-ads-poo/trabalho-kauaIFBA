package br.edu.ifba.saj.fwads.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Member extends AbstractEntity{

    @Column
    @NotBlank
    @Size(min = 5)
    private String name;

    @Column
    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf; // KEY PARA GERAR O UUID

    @Column
    @NotBlank
    @Size(min = 5)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "member_attended_meetings",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id")
    )
    private List<Meeting> attendedMeetings; // Encontros que participou

    @ManyToMany
    @JoinTable(
            name = "member_subscribed_meetings",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id")
    )
    private List<Meeting> subscribedMeetings; // Encontros que está inscrito

    @OneToMany(mappedBy = "host")
    private List<Meeting> myMeetings;

    public Member(@NotBlank @Size String name, @NotBlank @Size(min = 11, max = 11) String cpf,
                  @NotBlank @Size(min = 5)String password) {

        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.attendedMeetings = new ArrayList<>();
        this.subscribedMeetings = new ArrayList<>();
        this.myMeetings = new ArrayList<>();
    }

    public Member() {

    }

    // Getters
    public String getName() { return name; }
    public String getCpf() { return cpf; }
    public String getPassword() { return password; }
    public List<Meeting> getAttendedMeetings() { return attendedMeetings; }
    public List<Meeting> getSubscribedMeetings() { return subscribedMeetings; }
    public List<Meeting> getMyMeetings() { return myMeetings; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setPassword(String password) { this.password = password; }
    public void setAttendedMeetings(List<Meeting> attendedMeetings) { this.attendedMeetings = attendedMeetings; }
    public void setSubscribedMeetings(List<Meeting> subscribedMeetings) { this.subscribedMeetings = subscribedMeetings; }
    public void setMyMeetings(List<Meeting> myMeetings) {this.myMeetings = myMeetings;}

    public void addSubscribedMeeting(Meeting meeting) {
        if (meeting != null) {
            this.subscribedMeetings.add(meeting);
        }
    }

    public void addAttendedMeeting(Meeting meeting) {
        if (meeting != null && !attendedMeetings.contains(meeting)) {
            attendedMeetings.add(meeting);
        }
    }

    public void addUserMeeting(Meeting meeting) {
        if (meeting != null) {
            this.myMeetings.add(meeting);
        }
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", cpf=" + cpf +
                ", password='" + password + '\'' +
                ", attendedMeetings=" + attendedMeetings +
                ", subscribedMeetings=" + subscribedMeetings +
                ", myMeetings=" + myMeetings +
                '}';
    }
}
