package main_classes;

public class NegativeValueException extends Exception{
    public NegativeValueException(String value) {
        super(value + " can not be negative.");
    }  
}
