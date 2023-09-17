module ch.bvrt.game {
    requires javafx.controls;
    requires javafx.fxml;


    opens ch.bvrt.game to javafx.fxml;
    exports ch.bvrt.game;
}