
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



/**
 * Created by 1 on 03.05.2017.
 */

public class Map extends Pane {
	public static int rectangles=0;
    private static Image img2;
    private final static int unit = 60;
    public static int[][] map;
    private static Position start;
    public static int c = 0,a =0,b = 0;
    public Map(){}
	/*Бұл методты map0.txt файылндағы ақпаратты оқу үшін жаздым
	*/
    public Map(String mapStr) throws FileNotFoundException {
        Scanner scn = new Scanner(new File(mapStr));
        c = Integer.parseInt(scn.next());
        map = new int[c][c];
        for(int i = 0;i<c;i++){
            for(int j = 0;j<c;j++){
                if(scn.hasNext())
                    map[j][i] = Integer.parseInt(scn.next());
            }
        }
    }
    /*rectengle методдын торкөздер жасау үшін жасадым

        */
    public void rectengle(){
        GridPane gr = new GridPane();
        for(int i = 0;i<c;i++){
            for(int j = 0;j<c;j++) {
                img2 = new Image(getClass().getResourceAsStream("t.jpg"));
                  if(map[j][i]!=1)
                    img2 = new Image(getClass().getResourceAsStream("g.jpg"));                  
                if(map[i][j]==2){
                    a = i;b=j;
                }
				if(map[j][i]==1)
                    rectangles  = 1;
                ImageView img23 = new ImageView(img2);
                img23.setFitWidth(unit);
                img23.setFitHeight(unit);
                gr.add(img23,j,i);
            }
        }
        getChildren().addAll(gr);
    }

    public int[][] getMap(){return map;}//массив қайтарды
    public int getUnit(){return unit;}//торкөздердің ұзындығын қайтарды
    // алғашқы орынды беретін метод
    public Position getStartPosotion(){
        start = new Position(a,b);
        return start;
    }
    // картанын(массивтің) ұзындығы
    public int getSize() {
        return c;
    }
}
