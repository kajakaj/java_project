package main_classes;

public class PlantIsNotGrownException extends Exception{
    public PlantIsNotGrownException(Flower flower){
        super(flower + " is not grown.");
    }
    
}
