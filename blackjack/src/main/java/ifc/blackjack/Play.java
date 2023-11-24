package ifc.blackjack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private Pane pop_up;

    @FXML
    public void initialize() {
        pop_up.setVisible(false);
    }

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
        for (int i = 2; i <= 11; i++) {
            for (int j = 0; j < 4; j++) {
                deck.add(i);
            }
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

    // private void updateTablePoints() {
        
    // }

    private void drawDealerCard() {
        int cardValue = deck.remove(0);
        dealerScore += cardValue;

        updateCardImage(cardValue);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void askCard(ActionEvent event) {
        int cardValue = deck.remove(0);
        updatePlayerScore(cardValue);

        updateCardImage(cardValue);

        if (playerScore > 21) {
            table_points.setText(String.valueOf(dealerScore));
            if(dealerScore >= 10) {
                table_points.setLayoutX(34.0);
            }
            turn_definer.setText("MESA VENCEU");
            endGame();
        } else {
            btn_ask_card.setDisable(false);
            drawDealerCard();
        }
    }

    private void updateCardImage(int cardValue) {
        String cardFile;
        if (cardValue >= 1 && cardValue <= 10) {
            cardFile = "cards/" +cardValue + "c.png";
        } else {
            cardFile = "10c.png";
        }
        Image cardImage = new Image(getClass().getResourceAsStream(cardFile));
        current_card.setImage(cardImage);
    }

    @FXML
    void passTurn(ActionEvent event) {
        btn_ask_card.setDisable(true);
        btn_pass.setDisable(true);
        while (dealerScore < 17) {
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