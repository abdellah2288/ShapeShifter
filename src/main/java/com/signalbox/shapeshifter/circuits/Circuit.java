package com.signalbox.shapeshifter.circuits;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Circuit
{
    private List<List<Point>> pointList;
    private int dimensionVertical = 0;
    private int dimensionHorizontal = 0;
    private VBox circuitVertical = new VBox();
    static private double scaleFactor = 4.2;
    private int pointCounter = 0;
    public Circuit(int verical,int horizontal)
    {
        circuitVertical.setSpacing(20);
        dimensionVertical = verical;
        dimensionHorizontal = horizontal;
        pointList = new ArrayList<List<Point>>();
        fillCircuit();
    }
    private void fillCircuit() {
        for (int i = 0; i < dimensionVertical; i++) {
            List<Point> line = new ArrayList<>();
            for (int j = 0; j < dimensionHorizontal; j++) {
                line.add(new Point());
            }
            pointList.add(line);
        }
    }
    public Node getCircuit()
    {
     for(List<Point> line : pointList)
     {
         HBox circuitHorizontal = new HBox();
         circuitHorizontal.setSpacing(20);
         for(Point point : line)
         {
            point.getPoint().setOnMouseClicked((e)->{
                if(point.isActive()) pointCounter--;
                else pointCounter++;

                point.togglePoint(String.valueOf(pointCounter));
            });
            circuitHorizontal.getChildren().add(point.getView());
         }
         circuitVertical.getChildren().add(circuitHorizontal);
     }
     return circuitVertical;
    }

    public void togglePoint(int horizontalIndex, int verticalIndex)
    {
        pointList.get(horizontalIndex).get(verticalIndex).togglePoint(String.valueOf(pointCounter));
    }
    public void activatePoint(int horizontalIndex, int verticalIndex)
    {
        pointList.get(horizontalIndex).get(verticalIndex).activatePoint(String.valueOf(pointCounter));
        pointCounter++;
    }
    public void deactivatePoint(int horizontalIndex, int verticalIndex)
    {
        if(pointCounter>0 && pointList.get(horizontalIndex).get(verticalIndex).isActive())
        {
            pointList.get(horizontalIndex).get(verticalIndex).deactivatePoint();
            pointCounter--;
        }
    }
    public void scalePoints(double circuitWidth,double circuitHeight)
    {
        if(circuitWidth < circuitHeight)
        {
            circuitVertical.setSpacing((circuitWidth)/(scaleFactor*dimensionHorizontal));
            for(Node line : circuitVertical.getChildren())
            {
                ((HBox) line).setSpacing((circuitWidth)/(scaleFactor*dimensionHorizontal));
            }
            for(List<Point> line : pointList) for(Point point : line) point.getPoint().setRadius((circuitWidth)/(scaleFactor*dimensionHorizontal));
        }
        else
        {
            circuitVertical.setSpacing((circuitHeight)/(scaleFactor*dimensionVertical));
            for(Node line : circuitVertical.getChildren())
            {
                ((HBox) line).setSpacing((circuitHeight)/(scaleFactor*dimensionVertical));
            }
            for(List<Point> line : pointList) for(Point point : line) point.getPoint().setRadius((circuitHeight)/(scaleFactor*dimensionVertical));
        }
    }

    public void clearCircuit()
    {
        for(List<Point> line : pointList)
        {
            for(Point point : line) point.deactivatePoint();
        }
        resetCounter();
    }
    public void resetCounter()
    {
        pointCounter = 0;
    }
    public void reshape(int newWidth, int newHeight)
    {

        if(dimensionVertical > newHeight) pointList = pointList.subList(0, newHeight);
        if(dimensionHorizontal > newWidth) for(List<Point> list : pointList)   list = list.subList(0,newWidth);
        dimensionVertical = newHeight;
        dimensionHorizontal = newWidth;
        clearCircuit();
        circuitVertical.getChildren().clear();
        pointList.clear();
        fillCircuit();
    }
}
