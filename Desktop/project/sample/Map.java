

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map extends Pane {


    private double unit;
    private int size;
    private int[][] map;
    private Position start;

    public double getUnit() {
        return 50;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[][] getMap() {
        return this.map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Position getStart() {
        return this.start;
    }

    public void setStart(Position start) {
        Node node = (Node)this.getChildren().get(start.getX() * this.size + start.getY());
        Label label = (Label) node;
        Image image = new Image(getClass().getResourceAsStream("grass.png"),
                50, 50, false, false);
        label.setGraphic(new ImageView(image));
    }

    public void setEnd(Position end) {
        Node node = (Node)this.getChildren().get(end.getX() * this.size + end.getY());
        Label label = (Label) node;
        Image image = new Image(getClass().getResourceAsStream("1.png"),
                50, 50, false, false);
        label.setGraphic(new ImageView(image));
    }

    public void draw(String filename) throws FileNotFoundException {

        FileInputStream input = new FileInputStream(new File(filename));
        Scanner in = new Scanner(input);

        this.size = in.nextInt();
        this.map = new int[this.size][this.size];

        this.setPrefWidth(this.size*50);
        this.setPrefHeight(this.size*50);

        for (int i = 0; i < this.size; ++i){
            for (int j = 0; j < this.size; ++j){
                this.map[i][j] = in.nextInt();
                Label label = new Label();
                Image image;
                if (this.map[i][j] == 1)
                    image = new Image(getClass().getResourceAsStream("wall.png"),
                            50, 50, false, false);
                else if (this.map[i][j] == 2) {
                    image = new Image(getClass().getResourceAsStream("1.png"),
                            50, 50, false, false);
                    this.start = new Position(i, j);
                }
                else
                    image = new Image(getClass().getResourceAsStream("grass.png"),
                            50, 50, false, false);
                label.setGraphic(new ImageView(image));
                label.setStyle("-fx-background-color: #32CD32");


                this.getChildren().add(i * this.size + j, label);
                label.setLayoutX((i * 50));
                label.setLayoutY((j * 50));
            }
        }



    }
}
