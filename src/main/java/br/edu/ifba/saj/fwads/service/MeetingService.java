package br.edu.ifba.saj.fwads.service;

import br.edu.ifba.saj.fwads.exception.BookUniquinessException;
import br.edu.ifba.saj.fwads.exception.ImpossibleTimeTravel;
import br.edu.ifba.saj.fwads.exception.IncorretFormatException;
import br.edu.ifba.saj.fwads.model.Book;
import br.edu.ifba.saj.fwads.model.Meeting;
import br.edu.ifba.saj.fwads.model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MeetingService extends Service<Meeting>{

    public MeetingService() {
        super(Meeting.class);
    }

    public String howManyOpenMeetings(){
        List<Meeting> allOpenMeetings = findAll();
        int count = 0;

        for(Meeting meeting : allOpenMeetings){
            count++;
        }

        return String.valueOf(count);

    }

    public void updateMeeting(LocalDate newDate, Book newBook, Meeting meeting) throws IncorretFormatException, ImpossibleTimeTravel {
        List<String> errors = new ArrayList<>();

        if(newBook == null){
            errors.add("Selecione um livro.");
        }

        if(newDate == null){
            errors.add("Selecione uma data para o encontro.");
        }

        if(!errors.isEmpty()){
            String allErrors = String.join("\n", errors);
            throw new IncorretFormatException(allErrors);
        }

        if(newDate.isBefore(LocalDate.now())){
            throw new ImpossibleTimeTravel("Impossível agendar para esta data.");
        }

        meeting.setBook(newBook);
        meeting.setDateAndTime(newDate);

        update(meeting);
    }

    public Meeting create(LocalDate date, Book book, Member host) throws IncorretFormatException, ImpossibleTimeTravel {
        List<String> errors = new ArrayList<>();

        if(book == null){
            errors.add("Selecione um livro.");
        }

        if(date == null){
            errors.add("Selecione uma data para o encontro.");
        }

        if(!errors.isEmpty()){
            String allErrors = String.join("\n", errors);
            throw new IncorretFormatException(allErrors);
        }

        if(date.isBefore(LocalDate.now())){
            throw new ImpossibleTimeTravel("Impossível agendar para esta data.");
        }

        Meeting newMeeting = new Meeting(date, book, host);
        newMeeting.addSubscribedMember(host);
        return create(newMeeting);
    }

}
