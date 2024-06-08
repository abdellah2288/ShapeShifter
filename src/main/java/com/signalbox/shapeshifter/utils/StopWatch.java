package com.signalbox.shapeshifter.utils;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPane;

public class StopWatch
{
    static private volatile int milliseconds = 0;
    static private volatile boolean needsUpdating = false;
    static private volatile boolean stopWatchOn = false;
    static private Button pauseButton = new Button("⏸"){{
        setOnMouseClicked(event -> {
            StopWatch.pauseStopWatch();
        });
    }};
    static private Button runButton = new Button("⏵"){{
        setOnMouseClicked(event -> {
           StopWatch.startStopWatch();
        });
    }};
    static private Button resetButton = new Button("↺"){{
        setOnMouseClicked(event -> {
           StopWatch.resetStopWatch();
        });
    }};
    static private FlowPane stopWatchControls = new FlowPane(pauseButton, runButton, resetButton);
    static private Label timeLabel = new Label("00:000.00"){{setStyle("""
            -fx-font-family:'Kode Mono';
            -fx-font-size:1.7em;
            -fx-text-fill:gold;
            -fx-border-style:solid;
            -fx-border-color:gold;
            -fx-border-width:2px;
            -fx-padding: 10px 10px 10px 10px;
            """);}};
    static private FlowPane stopWatch = new FlowPane(timeLabel,runButton,pauseButton,resetButton);
    static public Thread stopWatchThread = new Thread(
            () ->
            {
                while(true)
                {
                    try
                    {
                        if(stopWatchOn)
                        {
                            milliseconds++;
                            Platform.runLater(() -> updateTimeLabel());
                            Thread.sleep(1);
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            });

    static public void updateTimeLabel()
    {

        long totalSeconds = milliseconds / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long millis = milliseconds % 1000;
        timeLabel.setText(String.format("%02d:%02d.%03d", minutes, seconds, millis));
    }

    static public void startStopWatch()
    {
        if (!stopWatchThread.isAlive()) stopWatchThread.start();
        stopWatchOn = true;
    }
    static public void pauseStopWatch()
    {
        stopWatchOn=false;
    }
    static public void resetStopWatch()
    {
        milliseconds = 0;
        Platform.runLater(()->updateTimeLabel());
    }
    static public int getMilliseconds()
    {
        return milliseconds;
    }
    static public FlowPane getStopWatch()
    {
        stopWatch.setOrientation(Orientation.VERTICAL);
        stopWatchControls.setMaxWidth(Double.MAX_VALUE);
        stopWatch.setMaxWidth(Double.MAX_VALUE);
        return stopWatch;
    }
}
