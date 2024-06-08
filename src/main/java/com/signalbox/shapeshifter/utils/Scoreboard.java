package com.signalbox.shapeshifter.utils;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.*;

public class Scoreboard
{
    static private boolean timeTrials = false;
    static private VBox scoreboard = new VBox();
    static public VBox getScoreboard()
    {
       refreshScoreboard();
       return scoreboard;
    }
    static public void refreshScoreboard()
    {
        HashMap<String,Double> teamMap = Parser.getTeamMap();
        HashMap<String,Double> teamMap2 = Parser.getTeamMapTT();
        List<Map.Entry<String, Double>> list;
        HBox header;
        Region spacer = new Region();
        spacer.setMinWidth(300);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        if(timeTrials)
        {
            header = new HBox(new Label("Team Name"),spacer,new Label("Time"));
            list = new LinkedList<>(teamMap2.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    return  o1.getValue().compareTo(o2.getValue());
                }
            });
        }
        else
        {
            header = new HBox(new Label("Team Name"),spacer,new Label("Score"));
            list = new LinkedList<>(teamMap.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    return -1 * o1.getValue().compareTo(o2.getValue());
                }
            });
        }
        // Create a result map that preserves the order of insertion
        Map<String, Double > sortedScoreBoard = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            sortedScoreBoard.put(entry.getKey(), entry.getValue());
        }
        VBox scoreboardSorted = new VBox();
        for(Map.Entry<String, Double> entry : sortedScoreBoard.entrySet())
        {
            HBox row = new HBox();
            row.setSpacing(20);
            Label teamName = new Label(entry.getKey());
            Label score = new Label(entry.getValue().toString());
            spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            row.getChildren().add(teamName);
            row.getChildren().add(spacer);
            row.getChildren().add(score);
            scoreboardSorted.getChildren().add(row);
        }


        scoreboard.getChildren().clear();
        scoreboard.getChildren().addAll(header,scoreboardSorted);
    }
    static public void enableTimeTrials()
    {
        timeTrials = true;
    }
    static public void disableTimeTrials()
    {
        timeTrials = false;
    }
    static public boolean isTimeTrials()
    {
        return timeTrials;
    }
}
