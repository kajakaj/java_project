package database_access_classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;


public class FlowersFarmDatabase{
   public static final String DRIVER = "org.sqlite.JDBC";
   public static final String DB_URL = "jdbc:sqlite:flowersFarmDatabase.db";
   private static Connection conn;
   private Statement stat;

   public FlowersFarmDatabase(){
      try {
         Class.forName(DRIVER);
      } catch (ClassNotFoundException e) {
         System.err.println("JDBC driver not found");
      }
      
      try {
         SQLiteConfig config = new SQLiteConfig();
         config.enforceForeignKeys(true);
         conn = DriverManager.getConnection(DB_URL, config.toProperties());
         stat = conn.createStatement();
      } catch (SQLException e) {
         System.err.println("Open connection error");
      }

      createTables();

   }

   public boolean createTables() {
      String createFarmers = "CREATE TABLE IF NOT EXISTS farmers (name varchar(40) PRIMARY KEY, money INTEGER)";
      String createFlowers = "CREATE TABLE IF NOT EXISTS flowers (id INTEGER PRIMARY KEY AUTOINCREMENT, type varchar(40), " +
               "currentHydration INTEGER, currentSoil INTEGER, isPlanted INTEGER, isHarvested INTEGER, farmerName varchar(40), CONSTRAINT fk_farmer FOREIGN KEY (farmerName) REFERENCES farmers(name) on delete cascade)";
      String createFarms = "CREATE TABLE IF NOT EXISTS farms (farmerName varchar(40) PRIMARY KEY, waterResources INTEGER, soilResources INTEGER, " + 
               "field INTEGER, CONSTRAINT fk_farmer FOREIGN KEY (farmerName) REFERENCES farmers(name) on delete cascade)";
      try {
            stat.execute(createFarmers);
            stat.execute(createFlowers);
            stat.execute(createFarms);
      } catch (SQLException e) {
            return false;
      }
      return true;
   }

   public static Connection getConn() {
      return conn;
   }

   public void closeConnection() {
      try {
            conn.close();
      } catch (SQLException e) {
            System.err.println("Close connection error");
      }
   }

}