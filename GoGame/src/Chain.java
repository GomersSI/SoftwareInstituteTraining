import java.awt.*;
import java.util.ArrayList;

public class Chain {
    private ArrayList<Stone> stones;
    private Color color;
    public Chain(Color colorIn){
        stones = new ArrayList<Stone>();
        color = colorIn;
    }
    public ArrayList<int[]> getPos(){
        ArrayList<int[]> positions = new ArrayList<int[]>();
        for(Stone stone : stones){
            positions.add(stone.getPos());
        }
        return positions;
    }
    public ArrayList<Stone> getStones(){
        return stones;
    }
    public int getFreeLiberties(){
        int total = 0;
        for (Stone stone : stones){
            total += stone.getFreeLiberties();
        }
        return total;
    }
    public void addStone(Stone stone){
        stones.add(stone);
        stone.setChain(this);
    }
    public void joinChain(Chain chain){
        for (Stone stone : chain.stones){
            stone.setChain(this);
            addStone(stone);
        }
    }
}
