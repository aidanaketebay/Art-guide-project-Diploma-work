import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Asus
 *
 */
public class MyPlayer implements Player { // craetes class MyPlayer which implements interface Player

    private Circle ball;
    private Map map;
    private Position coordinate;

    public MyPlayer(Map map) { //constructor MyPlayer which takes map array
		this.map = map;
		this.coordinate = map.getStartPosition();
        this.ball = new Circle(coordinate.getX()*map.unit+map.unit / 2,coordinate.getY()*map.unit+map.unit/2, map.unit/2);
        this.map.getChildren().add(ball);
    }

    @Override
    public void moveUp() {
        if (coordinate.getY() - 1 < 0 || map.getMap()[coordinate.getX()][coordinate.getY()-1] == 1
                ) {} 
	else {
            ball.setCenterY(ball.getCenterY() - map.getUnit());
            coordinate.setY(coordinate.getY() - 1);
        }
    }

    @Override
    public void moveDown() {
		
        if (coordinate.getY()+1 > (map.size-1) || map.getMap()[coordinate.getX()][coordinate.getY()+1] == 1
                ) {
			//System.out.println("HELLO");
        } else {
            ball.setCenterY(ball.getCenterY() + map.getUnit());
            coordinate.setY(coordinate.getY() + 1);
        }
		
		
		//System.out.println(coordinate.getX() + ": " + coordinate.getY() + ", " + map.size);
    }
	//position.getX()+1<=a-1 && (map.getMap())[j+1][i]!=1
    @Override
    public void moveRight() {
		//System.out.println(coordinate.getX() + ": " + coordinate.getY() + ", " + map.size);
        if (coordinate.getX()+1 > map.size-1 || map.getMap()[coordinate.getX()+1][coordinate.getY()] == 1
                ) {
        } else {
            ball.setCenterX(ball.getCenterX() + map.getUnit());
            coordinate.setX(coordinate.getX() + 1);
        }
    }

    @Override
    public void moveLeft() {
        if (coordinate.getX()-1 < 0 || map.getMap()[coordinate.getX()-1][coordinate.getY()] == 1) {
        } else {
            ball.setCenterX(ball.getCenterX() - map.getUnit());
            coordinate.setX(coordinate.getX() - 1);
        }
    }

    @Override
    public Position getPosition() {
        return coordinate;
    }
}
