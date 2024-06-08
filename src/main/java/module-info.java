module com.signalbox.shapeshifter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.signalbox.shapeshifter to javafx.fxml;
    exports com.signalbox.shapeshifter;
}