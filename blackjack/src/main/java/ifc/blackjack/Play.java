package ifc.blackjack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
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

    private Deck deck = new Deck();
    private Player player = new Player();
    private Player dealer = new Player();

    public Play() {
        
    }

    // private void initializeDeck() {
    //     deck = new ArrayList<>();
    //     for (int i = 1; i <= 13; i++) {
    //         deck.add(i);
    //     }
    //     shuffleDeck();
    // }

    // private void shuffleDeck() {
    //     Random rand = new Random();
    //     for (int i = 0; i < deck.size(); i++) {
    //         int randomIndex = i + rand.nextInt(deck.size() - i);
    //         int temp = deck.get(i);
    //         deck.set(i, deck.get(randomIndex));
    //         deck.set(randomIndex, temp);
    //     }
    // }

    // private void updatePlayerScore(int cardValue) {
    //     playerScore += cardValue;
    //     if(playerScore >= 10) {
    //         player_points.setLayoutX(34.0);
    //     }
    //     player_points.setText(String.valueOf(playerScore));
    // }

    private void drawDealerCard(){
        int cardValue = deck.removeCard();
        dealer.updateScore(cardValue, dealer.getScore());

        if(dealer.getScore() > 21) {
            dealer.updatePoints(table_points, dealer.getScore());
            turn_definer.setText("JOGADOR VENCEU");
            endGame(); 
        }
    }

    @FXML
    void askCard(ActionEvent event){
        int cardValue = deck.removeCard();
        if(cardValue == 1){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Escolha uma opção");
            alert.setHeaderText("Escolha o valor para o Ás");
            alert.setContentText("Escolha a sua opção:");
    
            ButtonType buttonTypeOne = new ButtonType("1");
            ButtonType buttonTypeEleven = new ButtonType("11");
    
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeEleven);
    
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                // ... user chose "1"
                player.updateScore(1, player.getScore(), player_points);
            } else if (result.get() == buttonTypeEleven) {
                // ... user chose "11"
                player.updateScore(11, player.getScore(), player_points);
            }
        }else if(cardValue > 10){
            player.updateScore(10, player.getScore(), player_points);
        }else{
            player.updateScore(cardValue, player.getScore(), player_points);
        }
        
        updateCardImage(cardValue);

        if (player.getScore() == 21) {
            dealer.updatePoints(table_points, dealer.getScore());
            turn_definer.setText("JOGADOR VENCEU");
            endGame();
        }else if (player.getScore() > 21) {
            turn_definer.setText("MESA VENCEU");
            endGame();
        }
    }

    private void updateCardImage(int cardValue) {
        String cardFile;
        cardFile = "cards/" +cardValue + "c.png";
        Image cardImage = new Image(getClass().getResourceAsStream(cardFile));
        current_card.setImage(cardImage);
    }

    @FXML
    void passTurn(ActionEvent event){
        btn_ask_card.setDisable(true);
        btn_pass.setDisable(true);
        while (dealer.getScore() < 12) {
            drawDealerCard();
        }

        dealer.updatePoints(table_points, dealer.getScore());
        if (dealer.getScore() > 21 || player.getScore() > dealer.getScore()) {
            turn_definer.setText("JOGADOR VENCEU");
        } else if (player.getScore() < dealer.getScore()) {
            turn_definer.setText("MESA VENCEU");
        } else {
            turn_definer.setText("EMPATE");
        }

        endGame();
    }

    @FXML
    void resetGame(ActionEvent event) {
        Image newGame = new Image(getClass().getResourceAsStream("cards/fundo.png"));
        btn_ask_card.setDisable(false);
        btn_pass.setDisable(false);
        current_card.setImage(newGame);
        player.setScore(0);
        dealer.setScore(0);
        player_points.setLayoutX(67.0);
        table_points.setLayoutX(67.0);
        player_points.setText("0");
        table_points.setText("?");
        turn_definer.setText("!BLACKJACK!");
        deck.initializeDeck();
    }

    private void endGame() {
        btn_ask_card.setDisable(true);
        btn_pass.setDisable(true);
    }

    @FXML
    void goBackMenu(ActionEvent event) throws IOException {
        App.setRoot("mainmenu");
    }
}