

import java.io.FileNotFoundException;

/**
 * Created by 1 on 03.05.2017.
 */
public interface Player {
    /*
	оңға жылжыту
    */
    public void moveRight();

    /*
	солға жылжыту
     */
    public void moveLeft();

    /*
	жоғарыға жылжыту
     */
    public void moveUp();

    /*
	төмен жылжыту
     */
    public void moveDown();
	/*
		шардың позициасын алу
	 */
    public Position getPosition();

}
