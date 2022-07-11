package com.example.app;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TableController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Quote, Date> dayCol;

    @FXML
    private TableColumn<Quote, String> first_nameCol;

    @FXML
    private TableColumn<Quote, String> last_nameCol;

    @FXML
    private TableColumn<Quote, String> quoteCol;

    @FXML
    private TableColumn<Quote, String> second_nameCol;

    @FXML
    private TableColumn<Quote, String> subjectCol;

    @FXML
    private TableView<Quote> teachers_quotes;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    String query = null;
    Connection connection = null;
    Handler handler = new Handler();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Quote quote = null;
    public static int quote_id;
    public static int user_id;

    ObservableList<Quote> quotes = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        loadingData();
        updateButton.setOnAction(actionEvent -> {
            updateTable();
        });
    }

    @FXML
    private void addQuote(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("addQuote.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Добавление цитаты");
        stage.show();
    }

    @FXML
    private void updateTable() {
        try {
            quotes.clear();
            query = "SELECT * FROM `teachers_quotes`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                quotes.add(new Quote(
                        resultSet.getInt(Constants.QUOTE_ID),
                        resultSet.getString(Constants.QUOTE),
                        resultSet.getString(Constants.LAST_NAME),
                        resultSet.getString(Constants.FIRST_NAME),
                        resultSet.getString(Constants.SECOND_NAME),
                        resultSet.getString(Constants.SUBJECT),
                        resultSet.getDate(Constants.DATE),
                        resultSet.getInt(Constants.USER_ID)));

                teachers_quotes.setItems(quotes);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadingData() throws SQLException, ClassNotFoundException {
        connection = Handler.getConnection();
        updateTable();

//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        quoteCol.setCellValueFactory(new PropertyValueFactory<>("quote"));
        last_nameCol.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        first_nameCol.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        second_nameCol.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("date"));
//        user_idCol.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    }

    public void changeScene(String window){
        addButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
