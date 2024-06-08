package com.signalbox.shapeshifter;

import com.signalbox.shapeshifter.circuits.Circuit;
import com.signalbox.shapeshifter.utils.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private String currentPhase = "PHASE I";
    private boolean changedPhase = false;
    private Circuit circuit;
    @Override

    public void start(Stage stage) throws IOException {

        circuit = new Circuit(0,0);
        BorderPane root = new BorderPane();
        /*Custom title bar*/
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.BASELINE_RIGHT);
        titleBar.setStyle("-fx-background-color: #212121; -fx-padding: 5px;");
        // Add control buttons
        Button closeButton = new Button("X");
        closeButton.setOnAction(event -> stage.close());
        Button minimizeButton = new Button("-");
        minimizeButton.setOnAction(event -> stage.setIconified(true));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleBar.getChildren().addAll(new Label("Circuit Jam 24"){{
                                    setStyle("-fx-font-weight:700;" +
                                              "-fx-text-fill:gold;" +
                                              "-fx-font-size:1.2em;" +
                                              "-fx-font-family:'Kode Mono';");}}
                ,spacer
                ,minimizeButton
                ,closeButton);
        /*END OF CUSTOM TITLEBAR*/
        
        root.setTop(titleBar);
        root.setLeft(Configurator.generateConfigPanel());
        root.setCenter(circuit.getCircuit());
        root.setRight(Scoreboard.getScoreboard());
        BorderPane.setMargin(root.getLeft(),new Insets(70,0,0,30));
        BorderPane.setMargin(root.getCenter(),new Insets(70,0,0,100));
        BorderPane.setMargin(root.getRight(),new Insets(70,30,0,0));
        Configurator.teamComboBox.getItems().addAll(Parser.getTeamNames());

        ImagePattern pattern = new ImagePattern(new Image("file:/home/abdellah/IdeaProjects/ShapeShifter/static/bg.png"));
        Scene scene = new Scene(root, 1024,720, Color.rgb(42,42,42));
        scene.setFill(pattern);
        root.setBackground(Background.EMPTY);
        try
        {
            scene.getStylesheets().add("file:/home/abdellah/IdeaProjects/ShapeShifter/static/style.css");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        circuit.scalePoints(50,50);
        attachConfiguratorCallbacks();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Shape Shifter");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.show();

        new Thread(()->{
            while(true){
                Platform.runLater(()->{
                    if(changedPhase)
                    {
                        if (currentPhase.equals("PHASE I") || currentPhase.equals("PHASE II")) {
                            if(Scoreboard.isTimeTrials())
                            {
                                Scoreboard.disableTimeTrials();
                                Configurator.chronoButton.setDisable(true);
                            }
                            circuit.resetCounter();
                            circuit.reshape(3, 3);
                        }
                        else if(currentPhase.equals("TIME TRIALS"))
                        {
                            circuit.resetCounter();
                            circuit.reshape(5, 5);
                            if(!Scoreboard.isTimeTrials())
                            {
                                Scoreboard.enableTimeTrials();
                                Configurator.chronoButton.setDisable(false);
                            }
                        }
                        else if (currentPhase.equals("PHASE III")) {
                            if(Scoreboard.isTimeTrials())
                            {
                                Scoreboard.disableTimeTrials();
                                Configurator.chronoButton.setDisable(true);
                            }
                            circuit.resetCounter();
                            circuit.reshape(5, 5);
                        }
                        else
                        {
                            Configurator.chronoButton.setDisable(true);
                            circuit.resetCounter();
                            circuit.reshape(0, 0);
                        }
                        root.setCenter(circuit.getCircuit());
                        changedPhase = false;
                        circuit.scalePoints(stage.getWidth(), stage.getHeight());
                    }
                    Scoreboard.refreshScoreboard();
                    if (!stage.isShowing()) System.exit(0);
                    }
                );
                try
                {
                    Thread.sleep(8);
                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }
    public void attachConfiguratorCallbacks()
    {
        Configurator.resetButton.setOnMouseClicked(event ->
                {
                    Parser.resetScores();
                }
        );
        Configurator.chronoButton.setDisable(true);
        Configurator.chronoButton.setOnMouseClicked(event ->
        {
            if(!Configurator.teamComboBox.getSelectionModel().isEmpty())
            {
                String currentTeam = Configurator.teamComboBox.getSelectionModel().getSelectedItem();
                Parser.changeScoreTT(currentTeam,StopWatch.getMilliseconds());
            }
        });
        Configurator.clearButton.setOnMouseClicked(event -> {
            circuit.clearCircuit();
            circuit.resetCounter();
        });
        Configurator.phaseComboBox.setOnAction(event ->
        {
            if(!Configurator.phaseComboBox.getSelectionModel().isEmpty())
            {
                currentPhase = Configurator.phaseComboBox.getSelectionModel().getSelectedItem();
                changedPhase = true;
            }
        });
        Configurator.validateButton.setOnMouseClicked(event -> {
            if(!Configurator.teamComboBox.getSelectionModel().isEmpty())
            {
                String currentTeam = Configurator.teamComboBox.getSelectionModel().getSelectedItem();
                Parser.changeScore(currentTeam,Parser.getTeamMap().get(currentTeam)+50);
            }
        });
        Configurator.plusButton.setOnMouseClicked(event -> {
            if(!Configurator.customScore.getText().isEmpty() && !Configurator.teamComboBox.getSelectionModel().isEmpty())
            {
                String currentTeam = Configurator.teamComboBox.getSelectionModel().getSelectedItem();
                Parser.changeScore(currentTeam,Parser.getTeamMap().get(currentTeam)+Double.valueOf(Configurator.customScore.getText()));
            }
        });
        Configurator.randomizeButton.setOnMouseClicked(event->{
            if(!Configurator.randomizerTextField.getText().isEmpty() && !Configurator.phaseComboBox.getSelectionModel().isEmpty())
            {
                circuit.clearCircuit();
                List<int[]> generatedShape;
                Integer shapeSize = Integer.valueOf(Configurator.randomizerTextField.getText());
                String currentPhase = Configurator.phaseComboBox.getSelectionModel().getSelectedItem();
                if(currentPhase.equals("PHASE III") || currentPhase.equals("TIME TRIALS"))
                {
                    generatedShape = Randomizer.generateRandomShape(shapeSize,2,2,5);
                }
                else
                {
                    generatedShape = Randomizer.generateRandomShape(shapeSize,1,1,3);
                }
                for(int[] point : generatedShape)
                {
                    Platform.runLater(()->{
                        circuit.activatePoint(point[0],point[1]);
                    });
                }
            }
        });
    }
    public static void main(String[] args) {
        launch();
    }
}