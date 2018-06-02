

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by 1 on 03.05.2017.
 */
public class MyPlayer implements Player {
    public static Circle ball;
    private static Map map = new Map();
    private Position position;
    private final  int radius = (map.getUnit()/2)-map.getUnit()/10;
    private final int c = (map.c-1)*31 +16;
    public static int corx=map.getUnit()/2,x =0;
    public static int cory = map.getUnit()/2,y =0;

    @Override
	/*Шарды оңға жылжыту */
    public void moveRight() {
        if(map.a < map.getSize()-1){
            map.a += 1;x += map.getUnit();
         }if (map.map[map.a][map.b] == 1) {
                x -= map.getUnit();
                map.a -= 1;
            }
      
        ball.setCenterX(x);
    }
    // шарды солға жылжыту

    @Override
    public void moveLeft() {
        if(map.a !=0){
            x-=map.getUnit();
            map.a-=1;}
			if(map.map[map.a][map.b]==1){
                x+=map.getUnit();
                map.a+=1;
            }
       
            ball.setCenterX(x);
    }
    // шарды жоғарыға жылжыту
    @Override
    public void moveUp(){
            if(map.b!=0){
            map.b-=1;y-=map.getUnit();}
         if(map.map[map.a][map.b]==1){
                y+=map.getUnit();
                map.b+=1;}
            ball.setCenterY(y);
    }
    // Шарды төменге жылжыту
    @Override
    public void moveDown() {

            if(map.b < map.getSize()-1){
            map.b +=1;y +=map.getUnit();
            }
			if(map.map[map.a][map.b] == 1){
                    y -= map.getUnit();map.b -= 1;}
            ball.setCenterY(y);

    }

    /*
    констукторды шарды құру үшін құрастырдым
    "х" шардын бастапкы мәні(х кордината бойынша)
    "у" шардын бастапкы мәні(у кордината бойынша)
    */
    MyPlayer(Map map){
        x = corx+map.getUnit()* map.a;
        y =cory+map.getUnit()*map.b;
        ball = new Circle(x,y,radius);

        ball.setFill(Color.RED);
        map.getChildren().addAll(ball);
    }
	
	/*
		шардың позициасын алу
	 */
    @Override
    public Position getPosition() {
        position = new Position(map.a,map.b);
        return position;
    }
}
