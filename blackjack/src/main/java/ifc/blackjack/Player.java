package ifc.blackjack;

import javafx.scene.text.Text;

public class Player {

    private int score;

    public Player(){
        this.score = 0;
    }

    public void updateScore(int cardValue, int score, Text player) {
        this.score += cardValue;
        updatePoints(player, this.score);
    }
    
    public void updateScore(int cardValue, int score) {
        this.score += cardValue;
    }

    public void updatePoints(Text points, int score) {
        points.setText(String.valueOf(this.score));
        if(this.score >= 10) {
            points.setLayoutX(34.0);
        }  
    }

    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
}
