import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.application.Platform;
import java.util.ArrayList;

/**
 *
 * @author Asus
 *
 */
public class BotPlayer implements Player { // craetes class MyPlayer which implements interface Player

    public Circle ball;
    public Map map;
	public int x,y;
	public Food food;
    public Position coordinate;

    public BotPlayer(Map map) { //constructor MyPlayer which takes map array
		this.map = map;
		this.coordinate = map.getStartPosition();
        this.ball = new Circle(coordinate.getX()*map.unit+map.unit / 2,coordinate.getY()*map.unit+map.unit/2, map.unit/2);
        this.map.getChildren().add(ball);
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run(){
					try{
						Thread.sleep(100);
						}
					catch(InterruptedException e){	
						}
					x=food.getPosition().getX();
					y=food.getPosition().getY();
					ArrayList<Integer> al = new ArrayList<>();
					for(int i = 0;i<=map.getSize()-1;i++){
						int x =0;
						while(x!=map.getSize()-1){
							al.add(0);//Right
							x++;
							if(x==map.getSize()-1)
								al.add(1);//Down
							}
							int y=map.getSize()-1;
							while(y!=0){
								al.add(2);//Left
								y--;
								if(y==0)
									al.add(1);
							}
						}
						for(int i:al){
						Platform.runLater(new Runnable(){
							@Override
							public void run(){
							if(i==0 && !coordinate.equals(new Position(x,y))){
								moveRight();
							}
							else if(i==1&&!coordinate.equals(new Position(x,y))){
								moveDown();
							}
							else if(i==2&&!coordinate.equals(new Position(x,y))){
								moveLeft();
							}
							}
						});
						try{
							Thread.sleep(100);
							}
						catch(InterruptedException e){}
				}
			}
		});
		Thread thread1 = new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					Thread.sleep(100);
				}catch(InterruptedException e){}
				while(true){
					int x = food.getPosition().getX();
					int y = food.getPosition().getY();
					int x1 = coordinate.getX();
					int y1 = coordinate.getY();
					ArrayList<Integer> al = new ArrayList<>();
					if(coordinate.getX()>x){
						while(x!=x1){
							al.add(1);
							x1--;//0 Right 1 Left 2 Down 3 Up
						}
					}
					else if(coordinate.getX()<x){
						while(x!=x1){
							al.add(0);
							x1++;//0 Right 1 Left 2 Down 3 Up
						}
					}
					else if(coordinate.getY()>y){
						while(y!=y1){
							al.add(3);
							y1--;//0 Right 1 Left 2 Down 3 Up
						}
					}
					else if(coordinate.getY()<y){
						while(y!=y1){
							al.add(2);
							y1++;//0 Right 1 Left 2 Down 3 Up
						}
					}
					for(int i : al){
					Platform.runLater(new Runnable(){
						public void run(){
							
								if(i==0)
									moveRight();
								else if(i==1)
									moveLeft();
								else if(i==2)
									moveDown();
								else if(i==3)
									moveUp();
						}
					});
					try{
						Thread.sleep(30);
					}catch(Exception e){}
					}
				}
			}
		});
		thread1.start();//Set scene if thread.start() Snake if thread1.start Algorithm
	}

	@Override
	public void feed(Food food){
		this.food=food;
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
		
        if (coordinate.getY()+1 > (map.size-1) || map.getMap()[coordinate.getX()][coordinate.getY()+1] == 1) {
			//System.out.println("HELLO");
        } else {
            ball.setCenterY(ball.getCenterY() + map.getUnit());
            coordinate.setY(coordinate.getY() + 1);
        }
	}
		
		
		//System.out.println(coordinate.getX() + ": " + coordinate.getY() + ", " + map.size);
    
	//position.getX()+1<=a-1 && (map.getMap())[j+1][i]!=1
    @Override
    public void moveRight() {
		//System.out.println(coordinate.getX() + ": " + coordinate.getY() + ", " + map.size);
        if (coordinate.getX()+1 > map.size-1 || map.getMap()[coordinate.getX()+1][coordinate.getY()] == 1) {
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

