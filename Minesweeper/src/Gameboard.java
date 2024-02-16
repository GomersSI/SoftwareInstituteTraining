import java.util.ArrayList;
import java.util.Random;

public class Gameboard {
    private ArrayList<Column> gameboard;
    private ArrayList<int[]> flippedAdjacent = new ArrayList<int[]>();
    private int gameSize;
    private int totalBombs;
    private int flippedCells = 0;
    private int flaggedCells = 0;
    private boolean bombsPlaced = false;
    public Gameboard(int size, int bombCount){
        gameSize = size;
        totalBombs = bombCount;
        gameboard = new ArrayList<Column>();
        for (int i =0; i < size;i++){
            gameboard.add(new Column(size));
        }
    }
    public int getTotalBombs(){
        return totalBombs;
    }
    public void setFlippedAdjacent(){
        flippedAdjacent = new ArrayList<int[]>();
    }
    public int getFlaggedCells(){
        return flaggedCells;
    }
    public void setFlaggedCells(int input){
        flaggedCells += input;
    }

    public Column getColumn(int x){
        return gameboard.get(x);
    }
    public boolean flipCell(int x, int y){
        Cell currentCell = gameboard.get(x).getCell(y);
        if (!currentCell.getFlipped() && !currentCell.getFlagged()) {
            flippedCells++;
            if (!bombsPlaced) {
                placeBombs(x, y);
            }
            currentCell.setFlipped();
            if (!currentCell.getBomb() && (currentCell.getAdjacentBombs() == 0)) {
                try{
                flipAdjacent(x, y);
                }catch (Exception e){};
            }
            if (flippedCells + totalBombs == gameSize * gameSize){
                NewGameGUI newGame = new NewGameGUI("Game won");
            }
        }
        return currentCell.getBomb();
    }
    public ArrayList<int[]> getFlippedAdjacent(){
        return flippedAdjacent;
    }
    public void flagCell(int x, int y){
        Cell currentCell = gameboard.get(x).getCell(y);
        if (!currentCell.getFlipped()) {
            currentCell.setFlagged();
        }
    }
    private void placeBombs(int x, int y){
        for (int i = 0; i < totalBombs; i++){
            Random rand = new Random();
            boolean addedBomb = false;
            do {
                int newx = rand.nextInt(gameSize);
                int newy = rand.nextInt(gameSize);
                if (!gameboard.get(newx).getCell(newy).getBomb() && newx != x && newy != y){
                    gameboard.get(newx).getCell(newy).setBomb();
                    addedBomb = true;
                }
            }while (!addedBomb);
        }
        for (int i = 0; i <gameSize; i++){
            for (int j = 0; j < gameSize; j++){
                checkAdjacent(i, j);
            }
        }
        bombsPlaced = true;
    }
    private void checkAdjacent(int x, int y){
        int bombsFound = 0;
        Cell currentCell;
        for(int i = x-1; i <= x+1; i++){
            if (i < 0){
                i = 0;
            }
            else if(i >= gameSize){
                i = gameSize - 1;
                break;
            }
            for(int j = y-1; j <= y+1; j++){
                if (j < 0){
                    j = 0;
                }
                else if(j >= gameSize){
                    j = gameSize - 1;
                    break;
                }
                currentCell = gameboard.get(i).getCell(j);
                if (currentCell.getBomb()){
                    bombsFound++;
                }
            }
        }
        currentCell = gameboard.get(x).getCell(y);
        currentCell.setAdjacentBombs(bombsFound);
    }
    private void flipAdjacent(int x, int y){
        for(int i = x-1; i <= x+1; i++){
            if (i < 0){
                i = 0;
            }
            else if(i >= gameSize){
                break;
            }
            for(int j = y-1; j <= y+1; j++){
                if (j < 0){
                    j = 0;
                }
                else if(j >= gameSize){
                    break;
                }
                Cell currentCell = gameboard.get(i).getCell(j);
                if(!currentCell.getFlipped()) {
                    int[] location = new int[]{i,j};
                    flippedAdjacent.add(location);
                }
            }
        }
        for (int[] cell: flippedAdjacent){
            flipCell(cell[0], cell[1]);
        }
    }
}
