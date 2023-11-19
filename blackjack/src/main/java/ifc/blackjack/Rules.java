package ifc.blackjack;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Rules {

    @FXML
    private Button btn_menu;

    @FXML
    void menu(ActionEvent event) throws IOException {
        App.setRoot("mainmenu");
    }

}
