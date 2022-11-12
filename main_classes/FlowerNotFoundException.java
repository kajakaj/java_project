package main_classes;

public class FlowerNotFoundException extends Exception{
    public FlowerNotFoundException(){
        super("You do not have that flower.");
    }
    
}
