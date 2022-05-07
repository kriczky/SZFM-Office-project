package hu.unideb.inf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoggdInController implements Initializable {

    @FXML
    public Button torles;

    @FXML
    public Button modositas;

    @FXML
    private TableColumn<Feladatok, String> Cegek;

    @FXML
    private TableColumn<Feladatok, String> Emberek;

    @FXML
    private TableColumn<Feladatok, String> Feladat;

    @FXML
    private TableView<Feladatok> table;

    @FXML
    private Button addTask;

    @FXML
    private Button button_logout;

    @FXML
    private Label lable_welcome;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBUtils.changeScene(event, "/fxml/simple.fxml", "Log in!", null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    try {

                        List<Feladatok> feladatok = DBUtils.getFeladatok();
                        File myObj = new File("listak.txt");
                        if (myObj.createNewFile()) {
                            try {

                                FileWriter myWriter = new FileWriter("listak.txt");

                                for (Feladatok feladat : feladatok) {
                                    myWriter.write(feladat.nevek + "#" + feladat.feladat + "#" + feladat.ceg_id + "\n");
                                }


                                myWriter.close();

                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }
                        } else {

                            FileWriter fw = new FileWriter("listak.txt"); //the true will append the new data

                            for (Feladatok feladat : feladatok) {
                                fw.write(feladat.nevek + "#" + feladat.feladat + "#" + feladat.ceg_id + "\n");
                            }

                            fw.close();

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    DBUtils.clearList();
                    DBUtils.changeScene(event, "/fxml/simple.fxml", "Log in!", null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });


        addTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DBUtils.changeScene(event, "/fxml/addTasks.fxml", "Task manager", null);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        torles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (table.getSelectionModel().getFocusedIndex() >= 0) {
                    System.out.println(table.selectionModelProperty().getValue().getFocusedIndex());
                    DBUtils.feladatok.remove(table.getSelectionModel().getFocusedIndex());
                    table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
                    textfieldFrissit(event);
                }

                try {
                    FileWriter myWriter = new FileWriter("listak.txt");
                    myWriter.write("");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        modositas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (table.getItems().size() != 0) {

                    if (table.getSelectionModel().getFocusedIndex() >= 0) {
                        delete(event);
                        addTask.fire();
                    }


                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Nincs módosítható elem!");
                    alert.show();
                }
            }
        });


    }


    public void setUserInformation(String username) {

        lable_welcome.setText("Welcome " + username + "!");
    }


    public void felhasznalokKiirasa(List<Person> felhasznalok) {
        for (int i = 0; i < felhasznalok.size(); i++) {
            System.out.println(felhasznalok.get(i).id + " " + felhasznalok.get(i).username + " " + felhasznalok.get(i).company_id);
        }

    }


    public void textfieldFrissit(ActionEvent event) {

        List<Feladatok> lista = new ArrayList<>();
        lista = DBUtils.getFeladatok();

        try {

            File myObj = new File("listak.txt");
            Scanner myReader = new Scanner(myObj);
            String line = "";

            List<Feladatok> feladatokTablaba = new ArrayList<>();


            if (myObj.length() != 0) {
                while (myReader.hasNextLine()) {
                    String lines = myReader.nextLine();
                    String[] tomb = lines.split("#");
                    DBUtils.addFeladatok(new Feladatok(tomb[0], tomb[1], tomb[2]));
                }


                try {
                    FileWriter myWriter = new FileWriter("listak.txt");
                    myWriter.write("");

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


            for (int i = 0; i < lista.size(); i++) {
                feladatokTablaba.add(new Feladatok(lista.get(i).nevek, lista.get(i).feladat, lista.get(i).ceg_id));
            }


            ObservableList<Feladatok> valami = FXCollections.observableArrayList(
                    feladatokTablaba
            );


            Emberek.setCellValueFactory(new PropertyValueFactory<Feladatok, String>("nevek"));
            Feladat.setCellValueFactory(new PropertyValueFactory<Feladatok, String>("feladat"));
            Cegek.setCellValueFactory(new PropertyValueFactory<Feladatok, String>("ceg_id"));
            table.setItems(valami);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void modify(ActionEvent event) {
        delete(event);

    }

    public void delete(ActionEvent event) {
        if (table.getSelectionModel().getFocusedIndex() >= 0) {
            DBUtils.feladatok.remove(table.getSelectionModel().getFocusedIndex());
            table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
            textfieldFrissit(event);
        }

        try {
            FileWriter myWriter = new FileWriter("listak.txt");
            myWriter.write("");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}