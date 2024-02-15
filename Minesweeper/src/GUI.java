import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
    private JFrame frame;
    public GUI(int size, int bombCount){
        Gameboard game = new Gameboard(size, bombCount);

        JButton[][] buttons = new JButton[size][size];
        frame = new JFrame("Minesweeper");
        frame.setSize(800,800);

        JMenuBar mb = new JMenuBar();
        JMenuItem restartButton = new JMenuItem("Restart");
        restartButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                NewGameGUI newGame = new NewGameGUI();
                frame.dispose();
            }
        });
        mb.add(restartButton);

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
                            buttons[x][y].setBackground(Color.ORANGE);
                            game.flagCell(x,y);
                        }
                        else
                        {
                            boolean hitBomb = game.flipCell(x,y);
                            if (hitBomb){
                                buttons[x][y].setBackground(Color.red);
                                //Game over would you like to play again
                            }
                            else{
                                buttons[x][y].setBackground(Color.green);
                                buttons[x][y].setText(game.getColumn(x).getCell(y).getAdjacentBombs() + "");
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
