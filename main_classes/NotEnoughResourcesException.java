package main_classes;

public class NotEnoughResourcesException extends Exception{
    public NotEnoughResourcesException(String resource){
        super("There is not enough " + resource + ".");
    }
}
