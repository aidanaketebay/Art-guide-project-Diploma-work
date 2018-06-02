

class BotPlayer{
    public Map map;
    public Food food;
    public MyPlayer player;

	BotPlayer(Map map,Food food,MyPlayer player){
	    this.food = food;
	    this.map = map;
	    this.player = player;
    }
    public void empty(){
        new Thread(() -> {
            try {Thread.sleep(500);}
            catch (InterruptedException e) {
                e.printStackTrace();}
            if(map.a<food.var2){
                player.moveRight();
               empty();}
            else if(map.a>food.var2){player.moveLeft(); empty();}
            else if(map.a==food.var2){
                if(map.b<food.var3){
                    player.moveDown();
                }else if(map.b>food.var3)
                    player.moveUp();
                empty();

            }
        }).start();
    }
    public void emptySnake(){
        new Thread(() -> {
            try {Thread.sleep(500);}
            catch (InterruptedException e) {
                e.printStackTrace();}
            if(map.a==food.var2&&map.b==food.var3){}
            else {
                if(map.a<map.getSize()-1&&map.b%2==0){
                    if(map.map[map.a+1][map.b]==0)
                        player.moveRight();
                    emptySnake();
                }
                else if((map.a==map.getSize()-1&&map.b%2==0)||(map.a==0&&map.b%2!=0)){player.moveDown();emptySnake();}

                else if(map.a>0){

                    if(map.map[map.a-1][map.b]==0){
                        player.moveLeft();}
                    emptySnake();}

            }
        }).start();
    }

    public void find(){

        new Thread(() ->{
            final int[] a = {food.var2};
            final int[] b = {food.var3};

            PATHT phj = new PATHT(map, a[0], b[0]);
            phj.mn();
            
            for(int i =0;i<phj.massiv[map.a][map.b];i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int t = 0;
                if(map.a < map.getSize()-1){
                    if (phj.massiv[map.a][map.b]-1 == phj.massiv[map.a + 1][map.b]){t=1;}
                }
                if(0<map.a) {
                    if (phj.massiv[map.a][map.b]-1 == phj.massiv[map.a - 1][map.b]){t=2;}
                }
                if(map.b<map.getSize()-1){
                    if (phj.massiv[map.a][map.b]-1 == phj.massiv[map.a][map.b + 1]){t=3;}
                }
                if (map.b > 0) {
                    if (phj.massiv[map.a][map.b] -1== phj.massiv[map.a][map.b - 1]){t=4;}
                }
                if(t==1)
                    player.moveRight();
                else if (t==2)
                    player.moveLeft();
                else if(t==3)
                    player.moveDown();
                else if(t==4)
                    player.moveUp();
            }
            find();}).start();
    }
    class PATHT {
        public int[][] massiv;
        int a;int b;
        PATHT(Map map,int a,int b){this.a = a;
            this.b = b;
            massiv = new  int [map.getSize()][map.getSize()];
            for(int i=0;i<map.getSize();i++){
                for(int j=0;j<map.getSize();j++){
                    massiv[i][j]=map.getMap()[i][j];
                }
            }
        }
        public void s(){
            for(int i=0;i<massiv[0].length;i++){
                for(int j=0;j<massiv[0].length;j++){
                    if(massiv[j][i]==1)
                        massiv[j][i]=-1;
                    if(massiv[j][i]==2)
                        massiv[j][i]=0;
                }
            }
        }

        public  void reck(int x,int y,int t){
            if(x<massiv.length && y<massiv.length&&x>=0&&y>=0){
                if(massiv[x][y]==0){
                    massiv[x][y]=t;
                    reck(x-1,y,t+1);
                    reck(x+1,y,t+1);
                    reck(x,y+1,t+1);
                    reck(x,y-1,t+1);
                }
                else if(massiv[x][y]>=t){
                    massiv[x][y]=t;
                    reck(x-1,y,t+1);
                    reck(x+1,y,t+1);
                    reck(x,y+1,t+1);
                    reck(x,y-1,t+1);
                }else
                    return;
            }}

        public void mn() {
            s();
            reck(a,b,1);
        }
    }

}