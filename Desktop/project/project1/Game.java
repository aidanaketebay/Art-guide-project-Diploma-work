/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Asus
 */
public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws NullPointerException, Exception { //call map, player and food classes

        Map m = new Map("map0.txt");
        BotPlayer bp=new BotPlayer(m);
		Food food=new Food(m,bp);
		bp.feed(food);   
		m.setOnKeyPressed(e -> { // if these buttons are tapped do the following steps
            switch (e.getCode()) {
                case UP:
                    bp.moveUp();
                    break;
                case DOWN:
                    bp.moveDown();
                    break;
                case RIGHT:
                    bp.moveRight();
                    break;
                case LEFT:
                    bp.moveLeft();
                    break;
            }
        });
        Scene scene = new Scene(m); //creates the scene
        primaryStage.setTitle("Pacman!");
        primaryStage.setScene(scene);
        primaryStage.show();
        m.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
