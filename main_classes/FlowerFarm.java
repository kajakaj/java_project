package main_classes;
import java.util.ArrayList;

public class FlowerFarm implements IFlowerFarm{
    private int size;
    private int waterResources;
    private int soilResources;
    private ArrayList<Flower> flowers;

    public FlowerFarm(int size, int waterResources, int soilResources, ArrayList<Flower> flowers) {
        this.size = size;
        this.waterResources = waterResources;
        this.soilResources = soilResources;
        this.flowers = flowers;
    }

    
    /** 
     * @return ArrayList<Flower> the list of all flowers
     */
    public ArrayList<Flower> getFlowers(){
        return flowers;
    }

    
    /** 
     * @return int the remaining fields' size
     */
    public int getSize(){
        return this.size;
    }

    
    /** 
     * @return int the water resources value
     */
    public int getWaterResources(){
        return this.waterResources;
    }

    
    /** 
     * @return int the soil resources value
     */
    public int getSoilResources(){
        return this.soilResources;
    }

    
    /** 
     * @param size the new field size value
     * @throws NegativeSizeValueException
     */
    public void setSize(int size) throws NegativeSizeValueException{
        if(size >= 0){
            this.size = size;
        } else {
            throw new NegativeSizeValueException();
        }
    }

    
    /** 
     * @param waterResources the new water resources value
     * @throws NegativeWaterResourcesValueException
     */
    public void setWaterResources(int waterResources) throws NegativeWaterResourcesValueException{
        if(waterResources >= 0){
            this.waterResources = waterResources;
        } else {
            throw new NegativeWaterResourcesValueException();
        }
    }

    
    /** 
     * @param soilResources the new soil resources value
     * @throws NegativeSoilResourcesValueException
     */
    public void setSoilResources(int soilResources) throws NegativeSoilResourcesValueException{
        if(soilResources >= 0){
            this.soilResources = soilResources;
        } else {
            throw new NegativeSoilResourcesValueException();
        }
    }

    
    /** 
     * @return boolean whether or not there is empty field part available
     */
    public boolean isEmptyPartAvailable(){
        return this.size > 0;
    }

    
    /** 
     * @param flowerId the id of the flower
     * @return Flower the Flower with given id
     * @throws FlowerNotFoundException
     */
    public Flower getFlower(int flowerId) throws FlowerNotFoundException{
        for(Flower flower: flowers){
            if(flower.getId() == flowerId){
                return flower;
            }
        }
        throw new FlowerNotFoundException();
    }
    
    
    /** 
     * @param flowerType the type of the flower (Tulip or Rose)
     * @throws NotEnoughFieldException
     * @throws FlowerNotFoundException
     */
    public void plantFlower(Class<? extends Flower> flowerType) throws NotEnoughFieldException, FlowerNotFoundException{
        if(isEmptyPartAvailable()){
            for(Flower flower: this.getFlowerSeeds()){
                if(flower.getClass().equals(flowerType)){
                    flower.plant();
                    --this.size;
                    return;
                }
            }
            throw new FlowerNotFoundException();
        } else {
            throw new NotEnoughFieldException();
        }     
    }

    
    /** 
     * @param flower the Flower to be watered
     * @throws PlantIsNotPlantedException
     * @throws NotEnoughWaterResourcesException
     * @throws FlowerNotFoundException
     */
    public void waterFlower(Flower flower) throws PlantIsNotPlantedException, NotEnoughWaterResourcesException, FlowerNotFoundException{
        if(this.flowers.contains(flower)){
            if(this.waterResources > 0){
                flower.water();
                --this.waterResources;
            } else {
                throw new NotEnoughWaterResourcesException();
            }
        } else {
            throw new FlowerNotFoundException();
        }  
    }

    
    /** 
     * @param flower the flower to be soiled
     * @throws PlantIsNotPlantedException
     * @throws NotEnoughSoilResourcesException
     * @throws FlowerNotFoundException
     */
    public <T extends ICanBeSoiled> void soilFlower(T flower) throws PlantIsNotPlantedException, NotEnoughSoilResourcesException, FlowerNotFoundException{
        if(this.flowers.contains(flower)){
            if(this.soilResources > 0){
                flower.soil();
                --this.soilResources;
            } else {
                throw new NotEnoughSoilResourcesException();
            }
        } else {
            throw new FlowerNotFoundException();
        }  
    }

    
    /** 
     * @param flower the flower to be harvested
     * @throws PlantIsNotGrownException
     * @throws PlantIsNotPlantedException
     * @throws FlowerNotFoundException
     */
    public void harvestFlower(Flower flower) throws PlantIsNotGrownException, PlantIsNotPlantedException, FlowerNotFoundException{
        if(this.flowers.contains(flower)){
            flower.harvest();
            ++this.size;
        } else {
            throw new FlowerNotFoundException();
        }
    }

    
    /** 
     * @param flowerType the type of the flower (Tulip or Rose)
     * @return int the number or flower seeds (not planted flowers)
     */
    public int getFlowerSeedsCount(Class<? extends Flower> flowerType){
        int flowersCount = 0;
        for(Flower flower: this.flowers){
            if(flower.getClass().equals(flowerType) && !flower.getIsPlanted()){
                flowersCount ++;
            }
        }
        return flowersCount;
    }

    
    /** 
     * @return int the number of planted flowers (but not harvested)
     */
    public int getPlantedFlowersCount(){
        int flowersCount = 0;
        for(Flower flower: this.flowers){
            if(flower.getIsPlanted()){
                flowersCount ++;
            }
        }
        return flowersCount;
    }

    
    /** 
     * @param flowerType the type of the flower (Tulip or Rose)
     * @return int the number of harvested flowers
     */
    public int getHarvestedFlowersCount(Class<? extends Flower> flowerType){
        int flowersCount = 0;
        for(Flower flower: this.flowers){
            if(flower.getClass().equals(flowerType) && flower.getIsHarvested()){
                flowersCount ++;
            }
        }
        return flowersCount;
    }

    
    /** 
     * @param flowerType the type of the flower (Tulip or Rose)
     * @param flowersToRemove the number of flowers to remove
     * @return ArrayList<Flower> the list of removed Flowers
     * @throws NotEnoughFlowerException
     */
    public ArrayList<Flower> removeHarvestedFlowers(Class<? extends Flower> flowerType, int flowersToRemove) throws NotEnoughFlowerException{
        if(getHarvestedFlowersCount(flowerType) < flowersToRemove){
            throw new NotEnoughFlowerException();
        }
        int flowersLeftToRemove = flowersToRemove;
        ArrayList<Flower> removedFlowers = new ArrayList<>();
        for(Flower flower: this.flowers){
            if (flower.getClass().equals(flowerType) && flower.getIsHarvested()){
                removedFlowers.add(flower);
                this.flowers.remove(flower);
                flowersLeftToRemove--;
                if(flowersLeftToRemove == 0){
                    break;
                }
            }
        }
        return removedFlowers;
    }

    
    /** 
     * @return ArrayList<Flower> the list of all flower seeds (not planted Flowers)
     */
    public ArrayList<Flower> getFlowerSeeds(){
        ArrayList<Flower> flowerSeeds = new ArrayList<>();
        for(Flower flower: this.flowers){
            if(flower.getIsPlanted() == false){
                flowerSeeds.add(flower);
            }
        }
        return flowerSeeds;
    }

    
    /** 
     * @return ArrayList<Flower> the list of all planted flowers (but not harvested)
     */
    public ArrayList<Flower> getPlantedFlowers(){
        ArrayList<Flower> plantedFlowers = new ArrayList<>();
        for(Flower flower: this.flowers){
             if (flower.getIsPlanted() == true && flower.isHarvested == false){
                plantedFlowers.add(flower);
            }
        }
        return plantedFlowers;
    }

    
    /** 
     * @return String the information about number of flowers seeds
     */
    public String getSeedsInfo(){
        StringBuilder info = new StringBuilder();
        info.append("* Tulip: ");
        info.append(this.getFlowerSeedsCount(Tulip.class));
        info.append("\n* Rose: ");
        info.append(this.getFlowerSeedsCount(Rose.class));
        return info.toString();
    }

    
    /** 
     * @return String the information about planted (but not harvested) flowers, includes id and state of each flower
     */
    public String getPlantedFlowersInfo(){
        StringBuilder info = new StringBuilder();
        String prefix = "* ";
        for(Flower flower: this.getPlantedFlowers()){
            info.append(prefix);
            info.append(flower);
            info.append(": ");
            info.append(flower.getCurrentStateInfo());
            prefix = "\n* ";
        }
        return info.toString();
    }

    
    /** 
     * @return String the information about number of harvested flowes
     */
    public String getHarvestedFlowersInfo(){
        StringBuilder info = new StringBuilder();
        info.append("* Tulip: ");
        info.append(this.getHarvestedFlowersCount(Tulip.class));
        info.append("\n* Rose: ");
        info.append(this.getHarvestedFlowersCount(Rose.class));
        return info.toString();
    }

    
    /** 
     * @return String the information about all flowers, remaining field size, water and soil resources
     */
    public String getCurrentStateInfo(){
        StringBuilder info = new StringBuilder();

        info.append("\nFlowers' seeds:\n");
        info.append(this.getSeedsInfo());

        info.append("\n\nFlowers:\n");
        info.append(this.getPlantedFlowersInfo());

        info.append("\n\nHarvested flowers:\n");
        info.append(this.getHarvestedFlowersInfo());

        info.append("\n\nWater resources: ");
        info.append(this.waterResources);

        info.append("\n\nSoil resources: ");
        info.append(this.soilResources);

        info.append("\n\nField size: ");
        info.append(this.size);

        return info.toString();
    }

}
