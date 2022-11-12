package main_classes;

public class NotEnoughMoneyException extends NotEnoughResourcesException{
    public NotEnoughMoneyException(){
        super("money");
    }
}
