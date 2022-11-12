package main_classes;

public class NotEnoughFieldException extends NotEnoughResourcesException{
    public NotEnoughFieldException(){
        super("field");
    }
}
