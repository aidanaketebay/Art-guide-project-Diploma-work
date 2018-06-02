public interface Player { // creates an interface Player with following methods
    public void feed(Food food);

    public void moveRight();

    public void moveLeft();

    public void moveUp();

    public void moveDown();

    public Position getPosition();
}
