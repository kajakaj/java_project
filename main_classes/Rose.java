package main_classes;

public class Rose extends Flower implements ICanBeSoiled{
    protected static int neededHydration = 5;
    protected static int neededSoil = 2;
    protected int currentSoil;

    
    /** 
     * @return int the value of current soil
     */
    public int getCurrentSoil(){
        return currentSoil;
    }

    
    /** 
     * @return int the value of needed hydration
     */
    @Override
    public int getNeededHydration(){
        return neededHydration;
    }

    
    /** 
     * @param soil the new value of current soil
     * @throws NegativeSizeValueException
     */
    public void setCurrentSoil(int soil) throws NegativeSizeValueException{
        if(soil >= 0){
            this.currentSoil = soil;
        } else {
            throw new NegativeSizeValueException();
        }
        
    }

    
    /** 
     * @return boolean whether or not rose was watered and soiled enough number of times
     */
    @Override
    public boolean isGrown(){
        return this.currentHydration == Rose.neededHydration && this.currentSoil == Rose.neededSoil;
    }

    
    /** 
     * @throws PlantIsNotPlantedException
     */
    public void soil() throws PlantIsNotPlantedException{
        if(this.isPlanted == true){
            this.currentSoil = Math.min(this.currentSoil + 1, neededSoil);
        } else {
            throw new PlantIsNotPlantedException(this);
        }
        
    }
    
    
    /** 
     * @return String the name of the class and unique id
     */
    @Override
    public String toString(){
        return "Rose (id: " + this.id + ")";
    }

    
    /** 
     * @return String the information about current state of the rose
     */
    @Override
    public String getCurrentStateInfo(){
        if(this.isPlanted == false){
            return "is not planted yet";
        } else if(this.isHarvested == true){
            return "is harvested";
        } else {
            int diffHydration = Rose.neededHydration - this.currentHydration;
            int diffSoil = Rose.neededSoil - this.currentSoil;
            return "needs to be water " + diffHydration + " more time(s)"
                 + "needs to be soil " + diffSoil + " more time(s)";
        }
    }

}