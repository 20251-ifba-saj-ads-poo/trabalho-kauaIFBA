package br.edu.ifba.saj.fwads.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meeting extends AbstractEntity{

    @Column
    @NotBlank
    private LocalDate date;

    @OneToOne
    private Book book;

    @ManyToMany(mappedBy = "subscribedMeetings")
    private List<Member> subscribedMembers;

    @OneToOne
    @JoinColumn(name = "host_id")
    private Member host;

    public Meeting(@NotBlank LocalDate date, Book book, Member host) {
        this.date = date;
        this.book = book;
        this.host = host;
        this.subscribedMembers = new ArrayList<>();
    }

    public Meeting() {

    }

    // Getters
    public LocalDate getDateAndTime() { return date; }
    public Book getBook() { return book; }
    public List<Member> getSubscribedMembers() { return subscribedMembers; }
    public Member getHost() { return host; }

    // Setters
    public void setDateAndTime(LocalDate date) { this.date = date; }
    public void setBook(Book book) { this.book = book; }
    public void setSubscribedMembers(List<Member> subscribedMembers) { this.subscribedMembers = subscribedMembers; }
    public void setHost(Member host) { this.host = host; }

    public void addSubscribedMember(Member subscribedMember) {
        if (subscribedMember != null) {
            this.subscribedMembers.add(subscribedMember);
        }
    }
}
