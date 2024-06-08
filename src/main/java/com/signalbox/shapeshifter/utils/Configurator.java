package com.signalbox.shapeshifter.utils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Configurator
{
    static private VBox configBox = new VBox();
    static private Label teamLabel = new Label("Current Team:");
    static private Label phaseLabel = new Label("Current Phase: ");
    static public ComboBox<String> phaseComboBox = new ComboBox<>();
    static public ComboBox<String> teamComboBox = new ComboBox<>();
    static public Button validateButton = new Button("Validate");
    static public Button resetButton = new Button("Reset Scoreboard");
    static public Button clearButton = new Button("Clear Circuit");

    static public TextField customScore = new TextField();
    static private Label customScoreLabel = new Label("Custom Score:");
    static public Button plusButton = new Button("+");
    static private FlowPane customScoreRow = new FlowPane();


    static public TextField randomizerTextField = new TextField();
    static public Button randomizeButton = new Button("⚂");
    static private FlowPane randomizerRow = new FlowPane(randomizerTextField,randomizeButton);
    static public Button chronoButton = new Button("⏱");
    static public VBox generateConfigPanel()
    {

        customScoreRow.getChildren().addAll(customScore,plusButton);
        customScoreRow.setAlignment(Pos.BASELINE_LEFT);
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        phaseComboBox.getItems().addAll("PHASE I","PHASE II","PHASE III","TIME TRIALS","FACE OFF");
        configBox.setSpacing(20);
        configBox.getChildren().addAll(teamLabel,teamComboBox, phaseComboBox, randomizerRow, customScoreRow , resetButton, clearButton,chronoButton,StopWatch.getStopWatch());
        configBox.setFillWidth(true);
        configBox.setMaxHeight(Double.MAX_VALUE);
        return configBox;
    }
    static public void enableTimeTrials()
    {
        configBox.getChildren().add(chronoButton);
    }

}
