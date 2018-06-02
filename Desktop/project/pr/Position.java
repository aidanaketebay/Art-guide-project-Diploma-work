

/**
 * Created by 1 on 03.05.2017.
 */
public class Position {
    int x;
    int y;
	// конструктор
    Position(int a,int b){
        this.x = a;
        this.y = b;
    }
    // шардың позициясын өзгерту (х бойынша)
    public int getX(){return x;}
    // шардың позициясын алу (y бойынша)
    public int getY(){return y; }
    // шардыңпозициясын өзгерту (х бойынша)
    public void setX(int a){this.x = a;}
    //шардың позициясын өзгерту (y бойынша)
    public void setY(int b){this.y=b;}
	//шармен тамақтың позициаларының теңдігін анықтайды
    public boolean equals(Position pos){
        return (this.getX()==pos.getX() && this.getY()==pos.getY());
    }
}

