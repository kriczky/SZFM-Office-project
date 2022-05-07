package org.example;

import com.mysql.cj.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddTasks implements Initializable {
    @FXML
    public Button freshName;

    @FXML
    public ComboBox names;

    @FXML
    public ListView<String> listView;

    @FXML
    public TextField textField;

    @FXML
    public Button save;

    List<String> lista = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        freshName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_office", "root", MainApp.Password);
                    ResultSet res = con.createStatement().executeQuery("select username from users");
                    ObservableList data = FXCollections.observableArrayList();

                    if(names.getValue()!=null){
                        System.out.println("most van");
                    }else {
                        while (res.next()) {
                            data.add(new String(res.getString(1)));
                        }
                        names.setItems(data);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        names.getSelectionModel().selectedItemProperty().addListener((observa, oldValue, newValue) -> {
            lista.add(newValue.toString());
            listView.getItems().add(newValue.toString());
            for (int i = 0; i < lista.size(); i++) {
                System.out.println(lista.get(i));
            }

        });


        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<Person> dolgozok = DBUtils.getPersons();
                String nevek = String.join("#", lista);
                String[] splitter = nevek.split("#");
                List<String> cegekListaja = new ArrayList<>();

                for (int i = 0; i < dolgozok.size(); i++) {
                    for (String dolgozo : splitter) {
                        if (dolgozo.equals(dolgozok.get(i).username)) {
                            cegekListaja.add(dolgozok.get(i).company_id);
                        }
                    }
                }


                DBUtils.feladatok.add(new Feladatok(String.join(",", lista), textField.getText(), String.join(",", cegekListaja)));


                try {
                    DBUtils.changeScene(event, "/fxml/logged-in.fxml", "Welcome", null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        });

    }


}