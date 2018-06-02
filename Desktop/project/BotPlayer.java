import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.application.Platform;
import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 *
 * @author Asus
 *
 */
public class BotPlayer implements Player { // craetes class MyPlayer which implements interface Player

    public ImageView ball;
    public Map map;
	public int x,y;
	public Food food;
    public Position coordinate;

    public BotPlayer(Map map) { //constructor MyPlayer which takes map array
		this.map = map;
		this.coordinate = map.getStartPosition();
        	this.ball = new ImageView("file:\\C:\\Users\\Asus\\Desktop\\pt.gif");
		ball.setX((coordinate.getY()*map.unit+map.unit)-map.unit);
		ball.setY((coordinate.getX()*map.unit+map.unit)-map.unit); 
		ball.setFitHeight(map.unit);
		ball.setFitWidth(map.unit); 
        	this.map.getChildren().add(ball);
		Thread thread = new Thread(new Runnable(){ //creates new thread
			@Override
			public void run(){
					try{
						Thread.sleep(100);
						}
					catch(InterruptedException e){	
						}
					x=food.getPosition().getX(); // gets x position of food
					y=food.getPosition().getY(); // gets y position of food
					ArrayList<Integer> al = new ArrayList<>(); //new ArrayList
					for(int i = 0;i<=map.getSize()-1;i++){ 
						int x =0;
						while(x!=map.getSize()-1){ //while x not equal to 7
							al.add(0);//Right // add 0 to ArrayList
							x++; //increases the value of x
							if(x==map.getSize()-1) // when x==7
								al.add(1);//Down //al adds 1
							}
							int y=map.getSize()-1; // y=7
							while(y!=0){ //while y not equals to 0
								al.add(2);//Left 
								y--; // y dicreases
								if(y==0)
									al.add(1);
							}
						}
						for(int i:al){ // for all int in list
						Platform.runLater(new Runnable(){
							@Override
							public void run(){
							if(i==0 && !coordinate.equals(new Position(x,y))){ // i==0 and coordinate not equal to new position o food
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
					if(coordinate.getX()>x){ //coordinate of x is bigger than coordinate of foo by x
						while(x!=x1){ // while they are not equal
							al.add(1); // al adds 1
							x1--;//0 Right 1 Left 2 Down 3 Up // int x1 dicreases
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
            ball.setY((ball.getY() - map.getUnit()));
            coordinate.setY(coordinate.getY() - 1);
        }
    }

    @Override
    public void moveDown() {
		
        if (coordinate.getY()+1 > (map.size-1) || map.getMap()[coordinate.getX()][coordinate.getY()+1] == 1) {
			//System.out.println("HELLO");
        } else {
            ball.setY((ball.getY() + map.getUnit()));
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
            ball.setX((ball.getX() + map.getUnit()));
            coordinate.setX(coordinate.getX() + 1);
        }
    }

    @Override
    public void moveLeft() {
        if (coordinate.getX()-1 < 0 || map.getMap()[coordinate.getX()-1][coordinate.getY()] == 1) {
        } else {
            ball.setX((ball.getX() - map.getUnit()));
            coordinate.setX(coordinate.getX() - 1);
        }
    }

    @Override
    public Position getPosition() {
        return coordinate;
    }
}

