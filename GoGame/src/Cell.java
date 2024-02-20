import java.awt.*;

public class Cell {
    private int row, col;
    private Stone stone;
    private boolean scored;
    private boolean scoredPoint;
    public Cell (int r, int c){
        row = r;
        col = c;
        stone = null;
        scored = false;
        scoredPoint = false;
    }

    public void addStone(Color color){
        stone = new Stone(color, row, col);
    }
    public void killStone(){
        stone = null;
    }
    public Stone getStone(){
        return stone;
    }
    public int[] getLocation(){
        return (new int[]{row, col});
    }
    public boolean getScored(){
        return scored;
    }
    public void setScored(){
        scored = true;
    }
    public void resetScored(){scored = false;}
    public void setScoredPoint(){scoredPoint = true;}
    public boolean getScoredPoint(){return scoredPoint;}
}
