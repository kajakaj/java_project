package main_classes;

public class Tulip extends Flower{
    protected static int neededHydration = 3;

    
    /** 
     * @return int the value of needed hydration
     */
    @Override
    public int getNeededHydration(){
        return neededHydration;
    }

    
    /** 
     * @return boolean whether or not tulip was watered enough number of times
     */
    @Override
    public boolean isGrown(){
        return this.currentHydration == Tulip.neededHydration;
    }
    
    
    /** 
     * @return String the name of the class and unique id
     */
    @Override
    public String toString(){
        return "Tulip (id: " + this.id + ")";
    }

    
    /** 
     * @return String the information about current state of the tulip
     */
    @Override
    public String getCurrentStateInfo(){
        if(this.isPlanted == false){
            return "is not planted yet";
        } else if(this.isHarvested == true){
            return "is harvested";
        } else {
            int diffHydration = Tulip.neededHydration - this.currentHydration;
            return "needs to be water " + diffHydration + " more time(s)";
        }
    }
}