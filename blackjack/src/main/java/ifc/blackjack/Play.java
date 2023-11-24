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

    private List<Integer> deck;
    private int playerScore;
    private int dealerScore;

    public Play() {
        initializeDeck();
        playerScore = 0;
        dealerScore = 0;
    }

    private void initializeDeck() {
        deck = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            deck.add(i);
        }
        shuffleDeck();
    }

    private void shuffleDeck() {
        Random rand = new Random();
        for (int i = 0; i < deck.size(); i++) {
            int randomIndex = i + rand.nextInt(deck.size() - i);
            int temp = deck.get(i);
            deck.set(i, deck.get(randomIndex));
            deck.set(randomIndex, temp);
        }
    }

    private void updatePlayerScore(int cardValue) {
        playerScore += cardValue;
        if(playerScore >= 10) {
            player_points.setLayoutX(34.0);
        }
        player_points.setText(String.valueOf(playerScore));

    }

    private void updateTablePoints() {
        table_points.setText(String.valueOf(dealerScore));
        if(dealerScore >= 10) {
            table_points.setLayoutX(34.0);
        }  
    }

    private void drawDealerCard(){
        int cardValue = deck.remove(0);
        dealerScore += cardValue;

        if(dealerScore > 21) {
            updateTablePoints();
            turn_definer.setText("JOGADOR VENCEU");
            endGame(); 
        }
    }

    @FXML
    void askCard(ActionEvent event){
        int cardValue = deck.remove(0);
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
                updatePlayerScore(1);
            } else if (result.get() == buttonTypeEleven) {
                // ... user chose "11"
                updatePlayerScore(11);
            }
        }else if(cardValue > 10){
            updatePlayerScore(10);
        }else{
            updatePlayerScore(cardValue);
        }
        
        updateCardImage(cardValue);

        if (playerScore == 21) {
            updateTablePoints();
            turn_definer.setText("JOGADOR VENCEU");
            endGame();
        }else if (playerScore > 21) {
            updateTablePoints();
            turn_definer.setText("MESA VENCEU");
            endGame();
        }else{
            drawDealerCard();
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
        while (dealerScore < 12) {
            drawDealerCard();
        }

        table_points.setText(String.valueOf(dealerScore));
        if(dealerScore >= 10) {
            table_points.setLayoutX(34.0);
        }
        // updateTablePoints();
        if (dealerScore > 21 || playerScore > dealerScore) {
            turn_definer.setText("JOGADOR VENCEU");
        } else if (playerScore < dealerScore) {
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
        playerScore = 0;
        dealerScore = 0;
        player_points.setLayoutX(67.0);
        table_points.setLayoutX(67.0);
        player_points.setText("0");
        table_points.setText("?");
        turn_definer.setText("!BLACKJACK!");
        initializeDeck();
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