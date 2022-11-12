package main_classes;

public abstract class Flower implements IPlant{
    protected int id;
    protected int currentHydration;
    protected boolean isPlanted;
    protected boolean isHarvested;

    public Flower(){
        currentHydration = 0;
        isPlanted = false;
        isHarvested = false;
    }

    
    /** 
     * @return int the current hydration
     */
    public int getCurrentHydration(){
        return this.currentHydration;
    }

    abstract public int getNeededHydration();

    
    /** 
     * @return int the flower id
     */
    public int getId(){
        return this.id;
    }

    
    /** 
     * @return boolean the information whether flower is planted (but not harvested) or not
     */
    public boolean getIsPlanted(){
        return this.isPlanted;
    }

    
    /** 
     * @return boolean the information whether flower is harvested or not
     */
    public boolean getIsHarvested(){
        return this.isHarvested;
    }

    
    /** 
     * @param hydration the new value of hydration
     * @throws NegativeHydrationValueException
     */
    public void setCurrentHydration(int hydration) throws NegativeHydrationValueException{
        if(hydration >= 0){
            this.currentHydration = hydration;
        } else {
            throw new NegativeHydrationValueException();
        }
        
    }

    
    /** 
     * @param isPlanted the new information whether flower is planted or not
     */
    public void setIsPlanted(boolean isPlanted){
        this.isPlanted = isPlanted;
    }

    
    /** 
     * @param isHarvested the new information whether flower is harvested or not
     */
    public void setIsHarvested(boolean isHarvested){
        this.isHarvested = isHarvested;
    }

    
    /** 
     * @param id the new flower id
     */
    public void setId(int id){
        this.id = id;
    }

    
    /** 
     * @throws PlantIsNotPlantedException
     */
    public void water() throws PlantIsNotPlantedException{
        if(this.isPlanted == true){
            this.currentHydration = Math.min(this.currentHydration + 1, getNeededHydration());
        } else {
            throw new PlantIsNotPlantedException(this);
        }
        
    }
    
    public void plant(){
        this.isPlanted = true;
    }

    
    /** 
     * @throws PlantIsNotPlantedException
     * @throws PlantIsNotGrownException
     */
    public void harvest() throws PlantIsNotPlantedException, PlantIsNotGrownException{
        if(this.isPlanted == true){
            if(this.isGrown()){
                this.isHarvested = true;
            } else {
                throw new PlantIsNotGrownException(this);
            }
        } else {
            throw new PlantIsNotPlantedException(this);
        }
        
    }

    abstract public boolean isGrown();

    abstract String getCurrentStateInfo();

}
