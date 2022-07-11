package com.example.app;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.app.gifs.Shake;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    public static User user = new User();

    @FXML
    void initialize() {
        signInButton.setOnAction(actionEvent -> {
            String loginText = login_field.getText().trim();
            String loginPassword = password_field.getText().trim();

            if(!loginText.equals("") && !loginPassword.equals("")) {
                try {
                    loginUser(loginText, loginPassword);
//                    changeScene(signInButton, "table.fxml");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else
                System.out.println("Присутствуют пустые поля!");
        });

        signUpButton.setOnAction(actionEvent -> {
            changeScene(signUpButton, "signUp.fxml");
        });
    }

    private void loginUser(String loginText, String loginPassword) throws SQLException {
        Handler handler = new Handler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet resultSet = handler.getUsersData(user);

        if(resultSet.next()) {
            do {
                this.user = user;
                user.setId(resultSet.getInt(1));
                user.setGroup(resultSet.getString(4));
                user.setLevel(resultSet.getString(5));

                if (user.getLevel().equals("user")) {
                    changeScene(signInButton,"table.fxml");
                }
            } while (resultSet.next());
        }
        else {
            Shake loginAnimation = new Shake(login_field);
            Shake passwordAnimation = new Shake(password_field);
            loginAnimation.animation();
            passwordAnimation.animation();
        }
    }

    public void changeScene(Button button, String window){
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
        stage.show();
    }
}