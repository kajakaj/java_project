package database_access_classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import main_classes.Flower;
import main_classes.NegativeHydrationValueException;
import main_classes.NegativeSizeValueException;
import main_classes.Rose;
import main_classes.Tulip;

public class FlowerDao {
    
    public <T extends Flower> T extract(ResultSet result){
        try {
            if(result.getString("type").equals("tulip")){
                Tulip tulip = new Tulip();
                tulip.setId(result.getInt("id"));
                tulip.setCurrentHydration(result.getInt("currentHydration"));
                tulip.setIsPlanted((result.getInt("isPlanted") == 0) ? false : true);
                tulip.setIsHarvested((result.getInt("isHarvested") == 0) ? false : true);
                return (T)tulip;
            } else if (result.getString("type").equals("rose")){
                Rose rose = new Rose();
                rose.setId(result.getInt("id"));
                rose.setCurrentHydration(result.getInt("currentHydration"));
                rose.setIsPlanted((result.getInt("isPlanted") == 0) ? false : true);
                rose.setIsHarvested((result.getInt("isHarvested") == 0) ? false : true);
                rose.setCurrentSoil(result.getInt("currentSoil"));
                return (T)rose;
            } else {
                return null;
            }
        } catch (SQLException | NegativeHydrationValueException | NegativeSizeValueException e){
            return null;
        }
    }

    public <T extends Flower> T get(int id) throws NegativeHydrationValueException{
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM flowers WHERE id = ?");
            prepStat.setInt(1, id);
            ResultSet result = prepStat.executeQuery();

            if(result.next()){
                return extract(result);
            }

        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public <T extends Flower> boolean insert(T flower, String farmerName) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement(
                    "insert into flowers values (NULL, ?, ?, ?, ?, ?, ?);");
            prepStat.setInt(2, flower.getCurrentHydration());
            prepStat.setInt(4, flower.getIsPlanted() == true ? 1 : 0);
            prepStat.setInt(5, flower.getIsHarvested() == true ? 1 : 0);
            prepStat.setString(6, farmerName);
            if(flower.getClass().equals(Tulip.class)){
                prepStat.setString(1, "tulip");
                prepStat.setNull(3, Types.INTEGER);
            } else {
                prepStat.setString(1, "rose");
                prepStat.setInt(3, ((Rose) flower).getCurrentSoil());
            }
            prepStat.execute();
            ResultSet rs = prepStat.getGeneratedKeys();
            flower.setId(rs.getInt(1));
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public <T extends Flower> boolean update(T flower){
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement(
                "UPDATE flowers SET currentHydration = ?, currentSoil = ?, isPlanted = ?, isHarvested = ? " +
                "WHERE id = ?");
                prepStat.setInt(1, flower.getCurrentHydration());
                prepStat.setInt(3, flower.getIsPlanted() == true ? 1 : 0);
                prepStat.setInt(4, flower.getIsHarvested() == true ? 1 : 0);
                prepStat.setInt(5, flower.getId());
                if(flower.getClass().equals(Tulip.class)){
                    prepStat.setNull(2, Types.INTEGER);
                } else {
                    prepStat.setInt(2, ((Rose) flower).getCurrentSoil());
                }
                prepStat.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public <T extends Flower> boolean delete(T flower) {
        Connection conn = FlowersFarmDatabase.getConn();
        try {
            PreparedStatement prepStat = conn.prepareStatement(
                    "DELETE FROM flowers WHERE id=?");
            prepStat.setInt(1, flower.getId());

            int deleted = prepStat.executeUpdate();
            if(deleted == 0){
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
    }


    public <T extends Flower> ArrayList<Flower> getAllFlowers(String farmerName) {
        Connection conn = FlowersFarmDatabase.getConn();
        ArrayList<Flower> flowers = new ArrayList<>();
        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM flowers WHERE farmerName = ?");
            prepStat.setString(1, farmerName);
            ResultSet result = prepStat.executeQuery();

            while (result.next()) {
                T flower = extract(result);
                flowers.add(flower);
            }

        } catch (SQLException e) {
            return null;
        }
        return flowers;

    }
    
}
