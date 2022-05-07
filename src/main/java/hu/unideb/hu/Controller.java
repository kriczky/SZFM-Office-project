package hu.unideb.hu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    public TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private Button button_sing_up;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.logInUser(event, tf_username.getText(), tf_password.getText());
            }
        });

        button_sing_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBUtils.changeScene(event, "/fxml/sign-up.fxml", "Sign up!", null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public static void main(String[] args) {

        Main.main(args);
    }


}
