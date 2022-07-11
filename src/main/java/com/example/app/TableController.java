package com.example.app;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Контроллер для страницы с отображением списка цитат
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
    private Button editButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button updateButton;

    @FXML
    private Text loginField;

    @FXML
    private Text groupField;

    String query = null;
    Connection connection = null;
    Handler handler = new Handler();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public static int quote_id;
    public static int user_id;

    // Массив для хранения цитат
    ObservableList<Quote> quotes = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        if (Controller.user.getLevel().equals("guest")) {
            setUsersData("Гость", "");
            addButton.setDisable(true);
            editButton.setDisable(true);
            removeButton.setDisable(true);
        } else setUsersData(Controller.user.getLogin(), "Группа: " + Controller.user.getGroup());

        loadingData();
        updateButton.setOnAction(actionEvent -> {
            updateTable();
        });

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("view.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(new Scene(root));
            stage.setTitle("Авторизация");
            stage.show();
        });
    }

    // Добавление новой цитаты
    @FXML
    private void addQuote(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("addQuote.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Добавление цитаты");
        stage.show();
    }

    // Изменение цитаты
    @FXML
    void editQuote(MouseEvent event) throws IOException {
        quote_id = teachers_quotes.getSelectionModel().getSelectedItem().getId();
        user_id = teachers_quotes.getSelectionModel().getSelectedItem().getUser_id();
        Parent parent = FXMLLoader.load(getClass().getResource("editQuote.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Изменение цитаты");
        stage.show();
    }

    // Удаление цитаты
    @FXML
    void removeQuote(MouseEvent event) throws SQLException, ClassNotFoundException {
        connection = handler.getConnection();

        int id = teachers_quotes.getSelectionModel().getSelectedItem().getId();
        query = "DELETE FROM " + Constants.QUOTES_TABLE + " WHERE " + Constants.QUOTE_ID + "=" + id;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
        updateTable();
    }

    // Обновление данных, хранящихся в таблице
    @FXML
    private void updateTable() {
        try {
            quotes.clear();
            String level = Controller.user.getLevel();

            if (level.equals("guest")) query = "SELECT * FROM " + Constants.QUOTES_TABLE;
            if (level.equals("admin")) query = "SELECT * FROM " + Constants.QUOTES_TABLE;
            if (level.equals("user")) query =
                    "SELECT teachers_quotes.id, teachers_quotes.quote, teachers_quotes.last_name, teachers_quotes.first_name, " +
                            "teachers_quotes.second_name, teachers_quotes.subject, teachers_quotes.date, teachers_quotes.user_id " +
                            "FROM users " +
                            "JOIN teachers_quotes ON (users.id = teachers_quotes.user_id) " +
                            "WHERE (users.group ='" + Controller.user.getGroup() + "')";

            if (level.equals("verifier")) query =
                    "SELECT teachers_quotes.id, teachers_quotes.quote, teachers_quotes.last_name, teachers_quotes.first_name, " +
                            "teachers_quotes.second_name, teachers_quotes.subject, teachers_quotes.date, teachers_quotes.user_id " +
                            "FROM users " +
                            "JOIN teachers_quotes ON (users.id = teachers_quotes.user_id) " +
                            "WHERE (users.group ='" + Controller.user.getGroup() + "')";


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

    // Отображение данных с сервера
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

    // Получение данных пользователя
    public void setUsersData(String login, String group) {
        loginField.setText(login);
        groupField.setText(group);
    }
}
