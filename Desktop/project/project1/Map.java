import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
/**
 *
 * @author Asus
 * 
 **/
public class Map extends Pane{ // creates a map which extends Pane
    int x = 0, y = 0; //global variable of coordinates x and y
    int unit = 30; //global variable unit
    int size; //int size
    private int[][] map; // creates a twodimentional int array map
    Position start; 
    public Map(String pool){ //Constructor with String variable(name of map.txt file)
		try{
        Scanner input = new Scanner(new File(pool)); //reads the file
        size = input.nextInt();	//size equals the the first number in file, which shows the actual size of the map
        map = new int[size][size]; //twodimentional map with number of columns and rows 
        int k = 0;
        while(k != size){ // while the number of k not equal to 8
            int l = 0;
            while(l != size){ // and while int l not equal to 8
                map[l][k] = input.nextInt(); //gives the value for the array
                l++; //increase the value for int l
            }
            k++; ////increase the value for int k
        }
        k = 0; //creates new int 
        while(k != size){ //while int not equal to 8
            x = 0; 
            int l = 0;
            while(l != size){ //while l not equal to 8
                if(map[k][l] != 1){ //if the value of the array is not equals to 1
                    Rectangle cube = new Rectangle(y, x, unit, unit); //creates new rectangle 
                    cube.setStroke(Color.BLACK);
                    cube.setFill(Color.WHITE);
                    this.getChildren().add(cube); //adds to the Pane the in the given coordinates new rectangle
                    if(map[k][l] == 2){ //if the value of the array is equals to 2
                        start = new Position(k,l); // craetes new position with coordinates k and l
                    }
                }
                else if (map[k][l] == 1){ //if the value of the array is equals to 1		
                    Rectangle cube = new Rectangle(y, x, unit, unit); //adds to the Pane the in the coordinate (0,0) new rectangle
                    cube.setStroke(Color.BLACK); 
                    cube.setFill(Color.BLACK);
                    this.getChildren().add(cube); //adds to the Pane the in the given coordinates new rectangle
                }
                x = x + unit; // increases the coordinate x by unit 
                l++; // increase the column number by 1
            }
            y = y + unit; // increases the coordinate y by unit 
            k++; // increase the row number by 1
        }
		}catch(Exception e){} // catch exceptions
    }
    public int getUnit(){ // gets the value of unit
       return unit;
    }
    public int getSize(){ //gets the value of the size 
       return size;
    }
    public int[][] getMap(){ //gets the map array
       return map;
    }
    public Position getStartPosition(){ // get the position
       return start;
    }
}

