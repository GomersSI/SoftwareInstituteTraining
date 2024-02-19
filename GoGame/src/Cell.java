import java.awt.*;

public class Cell {
    private int row, col;
    Stone stone;
    public Cell (int r, int c){
        row = r;
        col = c;
        stone = null;
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
}
