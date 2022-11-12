package main_classes;

import java.util.ArrayList;

public interface IFlowerFarm {
    public void plantFlower(Class<? extends Flower> type) throws NotEnoughFieldException, FlowerNotFoundException;
    public void waterFlower(Flower flower) throws PlantIsNotPlantedException, NotEnoughMoneyException, NotEnoughWaterResourcesException, FlowerNotFoundException;
    public void harvestFlower(Flower flower) throws PlantIsNotGrownException, PlantIsNotPlantedException, FlowerNotFoundException;
    public int getFlowerSeedsCount(Class<? extends Flower> flowerType);
    public ArrayList<Flower> getPlantedFlowers();
    public int getHarvestedFlowersCount(Class<? extends Flower> flowerType);
    public int getWaterResources();
    public int getSize();
}
