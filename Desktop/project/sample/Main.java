
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Map map = new Map();
        MyPlayer myplayer = new MyPlayer();



        myplayer.setMap(map);

        primaryStage.setTitle("Eater");
        Scene scene = new Scene(map);
        primaryStage.setScene(scene);
        map.draw("map0.txt");
        myplayer.setMyPos(new Position(map.getStart().getX(), map.getStart().getY()));
        Food food = new Food(map, myplayer);

        BotPlayer bot = new BotPlayer();
        bot.setMap(map.getMap());
        bot.setGraph();



        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int x = myplayer.getPosition().getX();
                int y = myplayer.getPosition().getY();

                map.setStart(new Position(x, y));


                switch(event.getCode()){
                    case UP:
                        myplayer.moveLeft();
                        System.out.println(bot.searchPath(3, 15));
                        break;
                    case DOWN:
                        myplayer.moveRight();
                        break;
                    case LEFT:
                        myplayer.moveUp();
                        break;
                    case RIGHT:
                        myplayer.moveDown();
                        break;
                }
                map.setEnd(myplayer.getPosition());
            }
        });
        primaryStage.show();

        Thread thread = new Thread(() -> {

            while (true) {
                int x = myplayer.getPosition().getX();
                int y = myplayer.getPosition().getY();

                String path = bot.searchPath(myplayer.getPosition().getX() * 8 + myplayer.getPosition().getY(), food.getFoodPosition().getX() * 8 + food.getFoodPosition().getY());
                System.out.println(path);
                String[] pathReadable = bot.makePathReadable(path);
                System.out.println(path);
                System.out.println(food.getFoodPosition().getX() + " " + food.getFoodPosition().getY());
                System.out.println(path);

                for (String s : pathReadable) {
                    if (s.equals("UP")) {
                        try {
                            //there are other methods such as positioning mouse and mouseclicks etc.
                            Robot r = new Robot();
                            r.keyPress(java.awt.event.KeyEvent.VK_LEFT);
                            r.keyRelease(java.awt.event.KeyEvent.VK_LEFT);
                            Thread.sleep(100L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (s.equals("DOWN")) {
                        try {
                            //there are other methods such as positioning mouse and mouseclicks etc.
                            Robot r = new Robot();
                            r.keyPress(java.awt.event.KeyEvent.VK_RIGHT);
                            r.keyRelease(java.awt.event.KeyEvent.VK_RIGHT);
                            Thread.sleep(100L);
                        } catch (Exception e) {
                            //Teleport penguins
                        }
                    } else if (s.equals("LEFT")) {
                        try {
                            //there are other methods such as positioning mouse and mouseclicks etc.
                            Robot r = new Robot();
                            r.keyPress(java.awt.event.KeyEvent.VK_UP);
                            r.keyRelease(java.awt.event.KeyEvent.VK_UP);
                            Thread.sleep(100L);
                        } catch (Exception e) {
                            //Teleport penguins
                        }
                    } else if (s.equals("RIGHT")) {
                        try {
                            //there are other methods such as positioning mouse and mouseclicks etc.
                            Robot r = new Robot();
                            r.keyPress(java.awt.event.KeyEvent.VK_DOWN);
                            r.keyRelease(java.awt.event.KeyEvent.VK_DOWN);
                            Thread.sleep(100L);
                        } catch (Exception e) {
                            //Teleport penguins
                        }
                    }
                }
            }
        });
        thread.start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
