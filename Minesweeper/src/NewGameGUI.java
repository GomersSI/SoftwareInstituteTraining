import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameGUI {
    private int maxSize = 20;
    private JTextField gridSize;
    private JTextField mineCount;
    private GUI currentGame;
    private JFrame frame;

    public NewGameGUI(String message){
        frame = new JFrame(message);
        gridSize = new JTextField("Grid Size (Max " + maxSize + ")");
        mineCount = new JTextField("Mine count");
        JButton startButton = new JButton("Start Game");
        JPanel panel = new JPanel();

        JMenuBar mb = new JMenuBar();
        JMenuItem gridSizeLabel = new JMenuItem("Grid Size (Max " + maxSize + ")");
        JMenuItem mineCountLabel = new JMenuItem("Mine count");
        JMenuItem startLabel = new JMenuItem("");
        mb.add(gridSizeLabel);
        mb.add(mineCountLabel);
        mb.add(startLabel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPressed();
            }
        });

        frame.setSize(400,125);
        panel.setLayout(new java.awt.GridLayout(1, 3));

        panel.add(gridSize);
        panel.add(mineCount);
        panel.add(startButton);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void startPressed(){
        String size = gridSize.getText();
        String bombs = mineCount.getText();
        if (integerCheck(size) && integerCheck(bombs) && sizeCheck(size) && mineCheck(bombs, size)){
            currentGame = new GUI(Integer.parseInt(size), Integer.parseInt(bombs));
            frame.dispose();
        }
    }
    private boolean integerCheck(String input){
        try{
            Integer.valueOf(input);
            return true;
        } catch(Exception e){
            return false;
        }
    }
    private boolean sizeCheck(String input){
        return (Integer.parseInt(input) <= maxSize);
    }
    private boolean mineCheck(String input1, String input2){
        return (Integer.parseInt(input1) < (Integer.parseInt(input2) * Integer.parseInt(input2)));
    }
}
