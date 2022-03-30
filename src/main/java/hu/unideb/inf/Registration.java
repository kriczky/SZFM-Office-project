package hu.unideb.inf;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class Registration implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_sing_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && tf_password.getText().equals(tf_password1.getText())){
                    DBUtils.signUpUser(event,tf_username.getText(),tf_password.getText());
                }else {
                    if(!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_password.getText().equals(tf_password1.getText()) || tf_password.getText().isEmpty() || tf_password1.getText().isEmpty()){
                        System.out.println("Nem egyezzik meg a két jelszó");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Nem egyezzik meg a két jelszó");
                        alert.show();
                    }else {
                        System.out.println("Please fill in");
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
                DBUtils.changeScene(event,"/fxml/logIn.fxml","Log in", null);
            }
        });
    }
}
