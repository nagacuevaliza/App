package com.example.app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField first_name;

    @FXML
    private TextField last_name;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField second_name;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            newUser();
        });
    }

    private void newUser() {
        Handler handler = new Handler();

        String lastName = last_name.getText();
        String firstName = first_name.getText();
        String secondName = second_name.getText();
        String login = login_field.getText();
        String password = password_field.getText();

        User user = new User(lastName, firstName, secondName, login, password);

        handler.signUpUser(user);

    }

}
