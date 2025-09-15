package br.edu.ifba.saj.fwads.exception;

// Exceção para formatos de data impossíveis
public class ImpossibleTimeTravel extends Exception{
    public ImpossibleTimeTravel(String msg){
        super(msg);
    }
}
