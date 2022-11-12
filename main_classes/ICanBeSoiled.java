package main_classes;

public interface ICanBeSoiled extends IPlant{
    public void soil() throws PlantIsNotPlantedException;
}
