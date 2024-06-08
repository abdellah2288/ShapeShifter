package com.signalbox.shapeshifter.circuits;


import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class Point
{
    private StackPane stackPane;
    private Text pointOrder;
    private Circle point;
    private boolean active = false;
    public Point()
    {
        stackPane = new StackPane();
        pointOrder = new Text();
        pointOrder.setBoundsType(TextBoundsType.VISUAL);

        point = new Circle(0);
        stackPane.getChildren().addAll(point,pointOrder);
        point.setEffect(new DropShadow(20, Color.rgb(255, 215, 0, 0.7)));
        point.setFill(Paint.valueOf("#f5f5f5"));
    }
    public void activatePoint(String order)
    {
        point.setFill(Paint.valueOf("#ffd700"));
        pointOrder.setText(order);
        active = true;
    }
    public void deactivatePoint()
    {
        point.setFill(Paint.valueOf("#f5f5f5"));
        pointOrder.setText("");
        active = false;
    }
    public void togglePoint(String order)
    {
        if(active)
        {
            deactivatePoint();
        }
        else activatePoint(order);
    }

    public Circle getPoint()
    {
        return point;
    }
    public StackPane getView()
    {
        return stackPane;
    }
    public boolean isActive() {
        return active;
    }
}