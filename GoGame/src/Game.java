import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Game extends JPanel{
    // Create Constants.
    private static int SIZE;
    private JLabel score, toPlay;
    private JPanel scorePanel, toPlayPanel;
    private JButton passButton, restartButton;
    private boolean passed, gameOver;
    private int whiteScore, blackScore, whiteStones, blackStones;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;
    // Create private variables.
    boolean whiteToMove;
    private Cell[][] grid;

    public Game(int boardSize){
        SIZE = boardSize;
        // Set up graphics.
        graphicsInit();
        restart();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (gameOver){
                    return;
                }
                // Converts to float for float division and then rounds to provide nearest intersection.
                int row = Math.round((float) (e.getY() - BORDER_SIZE)
                        / TILE_SIZE);
                int col = Math.round((float) (e.getX() - BORDER_SIZE)
                        / TILE_SIZE);

                // DEBUG INFO
                System.out.println(String.format("y: %d, x: %d", row, col));

                // Check wherever it's valid.
                if (row >= SIZE || col >= SIZE || row < 0 || col < 0) {
                    // DEBUG INFO
                    System.out.println("Invalid location");
                    return;
                }
                // DEBUG INFO
                System.out.println("Valid location");

                // Check for stone.
                if (grid[row][col].getStone() != null) {
                    // DEBUG INFO
                    System.out.println("Invalid, existing stone");
                    return;
                }
                // DEBUG INFO
                System.out.println("Valid, no stone");
                // Place Stone Method.
                try {
                    placeStone(row, col);
                }catch (Exception ex){}
                passed = false;

                // Update variables and text.
                turnPlayed();
                checkLiberties();
                updateText();
                repaint();
            }
        });
    }
    private void restart(){
        // Set up variables.
        whiteScore = 0;
        passed = false;
        gameOver = false;
        blackScore = 0;
        whiteToMove = true;
        grid = new Cell[SIZE][SIZE];
        for (int row = 0; row < grid.length; row++){
            for (int col = 0; col < grid.length; col++){
                grid[row][col] = new Cell(row, col);
            }
        }
        updateText();
        repaint();
    }
    private void turnPlayed(){
        whiteToMove = !whiteToMove;
    }
    private void updateText(){
        score.setText("White score : " + (whiteScore + whiteStones) + " Black score : " + (blackScore + blackStones));
        if (gameOver) {
            toPlay.setText("Game over!");
        }
        else if (whiteToMove){
            toPlay.setText("White to play");
        }
        else {
            toPlay.setText("Black to play");
        }
    }

    private void graphicsInit(){
        scorePanel = new JPanel();
        toPlayPanel = new JPanel();
        passButton = new JButton("Pass");
        restartButton = new JButton("Restart");
        score = new JLabel("White score : " + whiteScore + " Black score : " + blackScore);
        toPlay = (whiteToMove) ? new JLabel("Black to play") : new JLabel("White to play");

        scorePanel.add(score);
        toPlayPanel.add(toPlay);
        super.add(restartButton);
        super.add(scorePanel);
        super.add(toPlayPanel);
        super.add(passButton);

        this.setBackground(Color.getHSBColor(30, 81, 62));

        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passButtonClicked();
            }
        });
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart();
            }
        });
    }
    private void passButtonClicked(){
        if (passed){
            gameOver = true;
        }
        passed = true;
        turnPlayed();
        updateText();
        repaint();
    }
    private void checkLiberties(){
        blackStones = 0;
        whiteStones = 0;
        for (int row = 0; row < SIZE; row ++){
            for (int col = 0; col < SIZE; col++){
                if (grid[row][col].getStone() != null) {
                    if (grid[row][col].getStone().getColor() == Color.BLACK){
                        blackStones += 1;
                    }
                    else{
                        whiteStones += 1;
                    }
                    ArrayList<Cell> neighbors = new ArrayList<Cell>();
                    // Don't check outside the board
                    if (row > 0) {
                        neighbors.add(grid[row - 1][col]);
                    }
                    if (row < SIZE - 1) {
                        neighbors.add(grid[row + 1][col]);
                    }
                    if (col > 1) {
                        neighbors.add(grid[row][col - 1]);
                    }
                    if (col < SIZE - 1) {
                        neighbors.add(grid[row][col + 1]);
                    }
                    int freeLiberties = neighbors.size();
                    for (Cell cell : neighbors) {
                        if (cell.getStone() == null) {
                            continue;
                        }
                        freeLiberties--;
                    }
                    grid[row][col].getStone().setFreeLiberties(freeLiberties);
                    if (grid[row][col].getStone().getChain().getStones().size() == 1 && freeLiberties == 0){
                        grid[row][col].killStone();
                    }
                }
            }
        }
    }
    private void placeStone(int row, int col){
        //Get Color and place stone.
        Color player = whiteToMove ? Color.WHITE : Color.BLACK;
        grid[row][col].addStone(player);

        Cell[] neighbors = new Cell[4];
        // Don't check outside the board
        if (row > 0) {
            neighbors[0] = grid[row - 1][col];
        }
        if (row < SIZE - 1) {
            neighbors[1] = grid[row + 1][col];
        }
        if (col > 1) {
            neighbors[2] = grid[row][col - 1];
        }
        if (col < SIZE - 1) {
            neighbors[3] = grid[row][col + 1];
        }

        for (Cell cell : neighbors){
            if (cell.getStone() == null){
                continue;
            }
            grid[row][col].getStone().setFreeLiberties(grid[row][col].getStone().getFreeLiberties() - 1);
            cell.getStone().setFreeLiberties(cell.getStone().getFreeLiberties() - 1);

            if (cell.getStone().getColor() != grid[row][col].getStone().getColor()) {
                if (cell.getStone().checkStone()) {
                    Color team = cell.getStone().getColor();
                    // DEBUG INFO
                    System.out.println("Kill chain");
                    ArrayList<int[]> positions = cell.getStone().getChain().getPos();
                    for (int[] pos : positions){
                        grid[pos[0]][pos[1]].killStone();
                    }
                    if (team == Color.BLACK){
                        whiteScore += positions.size();
                    }
                    else{
                        blackScore += positions.size();
                    }
                    // DEBUG INFO
                    System.out.println("White score : " + whiteScore + " Black score : " + blackScore);
                }
            }
            else {
                cell.getStone().getChain().joinChain(grid[row][col].getStone().getChain());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.BLACK);
        // Draw rows.
        for (int i = 0; i < SIZE; i++) {
            g2.drawLine(BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE, TILE_SIZE
                    * SIZE - 1 + BORDER_SIZE - TILE_SIZE, i * TILE_SIZE + BORDER_SIZE);
        }
        // Draw columns.
        for (int i = 0; i < SIZE; i++) {
            g2.drawLine(i * TILE_SIZE + BORDER_SIZE, BORDER_SIZE, i * TILE_SIZE
                    + BORDER_SIZE, TILE_SIZE * SIZE + BORDER_SIZE - TILE_SIZE);
        }
        // Draw Stones.
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Stone stone = grid[row][col].getStone();
                if (stone != null) {
                    g2.setColor(stone.getColor());
                    g2.fillOval(col * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                            row * TILE_SIZE + BORDER_SIZE - TILE_SIZE / 2,
                            TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
}