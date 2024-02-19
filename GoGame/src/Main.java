import javax.swing.*;
import java.awt.*;
public class Main {
    //Create Constants
    private static final String TITLE = "Go";
    private static final int SIZE = 19;

    public static void main(String[] args) {
        new Main().init();
    }
    private void init(){
        JFrame frame = new JFrame();
        frame.setTitle(TITLE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(new BorderLayout());

        frame.add(panel);

        panel.setBorder(BorderFactory.createEmptyBorder(SIZE,SIZE,SIZE,SIZE));

        Game newGame = new Game(SIZE);
        panel.add(newGame);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setSize((SIZE + 3) * newGame.TILE_SIZE, (SIZE + 3) * newGame.TILE_SIZE);
        frame.setVisible(true);
    }
}