module ifc.blackjack {
    requires javafx.controls;
    requires javafx.fxml;

    opens ifc.blackjack to javafx.fxml;
    exports ifc.blackjack;
}
