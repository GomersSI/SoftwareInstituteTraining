import java.util.ArrayList;
import java.util.Random;

public class Gameboard {
    private ArrayList<Column> gameboard;
    private int gameSize;
    public Gameboard(int size, int bombCount){
        gameSize = size;
        gameboard = new ArrayList<Column>();
        for (int i =0; i < size;i++){
            gameboard.add(new Column(size));
        }
        for (int i = 0; i < bombCount; i++){
            Random rand = new Random();
            boolean addedBomb = false;
            do {
                int x = rand.nextInt(size);
                int y = rand.nextInt(size);
                if (!gameboard.get(x).getCell(y).getBomb()){
                    gameboard.get(x).getCell(y).setBomb();
                    addedBomb = true;
                }
            }while (!addedBomb);
        }
    }
    public Column getColumn(int x){
        return gameboard.get(x);
    }
    public boolean flipCell(int x, int y){
        Cell currentCell = gameboard.get(x).getCell(y);
        currentCell.setFlipped();
        if (!currentCell.getBomb()){
            checkAdjacent(x, y);
        }
        return currentCell.getBomb();
    }
    public void flagCell(int x, int y){
        Cell currentCell = gameboard.get(x).getCell(y);
        if (!currentCell.getFlipped()) {
            currentCell.setFlagged();
        }
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
        if (bombsFound == 0){
            flipAdjacent(x, y);
        }
    }
    private void flipAdjacent(int x, int y){
        for(int i = x-1; i <= x+1; i++){
            if (i < 0){
                i = 0;
            }
            else if(x > gameSize){
                i = gameSize - 1;
                break;
            }
            for(int j = y-1; j <= y+1; j++){
                if (j < 0){
                    j = 0;
                }
                else if(j > gameSize){
                    j = gameSize - 1;
                    break;
                }
                //flipCell(i,j);
            }
        }
    }
}
