package main_classes;

public class NotEnoughFlowerException extends NotEnoughResourcesException{
    public NotEnoughFlowerException(){
        super("flowers");
    }
}
