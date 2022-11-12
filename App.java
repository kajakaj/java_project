import java.util.ArrayList;
import java.util.Scanner;

import database_access_classes.FarmerDao;
import database_access_classes.FlowerDao;
import database_access_classes.FlowersFarmDatabase;
import main_classes.Farmer;
import main_classes.Flower;
import main_classes.FlowerFarm;
import main_classes.FlowerNotFoundException;
import main_classes.ICanBeSoiled;
import main_classes.NegativeMoneyValueException;
import main_classes.NegativeQuantityValueEsception;
import main_classes.NegativeSizeValueException;
import main_classes.NegativeSoilResourcesValueException;
import main_classes.NegativeWaterResourcesValueException;
import main_classes.NotEnoughFieldException;
import main_classes.NotEnoughFlowerException;
import main_classes.NotEnoughMoneyException;
import main_classes.NotEnoughSoilResourcesException;
import main_classes.NotEnoughWaterResourcesException;
import main_classes.PlantIsNotGrownException;
import main_classes.PlantIsNotPlantedException;
import main_classes.ProductBuy;
import main_classes.ProductSell;
import main_classes.Rose;
import main_classes.Tulip;

public class App {
    static final int INITIAL_MONEY = 200;
    static final int INITIAL_WATER_RESOURCES = 10;
    static final int INITIAL_SOIL_RESOURCES = 0;
    static final int INITIAL_FARM_SIZE = 3;

    public static void showResources(Farmer farmer){
        System.out.println(farmer.getFlowerFarm().getCurrentStateInfo());
        System.out.println("\nMoney: " + farmer.getMoney());
    }

    public static void plantFlower(Farmer farmer){
        FlowerFarm farm = farmer.getFlowerFarm();  
        System.out.println("Enter name of the flower you want to plant:");
        System.out.println("Flowers' seeds:");
        System.out.println(farmer.getFlowerFarm().getSeedsInfo());
        
        Scanner sc = new Scanner(System.in);
        String msgFromUser = sc.nextLine();

        Class<? extends Flower> type;
        switch(msgFromUser.strip().toLowerCase()){
            case "tulip":
                type = Tulip.class;
                break;
            case "rose":
                type = Rose.class;
                break;
            default:
                System.out.println("Incorrect flower type name.");
                return;
        }

        try{
            farm.plantFlower(type);
            new FarmerDao().update(farmer);
            System.out.println("Flower planted successfully.");
        } catch (NotEnoughFieldException e) {
            System.out.println("You do not have enough field to plant a flower.");
        } catch (FlowerNotFoundException e) {
            System.out.println("You do not have this flower type seeds.");
        }
    }

    public static void waterFlower(Farmer farmer){
        FlowerFarm farm = farmer.getFlowerFarm();  
        System.out.println("Enter id of the flower you want to water:");
        System.out.println(farm.getPlantedFlowersInfo());
        
        Scanner sc = new Scanner(System.in);
        String msgFromUser = sc.nextLine();

        try{
            int seedId = Integer.parseInt(msgFromUser);
            Flower flower = farm.getFlower(seedId);
            farm.waterFlower(flower);
            new FarmerDao().update(farmer);
            System.out.println("Flower watered successfully.");
        } catch (FlowerNotFoundException | NumberFormatException | PlantIsNotPlantedException e) {
            System.out.println("There is no planted flower with this id.");
        } catch (NotEnoughWaterResourcesException e) {
            System.out.println("You do not have enough water resources to water this flower.");
        }
    }

    public static <T extends ICanBeSoiled> void soilFlower(Farmer farmer){
        FlowerFarm farm = farmer.getFlowerFarm();  
        System.out.println("Enter id of the flower you want to soil:");
        System.out.println(farm.getPlantedFlowersInfo());
        
        Scanner sc = new Scanner(System.in);
        String msgFromUser = sc.nextLine();

        try{
            int seedId = Integer.parseInt(msgFromUser);
            T flower = (T) farm.getFlower(seedId);
            farm.soilFlower(flower);
            new FarmerDao().update(farmer);
            System.out.println("Flower soiled successfully.");
        } catch (FlowerNotFoundException | NumberFormatException | PlantIsNotPlantedException | ClassCastException e) {
            System.out.println("There is no planted flower that can be soiled with this id.");
        } catch (NotEnoughSoilResourcesException e) {
            System.out.println("You do not have enough soil resources to water this flower.");
        }
    }

    public static void harvestFlower(Farmer farmer){
        FlowerFarm farm = farmer.getFlowerFarm();  
        System.out.println("Enter id of the flower you want to harvest:");
        System.out.println(farm.getPlantedFlowersInfo());
        
        Scanner sc = new Scanner(System.in);
        String msgFromUser = sc.nextLine();

        try{
            int seedId = Integer.parseInt(msgFromUser);
            Flower flower = farm.getFlower(seedId);
            farm.harvestFlower(flower);
            new FarmerDao().update(farmer);
            System.out.println("Flower harvested successfully.");
        } catch (FlowerNotFoundException | NumberFormatException | PlantIsNotPlantedException | PlantIsNotGrownException e) {
            System.out.println("There is no flower ready to be harvested with this id.");
        }
    }

    public static void buyProducts(Farmer farmer) {
        System.out.println("\nProducts:");
        for(ProductBuy pBuy: ProductBuy.values()){
            System.out.println("* " + pBuy.name() + ": " + pBuy.getPrice());
        }

        System.out.println("Enter name of the product you want to buy:");
        Scanner sc = new Scanner(System.in);
        String msgFromUserName = sc.nextLine().toUpperCase();

        System.out.println("Enter quantity you want to buy:");
        Scanner sc2 = new Scanner(System.in);
        String msgFromUserQuantity = sc2.nextLine();

        try{
            ProductBuy pBuy = ProductBuy.valueOf(msgFromUserName);
            int quantity = Integer.parseInt(msgFromUserQuantity);
            
            ArrayList<Flower> bougthFlowers = farmer.buyProducts(pBuy, quantity);
            if(bougthFlowers != null){
                for(Flower flower: bougthFlowers){
                    new FlowerDao().insert(flower, farmer.getName());
                }
            }
            new FarmerDao().update(farmer);
            
            System.out.println("You bougth product(s) successfully.");
        } catch (NegativeQuantityValueEsception | NumberFormatException | NegativeSizeValueException | NegativeWaterResourcesValueException | NegativeSoilResourcesValueException e) {
            System.out.println("Quantity must be number bigger than 0.");
        } catch (IllegalArgumentException e) {
            System.out.println("There is no product with that name.");
        } catch (NotEnoughMoneyException e) {
            System.out.println("You do not have enough money.");
        }           
    }

    public static void sellProducts(Farmer farmer){
        System.out.println("\nProducts:");
        for(ProductSell pSell: ProductSell.values()){
            System.out.println("* " + pSell.name() + ": " + pSell.getPrice());
        }

        System.out.println("\nYour harvested flowers:");
        System.out.println(farmer.getFlowerFarm().getHarvestedFlowersInfo());

        System.out.println("Enter name of the product you want to sell:");
        Scanner sc = new Scanner(System.in);
        String msgFromUserName = sc.nextLine().toUpperCase();

        System.out.println("Enter quantity you want to sell:");
        Scanner sc2 = new Scanner(System.in);
        String msgFromUserQuantity = sc2.nextLine();

        try{
            ProductSell pSell = ProductSell.valueOf(msgFromUserName);
            int quantity = Integer.parseInt(msgFromUserQuantity);
            
            ArrayList<Flower> soldFlowers = farmer.sellProducts(pSell, quantity);
            if(soldFlowers != null){
                for(Flower flower: soldFlowers){
                    new FlowerDao().delete(flower);
                }
            }
            new FarmerDao().update(farmer);
            
            
            System.out.println("You sold product(s) successfully.");
        } catch (NumberFormatException | NegativeMoneyValueException | NegativeQuantityValueEsception e) {
            System.out.println("Quantity must be number bigger than 0.");
        } catch (NotEnoughFlowerException e) {
            System.out.println("You do not have enough flowers.");
        } catch (IllegalArgumentException e) {
            System.out.println("There is no product with that name.");
        }
    }

    public static void farmerPanel(Farmer farmer){
        String menu = "\n(1) Show farm\n"
                    + "(2) Plant flower\n"
                    + "(3) Water flower\n"
                    + "(4) Soil flower\n"
                    + "(5) Harvest flower\n"
                    + "(6) Buy products\n"
                    + "(7) Sell products\n"
                    + "(0) Exit";

        System.out.println(menu);

        Scanner sc = new Scanner(System.in);
        String msgFromUser = sc.nextLine();

        switch(msgFromUser.strip().toLowerCase()){
            case "0":
                System.out.println("You are back in the main menu.");
                break;
            case "1":
                showResources(farmer);
                farmerPanel(farmer);
                break;
            case "2":
                plantFlower(farmer);
                farmerPanel(farmer);
                break;
            case "3":
                waterFlower(farmer);
                farmerPanel(farmer);
                break;
            case "4":
                soilFlower(farmer);
                farmerPanel(farmer);
                break;
            case "5":
                harvestFlower(farmer);
                farmerPanel(farmer);
                break;
            case "6":
                buyProducts(farmer);
                farmerPanel(farmer);
                break;
            case "7":
                sellProducts(farmer);
                farmerPanel(farmer);
                break;
            default:
                System.out.println("Input not recognized.");
                farmerPanel(farmer);
        }

    }
    
    public static void chooseFarmer(){
        FarmerDao fd = new FarmerDao();

        System.out.println("\nTo play enter name of one of the following farmers:");
        for(Farmer farmer: fd.getAllFarmers()){
            System.out.println("* " + farmer);
        }

        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine().strip().toLowerCase();

        Farmer farmer = fd.get(name);
        if(farmer != null) {
            System.out.println("\nHello on " + farmer.getName() + "'s farm!");
            farmerPanel(farmer);
        } else {
            System.out.println("There is no farmer with this name.");
            mainPanel();
        }

    }

    public static void addFarmer(){
        FarmerDao fd = new FarmerDao();

        System.out.println("\nEnter name of the new farmer:");

        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine().strip().toLowerCase();

        FlowerFarm newFarm = new FlowerFarm(INITIAL_FARM_SIZE, INITIAL_WATER_RESOURCES, INITIAL_SOIL_RESOURCES, null);
        Farmer newFarmer = new Farmer(name, INITIAL_MONEY, newFarm);
        if(fd.insert(newFarmer)){
            System.out.println("Farmer added successfully.");
        } else {
            System.out.println("This name is already taken.");
        }

    }

    public static void deleteFarmer(){
        FarmerDao fd = new FarmerDao();

        System.out.println("\nEnter name of one of the following farmers that you want to delete:");
        for(Farmer farmer: fd.getAllFarmers()){
            System.out.println(farmer);
        }

        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine().strip().toLowerCase();

        if(fd.delete(name)) {
            System.out.println("Farmer deleted successfully.");
        } else {
            System.out.println("There is no farmer with this name.");
        }

    }

    public static void mainPanel(){
        String menu = "\n(1) Choose farmer\n"
                    + "(2) Add farmer\n"
                    + "(3) Delete farmer\n"
                    + "(0) Exit";

        System.out.println(menu);

        Scanner sc = new Scanner(System.in);  
        String msgFromUser = sc.nextLine();

            switch(msgFromUser.strip().toLowerCase()){
                case "0":
                    System.out.println("Bye bye!");
                    break;
                case "1":
                    chooseFarmer();
                    mainPanel();
                    break;
                case "2":
                    addFarmer();
                    mainPanel();
                    break;
                case "3":
                    deleteFarmer();
                    mainPanel();
                    break;
                default:
                    System.out.println("Input not recognized.");
                    mainPanel();
            }
    }

    public static void main(String[] args){

        new FlowersFarmDatabase();
       
        System.out.println("Hello on flowers' farm!");
        mainPanel();
    }
}
