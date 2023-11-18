package ifc.blackjack;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Play {

    @FXML
    private Button btn_ask_card;

    @FXML
    private Button btn_pass;

    @FXML
    private Button btn_menu;

    @FXML
    private Button btn_reset;

    @FXML
    private ImageView current_card;

    @FXML
    private Text player_points;

    @FXML
    private Text table_points;

    @FXML
    private Text turn_definer;
    
    @FXML
    void askCard(ActionEvent event) {

    }

    @FXML
    void passTurn(ActionEvent event) {

    }

    @FXML
    void resetGame(ActionEvent event) {

    }

    @FXML
    void goBackMenu(ActionEvent event) throws IOException{
        App.setRoot("mainmenu");
    }
}
