import java.awt.*;

public class Stone {
    private Color color;
    private int freeLiberties = 4;
    private Chain chain;
    private int row, col;
    public Stone(Color colorIn, int r, int c){
        color = colorIn;
        chain = new Chain(colorIn);
        chain.addStone(this);
        row = r;
        col = c;
    }

    public Color getColor() {
        return color;
    }
    public int[] getPos(){
        return new int[]{row, col};
    }

    public void setFreeLiberties(int input){
        freeLiberties = input;
    }
    public int getFreeLiberties() {
        return freeLiberties;
    }
    public void setChain(Chain chainIn){
        chain = chainIn;
    }
    public Chain getChain(){
        return chain;
    }

    public boolean checkStone(){
        return (chain.getFreeLiberties() == 0);
    }
}
