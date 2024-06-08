package com.signalbox.shapeshifter.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Set;

public class Parser {
    static private HashMap<String, Double> teamMap = null;
    static private HashMap<String, Double> teamMapTT = null;

    // Original parseScoreboard function for 'board'
    static public void parseScoreboard(String scoreboardPath) {
        try {
            teamMap = new HashMap<>();
            FileInputStream inputStream = new FileInputStream(scoreboardPath);
            byte[] sbBytes = inputStream.readAllBytes();
            StringBuilder placeHolder = new StringBuilder();
            String placeHolderKey = null;
            for (byte b : sbBytes) {
                char c = (char) (b & 0xFF);
                if (c == ',') {
                    placeHolderKey = placeHolder.toString();
                    placeHolder.setLength(0);
                } else if (c == ';') {
                    teamMap.put(placeHolderKey, Double.valueOf(placeHolder.toString()));
                    placeHolder.setLength(0);
                } else placeHolder.append(c);
            }
            inputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // New parseScoreboard function for 'boardTT'
    static public void parseScoreboardTT(String scoreboardPath) {
        try {
            teamMapTT = new HashMap<>();
            FileInputStream inputStream = new FileInputStream(scoreboardPath);
            byte[] sbBytes = inputStream.readAllBytes();
            StringBuilder placeHolder = new StringBuilder();
            String placeHolderKey = null;
            for (byte b : sbBytes) {
                char c = (char) (b & 0xFF);
                if (c == ',') {
                    placeHolderKey = placeHolder.toString();
                    placeHolder.setLength(0);
                } else if (c == ';') {
                    teamMapTT.put(placeHolderKey, Double.valueOf(placeHolder.toString()));
                    placeHolder.setLength(0);
                } else placeHolder.append(c);
            }
            inputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public HashMap<String, Double> getTeamMap() {
        if (teamMap == null) parseScoreboard("/home/abdellah/IdeaProjects/ShapeShifter/static/board");
        return teamMap;
    }

    static public HashMap<String, Double> getTeamMapTT() {
        if (teamMapTT == null) parseScoreboardTT("/home/abdellah/IdeaProjects/ShapeShifter/static/boardTT");
        return teamMapTT;
    }

    static public Set<String> getTeamNames() {
        parseScoreboard("/home/abdellah/IdeaProjects/ShapeShifter/static/board");
        return teamMap.keySet();
    }

    static public Set<String> getTeamNamesTT() {
        parseScoreboardTT("/home/abdellah/IdeaProjects/ShapeShifter/static/boardTT");
        return teamMapTT.keySet();
    }

    static public void changeScore(String teamName, double score) {
        try {
            FileOutputStream outputStream = new FileOutputStream("/home/abdellah/IdeaProjects/ShapeShifter/static/board");
            if (teamMap != null) teamMap.put(teamName, score);
            else parseScoreboard("/home/abdellah/IdeaProjects/ShapeShifter/static/board");
            for (String key : teamMap.keySet()) {
                outputStream.write(key.getBytes());
                outputStream.write(',');
                outputStream.write(String.valueOf(teamMap.get(key)).getBytes());
                outputStream.write(';');
            }
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public void changeScoreTT(String teamName, double score) {
        try {
            FileOutputStream outputStream = new FileOutputStream("/home/abdellah/IdeaProjects/ShapeShifter/static/boardTT");
            if (teamMapTT != null) teamMapTT.put(teamName, score);
            else parseScoreboardTT("/home/abdellah/IdeaProjects/ShapeShifter/static/boardTT");
            for (String key : teamMapTT.keySet()) {
                outputStream.write(key.getBytes());
                outputStream.write(',');
                outputStream.write(String.valueOf(teamMapTT.get(key)).getBytes());
                outputStream.write(';');
            }
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public void resetScores() {
        if (teamMap == null) getTeamMap();
        for (String key : teamMap.keySet()) {
            changeScore(key, 0);
        }
        if (teamMapTT == null) getTeamMapTT();
        for (String key : teamMapTT.keySet()) {
            changeScoreTT(key, 0);
        }
    }

    static public void resetScoresTT() {
        if (teamMapTT == null) getTeamMapTT();
        for (String key : teamMapTT.keySet()) {
            changeScoreTT(key, 0);
        }
    }
}
