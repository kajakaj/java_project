package main_classes;

public class NegativeHydrationValueException extends NegativeValueException{
    public NegativeHydrationValueException(){
        super("Current hydration");
    }
}
