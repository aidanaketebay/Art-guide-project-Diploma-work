public class Position { //Position class - Stores the position (x and y coordinates). 

    private int x;
    private int y;

    public Position(int x, int y) { // creates a constructor Position
        this.x = x;
        this.y = y;
    }

    public int getX() { // gets the coordinate of x
        return x;
    }

    public int getY() { //gets the coordinate of y
        return y;
    }

    public void setX(int x) { // sets the coordinate of x
        this.x = x;
    }

    public void setY(int y) { //sets the coordinate of y
        this.y = y;
    }

    public boolean equals(Position other) {  //checks if the coordinates are equal or not
        return (other.x == this.x && other.y == this.y);
    }

}
