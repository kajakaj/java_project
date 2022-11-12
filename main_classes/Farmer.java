package main_classes;

import java.util.ArrayList;

public class Farmer implements IFarmer{
    private String name;
    private FlowerFarm flowerFarm;
    private int money;

    public Farmer(String name, int money, FlowerFarm flowerFarm) {
        this.name = name;
        this.money = money;
        this.flowerFarm = flowerFarm;
    }

    
    /** 
     * @return String the farmer's name
     */
    public String getName(){
        return this.name;
    }

    
    /** 
     * @return int the value of money
     */
    public int getMoney(){
        return this.money;
    }

    
    /** 
     * @return FlowerFarm the flower farm
     */
    public FlowerFarm getFlowerFarm(){
        return this.flowerFarm;
    }

    
    /** 
     * @param money the new value of money
     * @throws NegativeMoneyValueException
     */
    private void setMoney(int money) throws NegativeMoneyValueException{
        if(money >= 0){
            this.money = money;
        } else {
            throw new NegativeMoneyValueException();
        }
    }


    /** 
     * @param product the product to be bought
     * @param quantity the quantity of the products to be bought
     * @return ArrayList<Flower> the list of bought flowers
     * @throws NotEnoughMoneyException
     * @throws NegativeSizeValueException
     * @throws NegativeWaterResourcesValueException
     * @throws NegativeSoilResourcesValueException
     * @throws NegativeQuantityValueEsception
     */
    public ArrayList<Flower> buyProducts(ProductBuy product, int quantity) throws NotEnoughMoneyException, NegativeSizeValueException, NegativeWaterResourcesValueException, NegativeSoilResourcesValueException, NegativeQuantityValueEsception{
        if(quantity < 0){
            throw new NegativeQuantityValueEsception();
        }

        int finalPrice = product.getPrice() * quantity;

        ArrayList<Flower> flowers = new ArrayList<>();
        
        try{
            this.setMoney(this.money - finalPrice);
            switch(product) {
                case FIELD: this.flowerFarm.setSize(this.flowerFarm.getSize() + quantity); break;
                case WATER: this.flowerFarm.setWaterResources(this.flowerFarm.getWaterResources() + quantity); break;
                case SOIL: this.flowerFarm.setSoilResources(this.flowerFarm.getSoilResources() + quantity); break;
                case TULIP_SEED: 
                    for(int i = 0; i < quantity; i++){
                        Tulip tulip = new Tulip();
                        this.flowerFarm.getFlowers().add(tulip);
                        flowers.add(tulip);
                    }
                    break;
                case ROSE_SEED:
                    for(int i = 0; i < quantity; i++){
                        Rose rose = new Rose();
                        this.flowerFarm.getFlowers().add(rose);
                        flowers.add(rose);
                    }
                    break;
                }
        } catch (NegativeMoneyValueException e){
            throw new NotEnoughMoneyException();
        }
        return flowers;   
    }
        

    
    /** 
     * @param flowerType the type of the flower (Tulip or Rose)
     * @param quantity the quantity of the flowers to be sold
     * @param price the price of the flower
     * @return ArrayList<Flower> the list of sold flowers
     * @throws NotEnoughFlowerException
     * @throws NegativeMoneyValueException
     */
    private ArrayList<Flower> sellFlowers(Class<? extends Flower> flowerType, int quantity, int price) throws NotEnoughFlowerException, NegativeMoneyValueException{
        int finalProfit = price * quantity;
        int flowerCount = this.flowerFarm.getHarvestedFlowersCount(flowerType);
        if(flowerCount >= quantity){
            this.setMoney(this.money + finalProfit);
            return this.flowerFarm.removeHarvestedFlowers(flowerType, flowerCount);
        } else {
            throw new NotEnoughFlowerException();
        }
    }

    
    /** 
     * @param product the product to be sold
     * @param quantity the quantity of the products to be sold
     * @return ArrayList<Flower> the list of sold flowers
     * @throws NegativeMoneyValueException
     * @throws NotEnoughFlowerException
     * @throws NegativeQuantityValueEsception
     */
    public ArrayList<Flower> sellProducts(ProductSell product, int quantity) throws NegativeMoneyValueException, NotEnoughFlowerException, NegativeQuantityValueEsception{
        if(quantity < 0){
            throw new NegativeQuantityValueEsception();
        }
        
        switch(product) {
            case TULIP_FLOWER: return this.sellFlowers(Tulip.class, quantity, product.getPrice());
            case ROSE_FLOWER: return this.sellFlowers(Rose.class, quantity, product.getPrice());
        }
        return null;
    }

    
    /** 
     * @return String the name of the farmer
     */
    @Override
    public String toString(){
        return this.name;
    }

}
