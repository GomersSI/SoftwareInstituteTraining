import java.util.ArrayList;

public class Column {
    private ArrayList<Cell> column;
    public Column(int size){
        column = new ArrayList<Cell>();
        for (int i = 0; i < size; i++){
            column.add(new Cell());
        }
    }

    public Cell getCell(int position){
        return column.get(position);
    }
}
