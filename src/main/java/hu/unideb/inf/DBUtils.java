package hu.unideb.inf;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {



    public static List<Person> felhasznalok = new ArrayList<>();
    public static List<Feladatok> feladatok = new ArrayList<>();


    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) throws InterruptedException {
        Parent root = null;


        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggdInController loggdInController = loader.getController();
                loggdInController.textfieldFrissit(event);
                loggdInController.setUserInformation(username);
                loggdInController.felhasznalokKiirasa(felhasznalok);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }


    public static List<Person> getPersons() {
        return felhasznalok;
    }

    public static List<Feladatok> getFeladatok() {
        return feladatok;
    }

    public static void addFeladatok(Feladatok tasks) {
        feladatok.add(tasks);
    }

    public static void clearList() {
        feladatok.clear();
    }


    public static void signUpUser(ActionEvent event, String username, String password, String comboBox) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;


        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_office", "root", MainApp.Password);
            psCheckUserExist = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExist.setString(1, username);
            resultSet = psCheckUserExist.executeQuery();


            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A felhasználónév már foglalt!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, company_id) VALUES(?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);

                if (comboBox.equals("Pesti Pipi")) {
                    psInsert.setString(3, "1");
                    felhasznalok.add(new Person(felhasznalok.size() + 1, username, "Pesti Pipi"));

                } else if (comboBox.equals("Szegedi Csirke")) {
                    psInsert.setString(3, "2");
                    felhasznalok.add(new Person(felhasznalok.size() + 1, username, "Szegedi Csirke"));
                } else if (comboBox.equals("Pécsi Tyúk")) {

                    felhasznalok.add(new Person(felhasznalok.size() + 1, username, "Pécsi Tyúk"));
                    psInsert.setString(3, "3");

                }
                psInsert.executeUpdate();

                changeScene(event, "/fxml/logged-in.fxml", "welcome", null);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExist != null) {
                try {
                    psCheckUserExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_office", "root", MainApp.Password);
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ? ");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A felhasználó név nem található!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrivePassword = resultSet.getString("password");

                    if (retrivePassword.equals(password)) {
                        changeScene(event, "/fxml/logged-in.fxml", "Welcome", null);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("A felhasználó név vagy a jelszó nem egyezik!");
                        alert.show();
                    }
                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }


        connection = null;
        preparedStatement = null;
        resultSet = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_office", "root", MainApp.Password);
            preparedStatement = connection.prepareStatement("select id,username,company_id from users");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                if (resultSet.getString("company_id").equals("1")) {
                    felhasznalok.add(new Person(Integer.parseInt(resultSet.getString("id")), resultSet.getString("username"), "Pesti Pipi"));
                } else if (resultSet.getString("company_id").equals("2")) {
                    felhasznalok.add(new Person(Integer.parseInt(resultSet.getString("id")), resultSet.getString("username"), "Szegedi Csirke"));
                } else {
                    felhasznalok.add(new Person(Integer.parseInt(resultSet.getString("id")), resultSet.getString("username"), "Pécsi Tyúk"));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
