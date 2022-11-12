package database_access_classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main_classes.Flower;
import main_classes.FlowerFarm;

public class FlowerFarmDao {
    public FlowerFarm extract(ResultSet result) {
        try{
            String farmerName = result.getString("farmerName");
            int size = result.getInt("field");
            int waterResources = result.getInt("waterResources");
            int soilResources = result.getInt("soilResources");
            ArrayList<Flower> flowers = new FlowerDao().getAllFlowers(farmerName);

            return new FlowerFarm(size, waterResources, soilResources, flowers);
        } catch (SQLException e){
            return null;
        }      
    }

    public FlowerFarm get(String farmersName) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM farms WHERE farmerName = ?");
            prepStat.setString(1, farmersName);
            ResultSet result = prepStat.executeQuery();
            
            if(result.next()){
                return extract(result);
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public boolean insert(FlowerFarm farm, String farmersName) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement(
                    "insert into farms values (?, ?, ?, ?);");
            prepStat.setString(1, farmersName);
            prepStat.setInt(2, farm.getWaterResources());
            prepStat.setInt(3, farm.getSoilResources());
            prepStat.setInt(4, farm.getSize());
            prepStat.execute();
            
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean update(FlowerFarm farm, String farmerName) {
        return updateResources(farm, farmerName) && updateFlowers(farmerName, farm.getFlowers());
    }

    public boolean updateResources(FlowerFarm farm, String farmerName) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement(
                "UPDATE farms SET waterResources = ?, soilResources = ?, field = ? " +
                "WHERE farmerName = ?");
            prepStat.setInt(1, farm.getWaterResources());
            prepStat.setInt(2, farm.getSoilResources());
            prepStat.setInt(3, farm.getSize());
            prepStat.setString(4, farmerName);
            prepStat.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean updateFlowers(String farmerName, ArrayList<Flower> flowers) {
        FlowerDao fd = new FlowerDao();
        for(Flower flower: flowers){
            if(fd.update(flower) == false){
                return false;
            }
        }
        return true;
    }

}