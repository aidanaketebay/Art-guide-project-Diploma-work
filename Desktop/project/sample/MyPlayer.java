public class MyPlayer implements Player {
    private Position myPos;
    private Map map;
    @Override
    public void moveRight() {
        if(this.myPos.getY()+1<this.map.getSize() && this.map.getMap()[this.myPos.getX()][this.myPos.getY()+1]!=1){
            this.myPos.setY(this.myPos.getY()+1);
        }
    }

    @Override
    public void moveLeft() {
        if(this.myPos.getY()>0 && this.map.getMap()[this.myPos.getX()][this.myPos.getY()-1]!=1){
            this.myPos.setY(this.myPos.getY()-1);
        }
    }

    @Override
    public void moveUp() {
        if(this.myPos.getX()>0 && this.map.getMap()[this.myPos.getX()-1][this.myPos.getY()]!=1){
            this.myPos.setX(this.myPos.getX()-1);
        }
    }

    @Override
    public void moveDown() {
        if(this.myPos.getX()+1<this.map.getSize() && this.map.getMap()[this.myPos.getX()+1][this.myPos.getY()]!=1){
            this.myPos.setX(this.myPos.getX()+1);
        }
    }

    @Override
    public Position getPosition() {
        return this.myPos;
    }


    public void setMyPos(Position myPos) {
        this.myPos = myPos;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
