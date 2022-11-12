package main_classes;

public class NegativeMoneyValueException extends NegativeValueException{
    public NegativeMoneyValueException(){
        super("Money");
    }
}
