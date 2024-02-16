import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GUI {
    private JFrame frame;
    public GUI(int size, int bombCount){
        Gameboard game = new Gameboard(size, bombCount);

        JButton[][] buttons = new JButton[size][size];
        frame = new JFrame("Minesweeper");
        frame.setSize(800,800);

        JMenuBar mb = new JMenuBar();
        JMenuItem restartButton = new JMenuItem("Restart");
        JMenuItem totalBombs = new JMenuItem("Total Bombs : " +game.getTotalBombs());
        JMenuItem flaggedCells = new JMenuItem("Flagged Cells : " + game.getFlaggedCells());
        restartButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                NewGameGUI newGame = new NewGameGUI("New game of minesweeper");
                frame.dispose();
            }
        });
        mb.add(restartButton);
        mb.add(totalBombs);
        mb.add(flaggedCells);

        JPanel ButtonPanel = new JPanel();
        for (int i = 0; i < size; i++){
            for (int j = 0; j <size; j++){
                buttons[i][j] = new JButton();
                buttons[i][j].setText("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].setBackground(Color.GRAY);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                final int x = i;
                final int y = j;
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)){
                            if(!game.getColumn(x).getCell(y).getFlipped()) {
                                if (game.getColumn(x).getCell(y).getFlagged()) {
                                    buttons[x][y].setBackground(Color.GRAY);
                                    game.flagCell(x, y);
                                    game.setFlaggedCells(-1);
                                } else {
                                    buttons[x][y].setBackground(Color.ORANGE);
                                    game.flagCell(x, y);
                                    game.setFlaggedCells(1);
                                }
                            }
                            flaggedCells.setText("Flagged Cells : " + game.getFlaggedCells());
                            if (game.getFlaggedCells() > game.getTotalBombs()){
                                flaggedCells.setForeground(Color.RED);
                            }
                            else if (game.getFlaggedCells() == game.getTotalBombs()){
                                flaggedCells.setForeground(Color.GREEN);
                            }
                            else{
                                flaggedCells.setForeground(Color.BLACK);
                            }
                        }
                        else
                        {
                            boolean hitBomb = game.flipCell(x,y);
                            if (hitBomb){
                                buttons[x][y].setBackground(Color.red);
                                NewGameGUI newGame = new NewGameGUI("Game over, you hit a mine.");
                            }
                            else{
                                if (!game.getColumn(x).getCell(y).getFlagged()) {
                                    buttons[x][y].setBackground(Color.green);
                                    buttons[x][y].setText(game.getColumn(x).getCell(y).getAdjacentBombs() + "");
                                    ArrayList<int[]> flippedAdjacent = game.getFlippedAdjacent();
                                    for (int[] location : flippedAdjacent) {
                                        buttons[location[0]][location[1]].setBackground(Color.green);
                                        buttons[location[0]][location[1]].setText(game.getColumn(location[0]).getCell(location[1]).getAdjacentBombs() + "");
                                    }
                                    game.setFlippedAdjacent();
                                }
                            };
                        }
                    }
                });
                ButtonPanel.add(buttons[i][j]);
            }
        }
        ButtonPanel.setLayout(new java.awt.GridLayout(size, size));
        ButtonPanel.setPreferredSize(new Dimension(400, 400));
        frame.add(ButtonPanel,BorderLayout.CENTER);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.setVisible(true);
    }
}
