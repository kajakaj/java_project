package main_classes;

import java.util.ArrayList;

public interface IFarmer {
    public String getName();
    public int getMoney();
    public FlowerFarm getFlowerFarm();
    public ArrayList<Flower> buyProducts(ProductBuy product, int quantity) throws NotEnoughMoneyException, NegativeSizeValueException, NegativeWaterResourcesValueException, NegativeSoilResourcesValueException, NegativeQuantityValueEsception;
    public ArrayList<Flower> sellProducts(ProductSell product, int quantity) throws NotEnoughFlowerException, NegativeMoneyValueException, NegativeQuantityValueEsception;
}
