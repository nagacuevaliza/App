package com.example.app;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Контроллер для страницы регистрации
public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField group_field;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signUpButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Text noticeField;
    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            try {
                newUser();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        goBackButton.setOnAction(actionEvent -> {
            changeScene(goBackButton, "view.fxml", "Доступ к базе данных");
        });
    }

    // Регистрация нового пользователя
    private void newUser() throws SQLException, ClassNotFoundException {
        Handler handler = new Handler();
        String login = login_field.getText();
        String password = password_field.getText();
        String group= group_field.getText();
        if (!login.equals("") && !password.equals("") && !group.equals("")){
            noticeField.setStyle("-fx-fill: " + "#32CD32");
            noticeField.setText("Регистрация прошла успешно!");
            User user = new User(login, password, group);
            handler.signUpUser(user);  // Внесение новых данных о пользователе в базу данных
        }

    }

    // Смена отображаемого экрана
    public void changeScene(Button button, String window, String title){
        button.getScene().getWindow().hide();

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
        stage.setTitle(title);
        stage.show();
    }
}
