package hu.unideb.inf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MainApp extends Application {


    final static String User = "root";
    final static String Db_Schema = "jdbc:mysql://localhost/";
    final static String DB_Url = "jdbc:mysql://localhost/todo_office";
    public final static String Password = "abc123";

    public static String setPassword(){
        return Password;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            String schemaName = "todo_office";
            Connection conn = DriverManager.getConnection(Db_Schema, User, Password);
            Statement stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(schemaName, null, null, null);
            System.out.println(tables.next());

            List<String> schemak = new ArrayList<>();
            ResultSet rs = conn.getMetaData().getCatalogs();

            while (rs.next()) {
                schemak.add(rs.getString("TABLE_CAT"));
            }


            if (!schemak.contains(schemaName)) {
                String sql = "CREATE SCHEMA " + "todo_office";
                stmt.executeUpdate(sql);

            } else {
                System.out.println("letezik");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            Connection conn = DriverManager.getConnection(DB_Url, User, Password);
            Statement stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables("todo_office", null, "users", null);


            if (tables.next()) {
                System.out.println("letezik");
            } else {
                System.out.println("nem létezik");
                String sql = "CREATE TABLE `todo_office`.`users`" +
                        "(id INTEGER not NULL AUTO_INCREMENT, " +
                        " username VARCHAR(45), " +
                        " password VARCHAR(45), " +
                        " company_id INTEGER, " +
                        " PRIMARY KEY ( id ))";

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
                System.out.println("created");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }


        try {

            Connection conn = DriverManager.getConnection(DB_Url, User, Password);
            Statement stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables("todo_office", null, "bridge", null);


            if (tables.next()) {
                System.out.println("letezik");
            } else {
                System.out.println("nem létezik");
                String sql = "CREATE TABLE `todo_office`.`bridge`" +
                        "(company_id INTEGER not NULL , " +
                        " PRIMARY KEY ( company_id ))";

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
                System.out.println("created");

                String sql2 = "INSERT INTO bridge VALUES (1),(2),(3)";

                stmt.executeUpdate(sql2);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }


        try {

            Connection conn = DriverManager.getConnection(DB_Url, User, Password);
            Statement stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables("todo_office", null, "company", null);


            if (tables.next()) {
                System.out.println("letezik");
            } else {
                System.out.println("nem létezik");
                String sql = "CREATE TABLE `todo_office`.`company`" +
                        "(name VARCHAR(45) not NULL , " +
                        " company_id INTEGER not NULL, " +
                        " address VARCHAR(45), " +
                        " PRIMARY KEY (name))";
                stmt.executeUpdate(sql);
                System.out.println("Created table in given database...");
                System.out.println("created");

                String sql2 = "INSERT INTO company(name, company_id, address ) VALUES ('Pécsi Tyúk',3,'Pécs, Komolyan Igazi utca 7'), ('Pesti Pipi',1,'Budapest Igazi utca 7'),('Szegedi Csirke',2,'Szeged Tányleg Igazi utca 7')";

                stmt.executeUpdate(sql2);

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }


        Parent root = FXMLLoader.load(getClass().getResource("/fxml/simple.fxml"));
        primaryStage.setTitle("Todo");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
