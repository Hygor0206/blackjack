package ifc.blackjack;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenu {

    @FXML
    private Button btn_one_player;

    @FXML
    private Button btn_how_to_play;

    @FXML
    private Button btn_close_app;

    @FXML
    void play(ActionEvent event) throws IOException{
        App.setRoot("playscreen");
    }

    @FXML
    void rules(ActionEvent event) throws IOException{
        App.setRoot("rules");
    }

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

}
