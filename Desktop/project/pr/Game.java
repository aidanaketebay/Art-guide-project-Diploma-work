
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game extends Application {

    private  Scene scn;
    public static Map map;
    static MyPlayer player;
    public static Food food;
    private Pane pn;
    private Pane pant;
    static int X,Y;
    private MediaPlayer mediaPlayer;
    private ArrayList<Integer> list;
    private ArrayList<Integer> listX;
    private  static int x=0,y=0;
    public static void main(String[] args){
        launch(args);
    }

    @Override
     public void start(Stage primaryStage) throws FileNotFoundException {
        map = new Map("map0.txt");
        map.rectengle();
        x = map.getUnit()*map.getSize();
        player = new MyPlayer(map);
        player.ball.setFill(Color.WHITE);

        pn = new Pane();
		int d = map.getSize()*map.getUnit();
        pn.setMinHeight(d);
        pn.setMinWidth(d);
		
        VBox v = new VBox();
        v.setAlignment(Pos.CENTER);
		
		if(map.rectangles==1){
			Button bt = new Button("Play");
        pn.setStyle("-fx-background-color:#42b9c1");
        pn.getChildren().add(bt);
        bt.setOnAction(event -> {
            food = new Food(map,player);
            player = new MyPlayer(map);
            pn.getChildren().clear();
            pn.getChildren().add(map);
            BotPlayer btpl = new BotPlayer(map,food,player);
            btpl.find();
        });}
		else {
			Button bt2 = new Button("play(->)");
			
			Button bt3 = new Button("play(0)");
			
			VBox v1 = new VBox();
			v1.getChildren().addAll(bt2,bt3);
			 pn.setStyle("-fx-background-color:#42b9c1");
			 pn.getChildren().add(v1);
			bt2.setOnAction(e->{
				food = new Food(map,player);
				player = new MyPlayer(map);
				pn.getChildren().clear();
				pn.getChildren().add(map);
                BotPlayer btpl = new BotPlayer(map,food,player);
                btpl.empty();
			});
			bt3.setOnAction(e->{
				food = new Food(map,player);
				player = new MyPlayer(map);
				pn.getChildren().clear();
				pn.getChildren().add(map);
				BotPlayer btpl = new BotPlayer(map,food,player);
				btpl.emptySnake();
			});
		}

        Scene scn = new Scene(pn);
        primaryStage.setScene(scn);
        primaryStage.setTitle("Game");
        primaryStage.show();
    }
}
