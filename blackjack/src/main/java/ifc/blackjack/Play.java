package ifc.blackjack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
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
        player_points.setText(String.valueOf(playerScore));
    }

    private void updateTablePoints() {
        table_points.setText("Dealer: " + dealerScore + " | Player: " + playerScore);
    }

    private void drawDealerCard() {
        int cardValue = deck.remove(0);
        dealerScore += cardValue;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateTablePoints();
        turn_definer.setText("Dealer: " + dealerScore);
    }

    @FXML
    void askCard(ActionEvent event) {
        int cardValue = deck.remove(0);
        updatePlayerScore(cardValue);

        if (playerScore > 21) {
            turn_definer.setText("Player Busted! Dealer Wins!");
            endGame();
        } else {
            btn_ask_card.setDisable(false);
            drawDealerCard();
        }
    }

    @FXML
    void passTurn(ActionEvent event) {
        btn_ask_card.setDisable(true);
        btn_pass.setDisable(true);
        while (dealerScore < 17) {
            drawDealerCard();
        }

        updateTablePoints();
        Platform.runLater(() -> {
            if (dealerScore > 21 || playerScore > dealerScore) {
                turn_definer.setText("Player Wins!");
            } else if (playerScore < dealerScore) {
                turn_definer.setText("Dealer Wins!");
            } else {
                turn_definer.setText("It's a Tie!");
            }

            endGame();

            btn_ask_card.setDisable(false);
            btn_pass.setDisable(false);
        });
    }

    @FXML
    void resetGame(ActionEvent event) {
        playerScore = 0;
        dealerScore = 0;
        player_points.setText("0");
        table_points.setText("Dealer: 0 | Player: 0");
        turn_definer.setText("");
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