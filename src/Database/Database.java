package Database;

import DateStats.DateStats;
import Road.Road;
import Sensor.Sensor;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/** Classe di accesso al DB SQLite
 */
public class Database {

    public static final String DATABASE_URL="jdbc:sqlite:mysqlitedb.sqlite";
    private Connection conn = null;
    private static Database instance = null;

    /**
     * Si stabilisce una connessione al database
     */
    private Database(){
        try{
            conn = DriverManager.getConnection(DATABASE_URL);
            Statement statement = conn.createStatement();
            //Attivo le chiavi esterne
            statement.executeUpdate("PRAGMA foreign_keys = ON;");
            int tableNum = checkExistingTables();
            if(tableNum == 0) {
                createRoadTable();
                createSensorTable();
                createStatusTable();
                createReportTable();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Si controlla il numero delle tabelle nel sistema,
     * utile per stabilire se creare o meno le tabelle necessarie.
     */
    public int checkExistingTables(){
        int num = 0;
        String sqlQuery = "SELECT COUNT(*) AS num FROM sqlite_master WHERE type = 'table'";
        try(Statement s = conn.createStatement()) {
            ResultSet rs = s.executeQuery(sqlQuery);
            if(rs.next()){
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static Database getInstance(){
        if(instance == null) {
            instance = new Database();
            return instance;
        }
        else
            return instance;
    }

    public Connection getConnection() {
        return conn;
    }

    /**
     * Creazione tabella strada
     */
    public void createRoadTable() {
        String sqlCreate = "CREATE TABLE Road(\n" +
                "                     address VARCHAR,\n" +
                "                     roadLength REAL,\n" +
                "                     pollutionLimit REAL,\n" +
                "                     tempLimit REAL ,\n" +
                "                     carLimit INTEGER,\n" +
                "                     PRIMARY KEY (address));";
        try (Statement s = conn.createStatement()) {
            s.execute(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creazione tabella sensore
     */
    public void createSensorTable() {
        String sqlCreate = "CREATE TABLE Sensor(\n" +
                "                       sensorID VARCHAR,\n" +
                "                       address VARCHAR,\n" +
                "                       pollution REAL,\n" +
                "                       temperature REAL,\n" +
                "                       carCounter INTEGER,\n" +
                "                       PRIMARY KEY (sensorID),\n" +
                "                       FOREIGN KEY (address) REFERENCES Road(address) ON DELETE CASCADE);";
        try (Statement s = conn.createStatement()) {
            s.execute(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creazione tabella status dei sensori da monitorare nel tempo
     */
    public void createStatusTable() {
        String sqlCreate = "CREATE TABLE Status(\n" +
                "                       dateUpdate TEXT,\n" +
                "                       sensID VARCHAR,\n" +
                "                       pollution REAL,\n" +
                "                       temperature REAL,\n" +
                "                       carCounter INTEGER,\n" +
                "                       PRIMARY KEY (dateUpdate,sensID),\n" +
                "                       FOREIGN KEY (sensID) REFERENCES  Sensor(sensorID) ON DELETE CASCADE);";
        try (Statement s = conn.createStatement()) {
            s.execute(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creazione tabella report, che contiene le informazioni delle segnalazioni
     */
    public void createReportTable() {
        String sqlCreate = "CREATE TABLE Report(\n" +
                "    reportDate TEXT,\n" +
                "    carPlate VARCHAR,\n" +
                "    roadAddress VARCHAR,\n" +
                "    PRIMARY KEY (carPlate,reportDate));";
        try (Statement s = conn.createStatement()) {
            s.execute(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
