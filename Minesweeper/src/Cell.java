public class Cell {
    private boolean flipped;
    private boolean flagged;
    private boolean isBomb;
    private int adjacentBombs;
    public Cell(){
        flipped = false;
        flagged = false;
        isBomb = false;
    }

    public boolean getFlipped(){
        return flipped;
    }
    public int getAdjacentBombs(){
        return adjacentBombs;
    }
    public boolean getFlagged(){
        return flagged;
    }
    public boolean getBomb(){
        return isBomb;
    }
    public void setFlipped(){
        flipped = true;
    }
    public void setFlagged(){
        flagged = !flagged;
    }
    public void setBomb(){
        isBomb = !isBomb;
    }
    public void setAdjacentBombs(int input){
        adjacentBombs = input;
    }
}
