package com.example.app;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

// Контроллер для окна с изменением цитаты
public class editQuoteController  {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text alertText;

    @FXML
    private Button cleanButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField first_nameField;

    @FXML
    private TextField last_nameField;

    @FXML
    private TextField quoteField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField second_nameField;

    @FXML
    private TextField subjectField;

    Connection connection = null;
    PreparedStatement prSt = null;
    Handler handler = new Handler();

    @FXML
    void initialize() {
    }

    // Загрузка введенных данных в базу данных
    @FXML
    void save(MouseEvent event) throws SQLException, ClassNotFoundException {
        connection = handler.getConnection();
        String update = "UPDATE " + Constants.QUOTES_TABLE + " SET " + Constants.QUOTE + "=?, " +
                Constants.LAST_NAME + "=?, " + Constants.FIRST_NAME + "=?, " + Constants.SECOND_NAME + "=?, " +
                Constants.SUBJECT + "=?, "  + Constants.DATE + "=?, " + Constants.USER_ID +
                "=? WHERE id=" + TableController.quote_id;


        prSt = connection.prepareStatement(update);
        prSt.setString(1, quoteField.getText());
        prSt.setString(2, last_nameField.getText());
        prSt.setString(3, first_nameField.getText());
        prSt.setString(4, second_nameField.getText());
        prSt.setString(5, subjectField.getText());
        prSt.setInt(7, Controller.user.getId());

        java.util.Date date = java.util.Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date dateForSql = new java.sql.Date(date.getTime());
        prSt.setDate(6, dateForSql);
        System.out.println(prSt);
        prSt.execute();
    }

    // Удаление значений всех полей
    @FXML
    void clean(MouseEvent event) {
        quoteField.setText(null);
        last_nameField.setText(null);
        first_nameField.setText(null);
        second_nameField.setText(null);
        subjectField.setText(null);
    }
}
