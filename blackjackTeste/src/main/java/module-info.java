module ifc.blackjack {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens ifc.blackjack to javafx.fxml;
    exports ifc.blackjack;
}
