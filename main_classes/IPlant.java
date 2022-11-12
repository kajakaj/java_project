package main_classes;
public interface IPlant {
    public void plant();
    public void water() throws PlantIsNotPlantedException;
    public void harvest() throws PlantIsNotPlantedException, PlantIsNotGrownException;
}
