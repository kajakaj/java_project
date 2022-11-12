package database_access_classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main_classes.Farmer;
import main_classes.FlowerFarm;

public class FarmerDao {
    public Farmer extract(ResultSet result) {
        String name;
        try {
            name = result.getString("name");
            int money = result.getInt("money");
            FlowerFarm farm = new FlowerFarmDao().get(name);
            return new Farmer(name, money, farm);
        } catch (SQLException e) {
            return null;
        }
    }

    public Farmer get(String name) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM farmers WHERE name = ?");
            prepStat.setString(1, name);
            ResultSet result = prepStat.executeQuery();

            if(result.next()){
                return extract(result);
            }

        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public ArrayList<Farmer> getAllFarmers() {
        Connection conn = FlowersFarmDatabase.getConn();
        ArrayList<Farmer> farmers = new ArrayList<>();
        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM farmers");
            ResultSet result = prepStat.executeQuery();

            while (result.next()) {
                Farmer farmer = extract(result);
                farmers.add(farmer);
            }

        } catch (SQLException e) {
            return null;
        }
        return farmers;

    }

    public boolean insert(Farmer farmer) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement("insert into farmers values (?, ?);");
            prepStat.setString(1, farmer.getName());
            prepStat.setInt(2, farmer.getMoney());
            prepStat.execute();
            new FlowerFarmDao().insert(farmer.getFlowerFarm(), farmer.getName());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean update(Farmer farmer) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement(
                "UPDATE farmers SET money = ? WHERE name = ?");
            prepStat.setInt(1, farmer.getMoney());
            prepStat.setString(2, farmer.getName());
            prepStat.execute();
            new FlowerFarmDao().update(farmer.getFlowerFarm(), farmer.getName());
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean delete(String name) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement(
                    "DELETE FROM farmers WHERE name=?");
            prepStat.setString(1, name);
            
            int deleted = prepStat.executeUpdate();
            System.out.println(deleted);
            if(deleted == 0){
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
}
