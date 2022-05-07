package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    public Button loadNames;

    @FXML
    private Button button_sing_up;

    @FXML
    private Button button_login;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_password1;

    @FXML
    public ComboBox cegCombox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_sing_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && tf_password.getText().equals(tf_password1.getText())) {

                    if (cegCombox.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Kérem válasszon egy céget! Ha a legördülő menüben nem lát \n cégeket akkor a " + '"' + "Cégek frissítése" + '"' + " gombra kattintson!");
                        alert.show();
                    } else {
                        DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), cegCombox.getValue().toString());
                    }
                } else {
                    if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_password.getText().equals(tf_password1.getText()) || tf_password.getText().isEmpty() || tf_password1.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Nem egyezzik meg a két jelszó");
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Minden mezőt töltsön ki");
                        alert.show();
                    }

                }
            }
        });

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBUtils.changeScene(event, "/fxml/simple.fxml", "Log in", null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        loadNames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_office", "root", MainApp.Password);
                    ResultSet res = con.createStatement().executeQuery("select name from company");
                    ObservableList data = FXCollections.observableArrayList();
                    while (res.next()) {
                        System.out.println(res.getString(1));
                        data.add(new String(res.getString(1)));
                    }
                    cegCombox.setItems(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }


}
